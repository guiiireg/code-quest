CREATE TABLE IF NOT EXISTS worlds (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS quests (
    id VARCHAR(255) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    xp_reward INTEGER NOT NULL,
    difficulty VARCHAR(50) NOT NULL,
    world_id VARCHAR(255),
    CONSTRAINT fk_world FOREIGN KEY (world_id) REFERENCES worlds(id) ON DELETE CASCADE
);
