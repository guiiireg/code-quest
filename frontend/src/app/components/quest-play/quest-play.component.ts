import { Component, OnInit, AfterViewInit, OnDestroy, ViewChild, ElementRef, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { WorldService } from '../../services/world.service';
import { UserProgressService } from '../../services/user-progress.service';
import { Quest, SubmissionResponse } from '../../models/world.model';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { FormsModule } from '@angular/forms';

import { AuthService } from '../../services/auth.service';

/**
 * Espace d'Épreuve / Playground de Code RPG avec Éditeur Monaco (VSCode).
 */
@Component({
  selector: 'app-quest-play',
  standalone: true,
  imports: [
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    RouterModule,
    FormsModule
  ],
  templateUrl: './quest-play.component.html',
  styleUrl: './quest-play.component.css'
})
export class QuestPlayComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild('editorContainer') editorContainer!: ElementRef<HTMLDivElement>;

  worldId: string | null = null;
  quest: Quest | null = null;
  code: string = '';
  loading: boolean = true;
  submitting: boolean = false;
  errorMessage: string = '';
  result: SubmissionResponse | null = null;

  private editorInstance: any = null;

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

    if (questId) {
      this.worldService.getQuestById(questId).subscribe({
        next: (data) => {
          this.quest = data;
          const savedCode = this.progressService.getSavedQuestCode(data.id);
          this.code = (savedCode !== null && savedCode !== undefined) ? savedCode : (data.codeTemplate || '');
          
          // Vérification de la progression séquentielle (ignorée si l'utilisateur est Admin)
          if (this.worldId && !this.authService.isAdmin()) {
            this.worldService.getWorldById(this.worldId).subscribe({
              next: (worldData) => {
                const idx = worldData.quests.findIndex(q => q.id === data.id);
                if (idx > 0) {
                  const prevQuest = worldData.quests[idx - 1];
                  const isCompletedCurrent = this.progressService.isQuestCompleted(data.id);
                  const isCompletedPrev = this.progressService.isQuestCompleted(prevQuest.id);
                  if (!isCompletedPrev && !isCompletedCurrent) {
                    this.errorMessage = `Cette épreuve est verrouillée ! Vous devez d'abord accomplir la quête précédente : "${prevQuest.title}".`;
                  }
                }
                this.loading = false;
                this.cdr.detectChanges();
                this.initMonaco();
              },
              error: () => {
                this.loading = false;
                this.cdr.detectChanges();
                this.initMonaco();
              }
            });
          } else {
            this.loading = false;
            this.cdr.detectChanges();
            this.initMonaco();
          }
        },
        error: (err) => {
          this.errorMessage = "Impossible de charger les instructions de la quête.";
          this.loading = false;
          console.error(err);
        }
      });
    } else {
      this.errorMessage = "Identifiants de la quête manquants.";
      this.loading = false;
    }
  }

  ngAfterViewInit(): void {
    if (!this.loading && this.quest) {
      this.initMonaco();
    }
  }

  initMonaco(): void {
    setTimeout(() => {
      if (!this.editorContainer || this.editorInstance) return;

      if ((window as any).monaco) {
        this.createMonacoEditor();
      } else {
        this.loadMonacoScript().then(() => {
          this.createMonacoEditor();
        });
      }
    }, 50);
  }

  loadMonacoScript(): Promise<void> {
    return new Promise((resolve) => {
      if ((window as any).monaco) {
        resolve();
        return;
      }
      const onGotAmdLoader = () => {
        (window as any).require.config({ paths: { vs: 'https://cdnjs.cloudflare.com/ajax/libs/monaco-editor/0.45.0/min/vs' } });
        (window as any).require(['vs/editor/editor.main'], () => {
          resolve();
        });
      };

      if ((window as any).require && (window as any).require.config) {
        onGotAmdLoader();
      } else {
        const loaderScript = document.createElement('script');
        loaderScript.type = 'text/javascript';
        loaderScript.src = 'https://cdnjs.cloudflare.com/ajax/libs/monaco-editor/0.45.0/min/vs/loader.min.js';
        loaderScript.addEventListener('load', onGotAmdLoader);
        document.body.appendChild(loaderScript);
      }
    });
  }

  createMonacoEditor(): void {
    if (!this.editorContainer || this.editorInstance) return;

    const langMode = this.getEditorLanguage();
    this.editorInstance = (window as any).monaco.editor.create(this.editorContainer.nativeElement, {
      value: this.code,
      language: langMode,
      theme: 'vs-dark',
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

    this.editorInstance.onDidChangeModelContent(() => {
      this.code = this.editorInstance.getValue();
      if (this.quest) {
        this.progressService.saveQuestCode(this.quest.id, this.code);
      }
    });
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

    if (!this.quest || !this.code.trim()) return;

    this.submitting = true;
    this.result = null;

    this.worldService.submitQuest(this.quest.id, this.code).subscribe({
      next: (res) => {
        this.result = res;
        this.submitting = false;
        if (res.success && this.quest) {
          this.progressService.completeQuest(this.quest.id, res.xpGained || this.quest.xpReward);
        }
      },
      error: (err) => {
        this.result = {
          success: false,
          output: "Erreur lors de l'évaluation du code par le compilateur.",
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
      this.progressService.clearSavedQuestCode(this.quest.id);
      if (this.editorInstance) {
        this.editorInstance.setValue(this.code);
      }
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
