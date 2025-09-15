CREATE TABLE meter (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    type VARCHAR(50) NOT NULL,
    unit VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    name VARCHAR(255),
    location VARCHAR(255),
    CONSTRAINT fk_meter_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
