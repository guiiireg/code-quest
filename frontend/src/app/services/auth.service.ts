import { Injectable, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { LoginRequest, SignupRequest, JwtResponse } from '../models/auth.model';

/**
 * Service managing user authentication (signin, signup, logout, session state).
 */
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = '/api/auth';
  
  // Signal holding the currently logged-in user details
  private currentUserSignal = signal<JwtResponse | null>(null);
  
  // Public readonly access to the current user signal
  readonly currentUser = this.currentUserSignal.asReadonly();
  
  // Computed signal indicating if a user is authenticated
  readonly isAuthenticated = computed(() => this.currentUser() !== null);

  // Computed signal determining if the user is an Administrator
  readonly isAdmin = computed(() => {
    const user = this.currentUser();
    if (!user) return false;
    return user.username.toLowerCase() === 'gui' || (user.roles && user.roles.includes('ROLE_ADMIN'));
  });

  constructor(private http: HttpClient) {
    this.loadUserFromStorage();
  }

  /**
   * Authenticates the user with credentials.
   * On success, persists user profile and JWT token locally.
   * 
   * @param credentials The login credentials
   * @returns Observable of JwtResponse
   */
  login(credentials: LoginRequest): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(`${this.apiUrl}/signin`, credentials).pipe(
      tap(response => this.saveUser(response))
    );
  }

  /**
   * Registers a new user account.
   * 
   * @param user Registration data
   * @returns Observable of success message response
   */
  register(user: SignupRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/signup`, user);
  }

  /**
   * Logs out the user and clears local storage session.
   */
  logout(): void {
    localStorage.removeItem('codequest_user');
    this.currentUserSignal.set(null);
  }

  /**
   * Retrieves the current JWT bearer token.
   * 
   * @returns Token string or null
   */
  getToken(): string | null {
    const user = this.currentUser();
    return user ? user.token : null;
  }

  /**
   * Loads user session from localStorage on application startup.
   */
  private loadUserFromStorage(): void {
    const storedUser = localStorage.getItem('codequest_user');
    if (storedUser) {
      try {
        const user = JSON.parse(storedUser) as JwtResponse;
        this.currentUserSignal.set(user);
      } catch (e) {
        console.error('Error parsing user session from local storage:', e);
        this.logout();
      }
    }
  }

  /**
   * Saves user session to localStorage.
   * 
   * @param user The user response object
   */
  private saveUser(user: JwtResponse): void {
    localStorage.setItem('codequest_user', JSON.stringify(user));
    this.currentUserSignal.set(user);
  }
}

