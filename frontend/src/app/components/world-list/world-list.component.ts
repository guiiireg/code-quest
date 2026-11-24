import { Component, computed, OnInit, signal } from '@angular/core';
import { WorldService } from '../../services/world.service';
import { AuthService } from '../../services/auth.service';
import { UserProgressService } from '../../services/user-progress.service';
import { AtlasQuest, World } from '../../models/world.model';
import { RouterModule } from '@angular/router';
import { forkJoin } from 'rxjs';

const BRANCHES = [
  { id: 'world-1', label: 'HTML5', direction: 'left' },
  { id: 'world-2', label: 'Accessibilité', direction: 'top' },
  { id: 'world-3', label: 'CSS3', direction: 'right' }
];

/**
 * Main player dashboard: Modern RPG progression map interface.
 */
@Component({
  selector: 'app-world-list',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './world-list.component.html',
  styleUrl: './world-list.component.css'
})
export class WorldListComponent implements OnInit {
  private readonly worlds = signal<World<AtlasQuest>[]>([]);
  private readonly selectedWorldId = signal('world-1');

  readonly branches = computed(() => BRANCHES.map(branch => {
    const world = this.worlds().find(candidate => candidate.id === branch.id);
    const quests = world?.quests ?? [];
    const completedIds = this.progressService.progress().completedQuests;
    const completed = quests.filter(quest => completedIds.includes(quest.id)).length;
    return { ...branch, world, completed, total: quests.length, fraction: quests.length ? completed / quests.length : 0 };
  }));

  readonly selectedBranch = computed(() => this.branches().find(branch => branch.id === this.selectedWorldId() && branch.world)
    ?? this.branches().find(branch => branch.world));

  readonly overallProgress = computed(() => Math.round(
    100 * this.branches().reduce((sum, branch) => sum + branch.fraction, 0) / BRANCHES.length
  ));

  readonly recentQuests = computed(() => {
    const quests = this.selectedBranch()?.world?.quests ?? [];
    const questsById = new Map(quests.map(quest => [quest.id, quest]));
    return [...this.progressService.progress().completedQuests].reverse()
      .map(id => questsById.get(id))
      .filter(quest => quest !== undefined)
      .slice(0, 3);
  });

  readonly extraWorlds = computed(() => this.worlds().filter(world => !BRANCHES.some(branch => branch.id === world.id)));
  errorMessage: string = '';
  loading: boolean = true;
  activeQuest: AtlasQuest | null = null;
  activeWorldId: string = 'world-1';

  constructor(
    private worldService: WorldService,
    public authService: AuthService,
    public progressService: UserProgressService
  ) {}

  ngOnInit(): void {
    forkJoin({
      worlds: this.worldService.getAllWorlds(),
      progress: this.progressService.loadProgress(true)
    }).subscribe({
      next: ({ worlds: data }) => {
        this.worlds.set(data);
        this.findActiveQuest(data);
        const latest = this.progressService.progress().completedQuests.at(-1);
        const latestWorld = data.find(world => world.quests.some(quest => quest.id === latest));
        this.selectedWorldId.set(latestWorld?.id ?? this.activeWorldId);
        this.loading = false;
      },
      error: (err) => {
        this.errorMessage = "Impossible de charger les mondes. Vérifie la connexion au serveur, puis réessaie.";
        this.loading = false;
        console.error(err);
      }
    });
  }

  /**
   * Identifies the recommended active quest for the player.
   */
  private findActiveQuest(worlds: World<AtlasQuest>[]): void {
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
  }

  selectWorld(id: string): void {
    this.selectedWorldId.set(id);
  }
}
