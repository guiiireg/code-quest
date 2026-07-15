import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { JwtResponse } from '../../models/auth.model';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { RouterModule } from '@angular/router';

/**
 * Composant pour afficher les informations de profil de l'utilisateur connecté.
 */
@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    RouterModule
  ],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {
  userSession: JwtResponse | null = null;

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    // Récupère l'utilisateur depuis le service
    this.userSession = this.authService.currentUser();
  }
}
