CREATE TABLE quest_completions (
    completion_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    quest_id VARCHAR(255) NOT NULL REFERENCES quests(id) ON DELETE RESTRICT,
    xp_awarded INTEGER NOT NULL CHECK (xp_awarded >= 0),
    UNIQUE (user_id, quest_id)
);

CREATE INDEX quest_completions_user_history ON quest_completions (user_id, completion_id);
