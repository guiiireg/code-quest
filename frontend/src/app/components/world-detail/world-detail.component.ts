import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { WorldService } from '../../services/world.service';
import { UserProgressService } from '../../services/user-progress.service';
import { World, Quest } from '../../models/world.model';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatTooltipModule } from '@angular/material/tooltip';
import { AuthService } from '../../services/auth.service';

export interface SubRegionGroup {
  name: string;
  quests: Quest[];
  completedCount: number;
  totalCount: number;
  totalXp: number;
}

/**
 * Page de détail d'une région / monde affichant le journal de quêtes.
 */
@Component({
  selector: 'app-world-detail',
  standalone: true,
  imports: [
    MatCardModule, 
    MatButtonModule, 
    MatIconModule, 
    MatDividerModule, 
    MatTooltipModule,
    RouterModule
  ],
  templateUrl: './world-detail.component.html',
  styleUrl: './world-detail.component.css'
})
export class WorldDetailComponent implements OnInit {
  world: World | null = null;
  loading: boolean = true;
  errorMessage: string = '';
  collapsedSubregions: Set<string> = new Set<string>();

  constructor(
    private route: ActivatedRoute,
    private worldService: WorldService,
    public progressService: UserProgressService,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    const worldId = this.route.snapshot.paramMap.get('id');
    if (worldId) {
      this.worldService.getWorldById(worldId).subscribe({
        next: (data) => {
          this.world = data;
          this.loading = false;
        },
        error: (err) => {
          this.errorMessage = "Monde introuvable ou erreur de chargement.";
          this.loading = false;
          console.error(err);
        }
      });
    } else {
      this.errorMessage = "ID de la région manquant.";
      this.loading = false;
    }
  }

  isCompleted(questId: string): boolean {
    return this.progressService.isQuestCompleted(questId);
  }

  isQuestUnlocked(quest: Quest): boolean {
    if (this.authService.isAdmin()) return true; // L'administrateur (ex: 'gui') a toutes les quêtes débloquées sans restriction !

    if (!this.world || !this.world.quests) return false;
    if (this.isCompleted(quest.id)) return true;

    const index = this.world.quests.findIndex(q => q.id === quest.id);
    if (index <= 0) return true; // La première quête est toujours débloquée

    const previousQuest = this.world.quests[index - 1];
    return this.isCompleted(previousQuest.id);
  }

  getPreviousQuestTitle(quest: Quest): string {
    if (!this.world || !this.world.quests) return '';
    const index = this.world.quests.findIndex(q => q.id === quest.id);
    if (index > 0) {
      return this.world.quests[index - 1].title;
    }
    return '';
  }

  toggleSubregion(groupName: string): void {
    if (this.collapsedSubregions.has(groupName)) {
      this.collapsedSubregions.delete(groupName);
    } else {
      this.collapsedSubregions.add(groupName);
    }
  }

  isSubregionCollapsed(groupName: string): boolean {
    return this.collapsedSubregions.has(groupName);
  }

  getLanguagesList(languages?: string): string[] {
    if (!languages) return [];
    return languages.split(',').map(l => l.trim()).filter(l => l.length > 0);
  }

  getCompletedQuestCount(): number {
    if (!this.world) return 0;
    return this.world.quests.filter(q => this.isCompleted(q.id)).length;
  }

  getTotalXp(): number {
    if (!this.world) return 0;
    return this.world.quests.reduce((sum, q) => sum + q.xpReward, 0);
  }

  getCategoryForQuest(quest: Quest): string {
    if (quest.category && quest.category.trim().length > 0) {
      return quest.category;
    }
    const match = quest.title.match(/^(\d+)\./);
    if (match) {
      const num = parseInt(match[1], 10);
      const categoryMap: { [key: number]: string } = {
        1: '1. Structure ancestrale',
        2: '2. Le Grimoire du <head>',
        3: '3. Texte & hiérarchie',
        4: '4. Listes',
        5: '5. Liens & navigation',
        6: '6. Médias',
        7: '7. Sémantique HTML5',
        8: '8. Formulaires',
        9: '9. Tableaux',
        10: '10. Éléments interactifs natifs',
        11: '11. Accessibilité',
        12: '12. Performance',
        13: '13. Sécurité',
        14: '14. Qualité',
        15: '15. ✨ Quêtes Annexes HTML5',
        16: '16. 🎓 Quiz & Évaluation des Connaissances HTML5'
      };
      if (categoryMap[num]) {
        return categoryMap[num];
      }
    }
    return 'Quêtes Principales';
  }

  get subRegionGroups(): SubRegionGroup[] {
    if (!this.world || !this.world.quests) return [];

    const groupsMap = new Map<string, Quest[]>();

    for (const quest of this.world.quests) {
      const cat = this.getCategoryForQuest(quest);
      if (!groupsMap.has(cat)) {
        groupsMap.set(cat, []);
      }
      groupsMap.get(cat)!.push(quest);
    }

    const groups = Array.from(groupsMap.entries()).map(([name, quests]) => {
      const completedCount = quests.filter(q => this.isCompleted(q.id)).length;
      const totalXp = quests.reduce((sum, q) => sum + q.xpReward, 0);
      return {
        name,
        quests,
        completedCount,
        totalCount: quests.length,
        totalXp
      };
    });

    groups.sort((a, b) => {
      const matchA = a.name.match(/^(\d+)/);
      const matchB = b.name.match(/^(\d+)/);
      if (matchA && matchB) {
        return parseInt(matchA[1], 10) - parseInt(matchB[1], 10);
      }
      return a.name.localeCompare(b.name);
    });

    return groups;
  }
}
