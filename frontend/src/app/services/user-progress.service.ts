import { HttpClient } from '@angular/common/http';
import { Injectable, signal, computed } from '@angular/core';
import { Observable, of, shareReplay, tap } from 'rxjs';
import { JwtResponse } from '../models/auth.model';
import { AuthService } from './auth.service';

export interface UserProgressState {
  xp: number;
  completedQuests: string[];
}

const EMPTY_PROGRESS: UserProgressState = { xp: 0, completedQuests: [] };

export interface LevelThreshold {
  level: number;
  title: string;
  minXp: number;
  maxXp: number;
  rewardTitle: string;
}

export const LEVEL_THRESHOLDS: LevelThreshold[] = [
  { level: 1, title: 'Initié du Code', minXp: 0, maxXp: 200, rewardTitle: 'Badge Scribe Java' },
  { level: 2, title: 'Apprenti Développeur', minXp: 200, maxXp: 500, rewardTitle: 'Badge Architecte HTTP' },
  { level: 3, title: 'Compagnon du Code', minXp: 500, maxXp: 1000, rewardTitle: 'Titre Mage Algorithmique' },
  { level: 4, title: 'Archimage Java', minXp: 1000, maxXp: 2000, rewardTitle: 'Couronne du Chevalier REST' },
  { level: 5, title: 'Légende de CodeQuest', minXp: 2000, maxXp: 99999, rewardTitle: 'Maître Suprême du Realm' }
];

@Injectable({
  providedIn: 'root'
})
export class UserProgressService {
  private readonly progressState = signal<{ user: JwtResponse; progress: UserProgressState } | null>(null);
  private pending?: { user: JwtResponse; response: Observable<UserProgressState> };

  readonly progress = computed(() => {
    const state = this.progressState();
    return state && state.user === this.authService.currentUser() ? state.progress : EMPTY_PROGRESS;
  });

  readonly hasUnverifiedLegacyProgress = computed(() => {
    const user = this.authService.currentUser();
    const verified = this.progress();
    if (!user || this.progressState()?.user !== user) return false;

    try {
      const stored = localStorage.getItem(`codequest_progress_${user.username}`);
      const completed = stored ? JSON.parse(stored).completedQuests : null;
      return Array.isArray(completed) && completed.some(id => !verified.completedQuests.includes(id));
    } catch {
      return false;
    }
  });

  readonly currentLevelInfo = computed(() => {
    const xp = this.progress().xp;
    const current = LEVEL_THRESHOLDS.find(t => xp >= t.minXp && xp < t.maxXp) || LEVEL_THRESHOLDS[LEVEL_THRESHOLDS.length - 1];
    return current;
  });

  readonly nextLevelInfo = computed(() => {
    const currentLvl = this.currentLevelInfo().level;
    return LEVEL_THRESHOLDS.find(t => t.level === currentLvl + 1) || null;
  });

  readonly xpProgressPercentage = computed(() => {
    const xp = this.progress().xp;
    const info = this.currentLevelInfo();
    const range = info.maxXp - info.minXp;
    if (range <= 0) return 100;
    const currentInLevel = xp - info.minXp;
    return Math.min(100, Math.max(0, Math.round((currentInLevel / range) * 100)));
  });

  readonly completedCount = computed(() => this.progress().completedQuests.length);

  constructor(private authService: AuthService, private http: HttpClient) {}

  loadProgress(refresh = false): Observable<UserProgressState> {
    const user = this.authService.currentUser();
    if (!user) {
      return of(EMPTY_PROGRESS);
    }
    if (!refresh && this.pending?.user === user) {
      return this.pending.response;
    }

    const response = this.http.get<UserProgressState>('/api/progress').pipe(
      tap({
        next: progress => {
          if (this.authService.currentUser() === user && this.pending?.response === response) {
            this.progressState.set({ user, progress });
          }
        },
        error: () => {
          if (this.pending?.response === response) this.pending = undefined;
        }
      }),
      shareReplay({ bufferSize: 1, refCount: true })
    );
    this.pending = { user, response };
    return response;
  }

  isQuestCompleted(questId: string): boolean {
    return this.progress().completedQuests.includes(questId);
  }

  /**
   * Saves in-progress code draft for a quest in localStorage.
   */
  saveQuestCode(questId: string, code: string): void {
    const user = this.authService.currentUser();
    const prefix = user ? user.username : 'guest';
    localStorage.setItem(`codequest_draft_${prefix}_${questId}`, code);
  }

  /**
   * Retrieves saved in-progress code draft for a quest.
   */
  getSavedQuestCode(questId: string): string | null {
    const user = this.authService.currentUser();
    const prefix = user ? user.username : 'guest';
    return localStorage.getItem(`codequest_draft_${prefix}_${questId}`);
  }

  /**
   * Clears saved code draft to reset starter template.
   */
  clearSavedQuestCode(questId: string): void {
    const user = this.authService.currentUser();
    const prefix = user ? user.username : 'guest';
    localStorage.removeItem(`codequest_draft_${prefix}_${questId}`);
  }
}
