import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { AtlasQuest, World, Quest, SubmissionResponse } from '../models/world.model';

const questOrder = new Intl.Collator('fr', { numeric: true });

function orderQuests<T extends AtlasQuest>(world: World<T>): World<T> {
  return {
    ...world,
    quests: [...world.quests].sort((left, right) => questOrder.compare(left.id, right.id))
  };
}

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
  getAllWorlds(): Observable<World<AtlasQuest>[]> {
    return this.http.get<World<AtlasQuest>[]>(this.apiUrl).pipe(
      map(worlds => worlds.map(orderQuests).sort((left, right) => questOrder.compare(left.id, right.id)))
    );
  }

  /**
   * Retrieves a specific world by its unique identifier.
   * 
   * @param id The unique world identifier
   * @returns An Observable containing the corresponding World
   */
  getWorldById(id: string): Observable<World> {
    return this.http.get<World>(`${this.apiUrl}/${id}`).pipe(map(orderQuests));
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

