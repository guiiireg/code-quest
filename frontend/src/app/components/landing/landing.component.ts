import { Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './landing.component.html',
  styleUrl: './landing.component.css'
})
export class LandingComponent {
  readonly auth = inject(AuthService);
  readonly worlds = [
    {
      name: 'HTML5',
      title: 'Les Archives',
      description: 'Structure tes pages avec le HTML sémantique.',
      image: '/landing/archives.webp'
    },
    {
      name: 'Accessibilité',
      title: 'Le Royaume de l’Inclusion',
      description: 'Crée des interfaces que tout le monde peut utiliser.',
      image: '/landing/inclusion.webp'
    },
    {
      name: 'CSS3',
      title: 'La Citadelle',
      description: 'Maîtrise les mises en page, les styles et les animations.',
      image: '/landing/citadel.webp'
    }
  ];

  readonly activeWorld = signal(0);
}
