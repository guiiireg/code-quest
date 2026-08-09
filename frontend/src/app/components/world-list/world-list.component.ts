import { Component, OnInit } from '@angular/core';
import { WorldService } from '../../services/world.service';
import { AuthService } from '../../services/auth.service';
import { UserProgressService } from '../../services/user-progress.service';
import { World, Quest } from '../../models/world.model';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatDividerModule } from '@angular/material/divider';
import { MatTooltipModule } from '@angular/material/tooltip';
import { RouterModule } from '@angular/router';

/**
 * Dashboard principal du joueur : Interface de progression RPG moderne.
 */
@Component({
  selector: 'app-world-list',
  standalone: true,
  imports: [
    MatCardModule, 
    MatButtonModule, 
    MatIconModule, 
    MatProgressBarModule,
    MatDividerModule,
    MatTooltipModule,
    RouterModule
  ],
  templateUrl: './world-list.component.html',
  styleUrl: './world-list.component.css'
})
export class WorldListComponent implements OnInit {
  worlds: World[] = [];
  errorMessage: string = '';
  loading: boolean = true;
  activeQuest: Quest | null = null;
  activeWorldId: string = 'world-1';

  constructor(
    private worldService: WorldService,
    public authService: AuthService,
    public progressService: UserProgressService
  ) {}

  ngOnInit(): void {
    this.worldService.getAllWorlds().subscribe({
      next: (data) => {
        this.worlds = data;
        this.findActiveQuest(data);
        this.loading = false;
      },
      error: (err) => {
        this.errorMessage = "Impossible de charger les territoires de CodeQuest. Veuillez vérifier votre connexion au serveur.";
        this.loading = false;
        console.error(err);
      }
    });
  }

  /**
   * Trouve la quête active recommandée pour le joueur.
   */
  private findActiveQuest(worlds: World[]): void {
    const completed = this.progressService.progress().completedQuests;
    for (const world of worlds) {
      for (const quest of world.quests) {
        if (!completed.includes(quest.id)) {
          this.activeQuest = quest;
          this.activeWorldId = world.id;
          return;
        }
      }
    }
    // Si toutes les quêtes sont accomplies, prendre la première
    if (worlds.length > 0 && worlds[0].quests.length > 0) {
      this.activeQuest = worlds[0].quests[0];
      this.activeWorldId = worlds[0].id;
    }
  }

  getCompletedQuestCountInWorld(world: World): number {
    return world.quests.filter(q => this.progressService.isQuestCompleted(q.id)).length;
  }

  getWorldProgressPercentage(world: World): number {
    if (!world.quests || world.quests.length === 0) return 0;
    const done = this.getCompletedQuestCountInWorld(world);
    return Math.round((done / world.quests.length) * 100);
  }
}
