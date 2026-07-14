import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { World } from '../models/world.model';

/**
 * Service pour interagir avec les endpoints de l'API /api/worlds.
 */
@Injectable({
  providedIn: 'root'
})
export class WorldService {
  private apiUrl = '/api/worlds';

  constructor(private http: HttpClient) {}

  /**
   * Récupère la liste de tous les mondes disponibles.
   * 
   * @returns Un Observable contenant un tableau de World
   */
  getAllWorlds(): Observable<World[]> {
    return this.http.get<World[]>(this.apiUrl);
  }

  /**
   * Récupère un monde spécifique par son identifiant unique.
   * 
   * @param id L'identifiant unique du monde à rechercher
   * @returns Un Observable contenant le World correspondant
   */
  getWorldById(id: string): Observable<World> {
    return this.http.get<World>(`${this.apiUrl}/${id}`);
  }
}
