/**
 * Login request payload.
 */
export interface LoginRequest {
  username: string;
  password: string;
}

/**
 * Registration request payload.
 */
export interface SignupRequest {
  username: string;
  email: string;
  password: string;
  roles?: string[];
}

/**
 * Server authentication response containing the JWT bearer token.
 */
export interface JwtResponse {
  token: string;
  id: string;
  username: string;
  email: string;
  roles: string[];
}

