/**
 * Represents a quest model in the frontend.
 */
export interface Quest {
  id: string;
  title: string;
  description: string;
  xpReward: number;
  difficulty: string;
  codeTemplate?: string;
  testValidationRegex?: string;
  category?: string;
  languages?: string;
  concept?: string;
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
export interface World {
  id: string;
  name: string;
  description: string;
  quests: Quest[];
}

