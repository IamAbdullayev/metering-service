CREATE TABLE meter_reading (
    id UUID PRIMARY KEY,
    meter_id UUID NOT NULL,
    user_id UUID NOT NULL,
    value NUMERIC(19,3) NOT NULL,
    reading_date DATE NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_reading_meter FOREIGN KEY (meter_id) REFERENCES meter(id) ON DELETE CASCADE,
    CONSTRAINT fk_reading_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT uq_meter_reading UNIQUE (meter_id, reading_date)
);
