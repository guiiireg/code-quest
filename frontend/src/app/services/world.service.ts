import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { World, Quest, SubmissionResponse } from '../models/world.model';

/**
 * Service to interact with the /api world and quest endpoints.
 */
@Injectable({
  providedIn: 'root'
})
export class WorldService {
  private apiUrl = '/api/worlds';

  constructor(private http: HttpClient) {}

  /**
   * Retrieves all available worlds.
   * 
   * @returns An Observable containing an array of World objects
   */
  getAllWorlds(): Observable<World[]> {
    return this.http.get<World[]>(this.apiUrl);
  }

  /**
   * Retrieves a specific world by its unique identifier.
   * 
   * @param id The unique world identifier
   * @returns An Observable containing the corresponding World
   */
  getWorldById(id: string): Observable<World> {
    return this.http.get<World>(`${this.apiUrl}/${id}`);
  }

  /**
   * Retrieves details of a specific quest by its unique identifier.
   * 
   * @param id The unique quest identifier
   * @returns An Observable containing the Quest details
   */
  getQuestById(id: string): Observable<Quest> {
    return this.http.get<Quest>(`/api/quests/${id}`);
  }

  /**
   * Submits user solution code for validation and evaluation.
   * 
   * @param id The unique quest identifier
   * @param code The submitted source code string
   * @returns An Observable containing the SubmissionResponse
   */
  submitQuest(id: string, code: string): Observable<SubmissionResponse> {
    return this.http.post<SubmissionResponse>(`/api/quests/${id}/submit`, { code });
  }
}

