import { Component, OnInit, AfterViewInit, OnDestroy, ViewChild, ElementRef, ChangeDetectorRef, DestroyRef, inject } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { WorldService } from '../../services/world.service';
import { UserProgressService } from '../../services/user-progress.service';
import { Quest, SubmissionResponse } from '../../models/world.model';
import { FormsModule } from '@angular/forms';
import { catchError, forkJoin, map, of, switchMap } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { AuthService } from '../../services/auth.service';

/**
 * Quest playground and code execution workspace with Monaco Editor (VSCode).
 */
@Component({
  selector: 'app-quest-play',
  standalone: true,
  imports: [RouterModule, FormsModule],
  templateUrl: './quest-play.component.html',
  styleUrl: './quest-play.component.css'
})
export class QuestPlayComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild('editorContainer') editorContainer!: ElementRef<HTMLDivElement>;

  worldId: string | null = null;
  quest: Quest | null = null;
  code: string = '';
  editorReady = false;
  loading: boolean = true;
  submitting: boolean = false;
  errorMessage: string = '';
  result: SubmissionResponse | null = null;
  progressSyncFailed = false;

  private editorInstance: any = null;
  private readonly destroyRef = inject(DestroyRef);

  constructor(
    private route: ActivatedRoute,
    private worldService: WorldService,
    public progressService: UserProgressService,
    public authService: AuthService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.worldId = this.route.snapshot.paramMap.get('worldId');
    const questId = this.route.snapshot.paramMap.get('questId');

    if (!questId || !this.worldId) {
      this.errorMessage = 'Identifiant de quête ou de monde manquant.';
      this.loading = false;
      return;
    }

    forkJoin({
      quest: this.worldService.getQuestById(questId),
      world: this.worldService.getWorldById(this.worldId),
      progress: this.progressService.loadProgress()
    }).pipe(takeUntilDestroyed(this.destroyRef)).subscribe({
      next: ({ quest, world }) => {
        const index = world.quests.findIndex(candidate => candidate.id === quest.id);
        if (index < 0) {
          this.errorMessage = 'Cette quête ne fait pas partie de ce monde.';
        } else if (index > 0 && !this.authService.isAdmin() &&
          !this.progressService.isQuestCompleted(quest.id) &&
          !this.progressService.isQuestCompleted(world.quests[index - 1].id)) {
          this.errorMessage = `Termine d’abord la quête « ${world.quests[index - 1].title} ».`;
        } else {
          this.quest = quest;
          this.code = this.progressService.getSavedQuestCode(quest.id) ?? quest.codeTemplate ?? '';
        }
        this.loading = false;
        this.cdr.detectChanges();
        if (this.quest) this.initMonaco();
      },
      error: () => {
        this.errorMessage = 'Impossible de charger la quête ou son journal.';
        this.loading = false;
      }
    });
  }

  ngAfterViewInit(): void {
    if (!this.loading && this.quest) {
      this.initMonaco();
    }
  }

  initMonaco(): void {
    setTimeout(() => {
      if (!this.editorContainer?.nativeElement.isConnected || this.editorInstance) return;

      if ((window as any).monaco?.editor) {
        this.createMonacoEditor();
      } else {
        this.loadMonacoScript().then(loaded => {
          if (loaded) this.createMonacoEditor();
        });
      }
    }, 50);
  }

  loadMonacoScript(): Promise<boolean> {
    return new Promise((resolve) => {
      if ((window as any).monaco?.editor) {
        resolve(true);
        return;
      }
      const onGotAmdLoader = () => {
        (window as any).require.config({ paths: { vs: 'https://cdnjs.cloudflare.com/ajax/libs/monaco-editor/0.45.0/min/vs' } });
        (window as any).require(['vs/editor/editor.main'], () => {
          resolve(true);
        }, () => resolve(false));
      };

      if ((window as any).require && (window as any).require.config) {
        onGotAmdLoader();
      } else {
        const loaderScript = document.createElement('script');
        loaderScript.type = 'text/javascript';
        loaderScript.src = 'https://cdnjs.cloudflare.com/ajax/libs/monaco-editor/0.45.0/min/vs/loader.min.js';
        loaderScript.addEventListener('load', onGotAmdLoader);
        loaderScript.addEventListener('error', () => resolve(false));
        document.body.appendChild(loaderScript);
      }
    });
  }

  createMonacoEditor(): void {
    if (!this.editorContainer?.nativeElement.isConnected || this.editorInstance) return;

    this.editorReady = true;
    this.cdr.detectChanges();
    const langMode = this.getEditorLanguage();
    this.editorInstance = (window as any).monaco.editor.create(this.editorContainer.nativeElement, {
      value: this.code,
      language: langMode,
      theme: 'vs-dark',
      ariaLabel: 'Ton code',
      automaticLayout: true,
      fontSize: 14,
      fontFamily: "'Fira Code', 'Cascadia Code', 'Consolas', monospace",
      minimap: { enabled: false },
      scrollBeyondLastLine: false,
      roundedSelection: true,
      quickSuggestions: true,
      suggestOnTriggerCharacters: true,
      autoClosingBrackets: 'always',
      autoClosingQuotes: 'always',
      formatOnType: true,
      formatOnPaste: true,
      lineNumbers: 'on',
      padding: { top: 14, bottom: 14 }
    });

    this.editorInstance.onDidChangeModelContent(() => this.updateCode(this.editorInstance.getValue()));
  }

  updateCode(code: string): void {
    this.code = code;
    if (this.quest) {
      this.progressService.saveQuestCode(this.quest.id, code);
    }
  }

  getEditorLanguage(): string {
    if (!this.quest || !this.quest.languages) return 'html';
    const l = this.quest.languages.toLowerCase();
    if (l.includes('html')) return 'html';
    if (l.includes('css')) return 'css';
    if (l.includes('javascript') || l.includes('js')) return 'javascript';
    if (l.includes('java')) return 'java';
    return 'html';
  }

  submitCode(): void {
    if (this.editorInstance) {
      this.code = this.editorInstance.getValue();
    }

    if (!this.quest || !this.code.trim() || this.submitting) return;

    this.submitting = true;
    this.result = null;

    this.progressSyncFailed = false;
    this.worldService.submitQuest(this.quest.id, this.code).pipe(
      switchMap(response => response.success
        ? this.progressService.loadProgress(true).pipe(
          map(() => ({ response, synced: true })),
          catchError(() => of({ response, synced: false }))
        )
        : of({ response, synced: true })),
      takeUntilDestroyed(this.destroyRef)
    ).subscribe({
      next: ({ response, synced }) => {
        this.result = response;
        this.progressSyncFailed = !synced;
        this.submitting = false;
      },
      error: (err) => {
        this.result = {
          success: false,
          output: "Impossible de valider la solution pour le moment.",
          xpGained: 0
        };
        this.submitting = false;
        console.error(err);
      }
    });
  }

  resetTemplate(): void {
    if (this.quest) {
      this.code = this.quest.codeTemplate || '';
      if (this.editorInstance) {
        this.editorInstance.setValue(this.code);
      }
      this.progressService.clearSavedQuestCode(this.quest.id);
      this.result = null;
    }
  }

  ngOnDestroy(): void {
    if (this.editorInstance) {
      this.editorInstance.dispose();
      this.editorInstance = null;
    }
  }
}

