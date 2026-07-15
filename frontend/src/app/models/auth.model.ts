/**
 * Requête pour l'authentification (connexion).
 */
export interface LoginRequest {
  username: string;
  password: string;
}

/**
 * Requête pour l'enregistrement (inscription).
 */
export interface SignupRequest {
  username: string;
  email: string;
  password: string;
  roles?: string[];
}

/**
 * Réponse du serveur après une authentification réussie contenant le jeton JWT.
 */
export interface JwtResponse {
  token: string;
  id: string;
  username: string;
  email: string;
  roles: string[];
}
