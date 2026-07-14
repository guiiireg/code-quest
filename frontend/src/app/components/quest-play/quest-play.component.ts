import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { WorldService } from '../../services/world.service';
import { Quest, SubmissionResponse } from '../../models/world.model';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { FormsModule } from '@angular/forms';

/**
 * Composant de l'espace de jeu et d'exercice (Playground).
 * Permet à l'utilisateur de lire les détails d'une quête, de rédiger du code
 * et de le soumettre pour évaluation.
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
export class QuestPlayComponent implements OnInit {
  worldId: string | null = null;
  quest: Quest | null = null;
  code: string = '';
  loading: boolean = true;
  submitting: boolean = false;
  errorMessage: string = '';
  result: SubmissionResponse | null = null;

  constructor(
    private route: ActivatedRoute,
    private worldService: WorldService
  ) {}

  ngOnInit(): void {
    this.worldId = this.route.snapshot.paramMap.get('worldId');
    const questId = this.route.snapshot.paramMap.get('questId');

    if (questId) {
      this.worldService.getQuestById(questId).subscribe({
        next: (data) => {
          this.quest = data;
          this.code = data.codeTemplate || '';
          this.loading = false;
        },
        error: (err) => {
          this.errorMessage = "Impossible de charger les détails de la quête.";
          this.loading = false;
          console.error(err);
        }
      });
    } else {
      this.errorMessage = "Identifiants de la quête manquants.";
      this.loading = false;
    }
  }

  submitCode(): void {
    if (!this.quest || !this.code.trim()) return;

    this.submitting = true;
    this.result = null;

    this.worldService.submitQuest(this.quest.id, this.code).subscribe({
      next: (res) => {
        this.result = res;
        this.submitting = false;
      },
      error: (err) => {
        this.result = {
          success: false,
          output: "Erreur serveur lors de la soumission de votre code.",
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
      this.result = null;
    }
  }
}
