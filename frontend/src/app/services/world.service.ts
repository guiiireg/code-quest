import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { World, Quest, SubmissionResponse } from '../models/world.model';

/**
 * Service pour interagir avec les endpoints de l'API /api.
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

  /**
   * Récupère les détails d'une quête spécifique par son identifiant unique.
   * 
   * @param id L'identifiant unique de la quête
   * @returns Un Observable contenant les détails de la Quest
   */
  getQuestById(id: string): Observable<Quest> {
    return this.http.get<Quest>(`/api/quests/${id}`);
  }

  /**
   * Soumet le code de l'utilisateur pour évaluation et validation.
   * 
   * @param id L'identifiant unique de la quête
   * @param code Le code source soumis par l'utilisateur
   * @returns Un Observable contenant la réponse d'évaluation SubmissionResponse
   */
  submitQuest(id: string, code: string): Observable<SubmissionResponse> {
    return this.http.post<SubmissionResponse>(`/api/quests/${id}/submit`, { code });
  }
}
