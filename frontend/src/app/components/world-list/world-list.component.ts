import { Component, OnInit } from '@angular/core';
import { WorldService } from '../../services/world.service';
import { World } from '../../models/world.model';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

/**
 * Composant qui affiche la liste des mondes sous forme de cartes Material.
 */
@Component({
  selector: 'app-world-list',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, MatIconModule],
  templateUrl: './world-list.component.html',
  styleUrl: './world-list.component.css'
})
export class WorldListComponent implements OnInit {
  worlds: World[] = [];
  errorMessage: string = '';
  loading: boolean = true;

  constructor(private worldService: WorldService) {}

  ngOnInit(): void {
    this.worldService.getAllWorlds().subscribe({
      next: (data) => {
        this.worlds = data;
        this.loading = false;
      },
      error: (err) => {
        this.errorMessage = "Impossible de charger les mondes. Veuillez réessayer plus tard.";
        this.loading = false;
        console.error(err);
      }
    });
  }
}
