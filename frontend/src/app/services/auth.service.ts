import { Injectable, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { LoginRequest, SignupRequest, JwtResponse } from '../models/auth.model';

/**
 * Service pour gérer l'authentification des utilisateurs (connexion, inscription, déconnexion).
 */
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = '/api/auth';
  
  // Utilisation d'un Signal pour stocker l'utilisateur actuellement connecté
  private currentUserSignal = signal<JwtResponse | null>(null);
  
  // Exposer l'utilisateur courant de manière publique et en lecture seule
  readonly currentUser = this.currentUserSignal.asReadonly();
  
  // Signal calculé pour savoir si l'utilisateur est connecté
  readonly isAuthenticated = computed(() => this.currentUser() !== null);

  // Signal calculé pour savoir si l'utilisateur est Administrateur (pseudo 'gui' ou rôle ROLE_ADMIN)
  readonly isAdmin = computed(() => {
    const user = this.currentUser();
    if (!user) return false;
    return user.username.toLowerCase() === 'gui' || (user.roles && user.roles.includes('ROLE_ADMIN'));
  });

  constructor(private http: HttpClient) {
    this.loadUserFromStorage();
  }

  /**
   * Tente de connecter l'utilisateur avec ses identifiants.
   * En cas de succès, stocke les informations et le jeton en local.
   * 
   * @param credentials Les identifiants de connexion
   * @returns Un Observable de JwtResponse
   */
  login(credentials: LoginRequest): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(`${this.apiUrl}/signin`, credentials).pipe(
      tap(response => this.saveUser(response))
    );
  }

  /**
   * Enregistre un nouvel utilisateur.
   * 
   * @param user Les données d'inscription de l'utilisateur
   * @returns Un Observable contenant le message de succès
   */
  register(user: SignupRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/signup`, user);
  }

  /**
   * Déconnecte l'utilisateur en effaçant les données du stockage local.
   */
  logout(): void {
    localStorage.removeItem('codequest_user');
    this.currentUserSignal.set(null);
  }

  /**
   * Récupère le jeton JWT actuel de l'utilisateur connecté.
   * 
   * @returns Le jeton sous forme de chaîne de caractères ou null
   */
  getToken(): string | null {
    const user = this.currentUser();
    return user ? user.token : null;
  }

  /**
   * Charge l'utilisateur depuis le localStorage au démarrage de l'application.
   */
  private loadUserFromStorage(): void {
    const storedUser = localStorage.getItem('codequest_user');
    if (storedUser) {
      try {
        const user = JSON.parse(storedUser) as JwtResponse;
        this.currentUserSignal.set(user);
      } catch (e) {
        console.error("Erreur lors de la lecture de la session utilisateur :", e);
        this.logout();
      }
    }
  }

  /**
   * Sauvegarde les informations de l'utilisateur dans le stockage local.
   * 
   * @param user Les informations de l'utilisateur à stocker
   */
  private saveUser(user: JwtResponse): void {
    localStorage.setItem('codequest_user', JSON.stringify(user));
    this.currentUserSignal.set(user);
  }
}
