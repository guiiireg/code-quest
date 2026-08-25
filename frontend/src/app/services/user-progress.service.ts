import { Injectable, signal, computed } from '@angular/core';
import { AuthService } from './auth.service';

export interface UserProgressState {
  xp: number;
  level: number;
  completedQuests: string[];
  unlockedSkills: string[];
  activeQuestId: string | null;
}

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
  private progressSignal = signal<UserProgressState>({
    xp: 0,
    level: 1,
    completedQuests: [],
    unlockedSkills: ['Syntaxe de base'],
    activeQuestId: 'q1'
  });

  readonly progress = this.progressSignal.asReadonly();

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

  constructor(private authService: AuthService) {
    this.loadProgress();
  }

  /**
   * Marks a quest as completed and awards experience points.
   */
  completeQuest(questId: string, xpReward: number): void {
    const current = this.progressSignal();
    const isNewCompletion = !current.completedQuests.includes(questId);
    
    const newCompleted = isNewCompletion 
      ? [...current.completedQuests, questId] 
      : current.completedQuests;
    
    const newXp = current.xp + (isNewCompletion ? xpReward : 0);
    const newLevelInfo = LEVEL_THRESHOLDS.find(t => newXp >= t.minXp && newXp < t.maxXp) || LEVEL_THRESHOLDS[LEVEL_THRESHOLDS.length - 1];

    const updatedSkills = [...current.unlockedSkills];
    if (questId === 'q1' && !updatedSkills.includes('Variables Java')) updatedSkills.push('Variables Java');
    if (questId === 'q2' && !updatedSkills.includes('Logique Booleenne')) updatedSkills.push('Logique Booleenne');
    if (questId === 'q3' && !updatedSkills.includes('Design REST & HTTP')) updatedSkills.push('Design REST & HTTP');

    const updatedState: UserProgressState = {
      ...current,
      xp: newXp,
      level: newLevelInfo.level,
      completedQuests: newCompleted,
      unlockedSkills: updatedSkills,
      activeQuestId: questId === 'q1' ? 'q2' : questId === 'q2' ? 'q3' : null
    };

    this.progressSignal.set(updatedState);
    this.saveProgress(updatedState);
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

  private loadProgress(): void {
    const user = this.authService.currentUser();
    const key = user ? `codequest_progress_${user.username}` : 'codequest_progress_default';
    const stored = localStorage.getItem(key);
    if (stored) {
      try {
        const parsed = JSON.parse(stored);
        this.progressSignal.set(parsed);
      } catch (e) {
        console.error('Error loading progress from storage:', e);
      }
    }
  }

  private saveProgress(state: UserProgressState): void {
    const user = this.authService.currentUser();
    const key = user ? `codequest_progress_${user.username}` : 'codequest_progress_default';
    localStorage.setItem(key, JSON.stringify(state));
  }
}

