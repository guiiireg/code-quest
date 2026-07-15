import { Component } from '@angular/core';
import { RouterOutlet, RouterModule } from '@angular/router';
import { TitleCasePipe } from '@angular/common';
import { AuthService } from './services/auth.service';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatDividerModule } from '@angular/material/divider';

/**
 * Composant racine de l'application gérant la barre de navigation globale.
 */
@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterModule, TitleCasePipe, MatButtonModule, MatIconModule, MatMenuModule, MatDividerModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'CodeQuest';

  constructor(public authService: AuthService) {}

  /**
   * Déconnecte l'utilisateur actuel.
   */
  logout(): void {
    this.authService.logout();
  }
}
