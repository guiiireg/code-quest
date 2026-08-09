import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { UserProgressService, LEVEL_THRESHOLDS } from '../../services/user-progress.service';
import { JwtResponse } from '../../models/auth.model';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { RouterModule } from '@angular/router';

/**
 * Composant de la Fiche de Personnage RPG du joueur.
 */
@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    MatProgressBarModule,
    RouterModule
  ],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {
  userSession: JwtResponse | null = null;
  levelThresholds = LEVEL_THRESHOLDS;

  constructor(
    public authService: AuthService,
    public progressService: UserProgressService
  ) {}

  ngOnInit(): void {
    this.userSession = this.authService.currentUser();
  }
}
