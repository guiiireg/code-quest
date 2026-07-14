/**
 * Représente une quête dans le frontend.
 */
export interface Quest {
  id: string;
  title: string;
  description: string;
  xpReward: number;
  difficulty: string;
}

/**
 * Représente un monde dans le frontend.
 */
export interface World {
  id: string;
  name: string;
  description: string;
  quests: Quest[];
}
