import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { WorldService } from '../../services/world.service';
import { World } from '../../models/world.model';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';

/**
 * Composant de détail affichant les détails d'un monde et ses quêtes associées.
 */
@Component({
  selector: 'app-world-detail',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, MatIconModule, MatDividerModule, RouterModule],
  templateUrl: './world-detail.component.html',
  styleUrl: './world-detail.component.css'
})
export class WorldDetailComponent implements OnInit {
  world: World | null = null;
  loading: boolean = true;
  errorMessage: string = '';

  constructor(
    private route: ActivatedRoute,
    private worldService: WorldService
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
      this.errorMessage = "ID du monde manquant.";
      this.loading = false;
    }
  }
}
