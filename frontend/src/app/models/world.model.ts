/**
 * Représente une quête dans le frontend.
 */
export interface Quest {
  id: string;
  title: string;
  description: string;
  xpReward: number;
  difficulty: string;
  codeTemplate?: string;
  testValidationRegex?: string;
}

/**
 * Représente la réponse de soumission de code.
 */
export interface SubmissionResponse {
  success: boolean;
  output: string;
  xpGained: number;
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
