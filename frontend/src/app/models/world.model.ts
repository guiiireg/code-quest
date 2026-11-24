/**
 * Represents a quest model in the frontend.
 */
export interface AtlasQuest {
  id: string;
  title: string;
  xpReward: number;
  concept?: string;
}

export interface JournalQuest extends AtlasQuest {
  description: string;
  difficulty: string;
  category?: string;
  languages?: string;
}

export interface Quest extends JournalQuest {
  codeTemplate?: string;
  theory?: string;
}

/**
 * Represents a code submission evaluation response.
 */
export interface SubmissionResponse {
  success: boolean;
  output: string;
  xpGained: number;
}

/**
 * Represents a world/realm model in the frontend.
 */
export interface World<T extends AtlasQuest = JournalQuest> {
  id: string;
  name: string;
  description: string;
  quests: T[];
}

