-- Create the users table
CREATE TABLE users (
    id serial PRIMARY KEY,
    nickname VARCHAR(10),
    email VARCHAR NOT NULL UNIQUE,
    hashed_password VARCHAR NOT NULL,
    district VARCHAR(20),
    credits INT DEFAULT 6,
    date_registered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    consent_to_share BOOLEAN DEFAULT FALSE,
    is_admin BOOLEAN DEFAULT FALSE
);

-- Insert sample data into users
INSERT INTO users (nickname, email, hashed_password, district, credits, date_registered, consent_to_share, is_admin)
VALUES
    ('user1', 'user1@example.com', '$2a$10$SOMEHASH...', 'District 1', 10, '2023-01-01 10:00:00', TRUE, TRUE),
    ('user2', 'user2@example.com', '$2a$10$OTHERHASH...', 'District 2', 5, '2023-01-02 14:30:00', FALSE, FALSE),
    ('user3', 'user3@example.com', '$2a$10$ANOTHERHASH...', 'District 1', 15, '2023-01-03 16:45:00', TRUE, FALSE);
