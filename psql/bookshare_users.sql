-- Create the bookshare_users table
CREATE TABLE bookshare_users (
    id serial PRIMARY KEY,
    nickname VARCHAR(10),
    email VARCHAR NOT NULL UNIQUE,
    hashed_password VARCHAR NOT NULL,
    district VARCHAR(10),
    credits INT DEFAULT 6,
    date_registered DATE,
    last_access TIMESTAMP
);

-- Pre-generated password hashes for test accounts
-- Use a secure hashing function to generate these hashes
-- Example: BCrypt hashed passwords
INSERT INTO bookshare_users (nickname, email, hashed_password, district, credits, date_registered, last_access)
VALUES
    ('user1', 'user1@example.com', '$2a$10$SOMEHASH...', 'District 1', 10, '2023-01-01', '2023-01-01 10:00:00'),
    ('user2', 'user2@example.com', '$2a$10$OTHERHASH...', 'District 2', 5, '2023-01-02', '2023-01-02 14:30:00'),
    ('user3', 'user3@example.com', '$2a$10$ANOTHERHASH...', 'District 1', 15, '2023-01-03', '2023-01-03 16:45:00');
