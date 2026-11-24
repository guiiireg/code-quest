import { Component, computed, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { RouterLink } from '@angular/router';
import { of, switchMap } from 'rxjs';
import { AuthService } from '../../services/auth.service';
import { LEVEL_THRESHOLDS, UserProgressService } from '../../services/user-progress.service';
import { WorldService } from '../../services/world.service';
import { AtlasQuest, World } from '../../models/world.model';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {
  readonly auth = inject(AuthService);
  readonly progress = inject(UserProgressService);
  readonly levels = LEVEL_THRESHOLDS;

  private readonly worldService = inject(WorldService);
  private readonly destroyRef = inject(DestroyRef);
  private readonly worlds = signal<World<AtlasQuest>[]>([]);
  readonly historyState = signal<'loading' | 'ready' | 'unavailable'>('ready');

  readonly monogram = computed(() => Array.from(this.auth.currentUser()?.username.trim() || '?')[0].toLocaleUpperCase('fr'));
  readonly remainingXp = computed(() => Math.max(0, (this.progress.nextLevelInfo()?.minXp ?? 0) - this.progress.progress().xp));

  readonly recentQuests = computed(() => {
    const catalogue = new Map(this.worlds().flatMap(world =>
      world.quests.map(quest => [quest.id, { quest, world }] as const)
    ));

    return this.progress.progress().completedQuests
      .slice().reverse()
      .map(id => catalogue.get(id))
      .filter(entry => entry !== undefined)
      .slice(0, 6);
  });

  ngOnInit(): void {
    this.historyState.set('loading');
    this.progress.loadProgress(true).pipe(
      switchMap(progress => progress.completedQuests.length ? this.worldService.getAllWorlds() : of([])),
      takeUntilDestroyed(this.destroyRef)
    ).subscribe({
      next: worlds => {
        this.worlds.set(worlds);
        this.historyState.set('ready');
      },
      error: () => this.historyState.set('unavailable')
    });
  }
}
