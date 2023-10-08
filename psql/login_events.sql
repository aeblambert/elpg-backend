CREATE TABLE login_events (
    id serial PRIMARY KEY,
    user_id INT REFERENCES users(id),
    login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample login events into login_events
INSERT INTO login_events (user_id, login_time)
VALUES
    (1, '2023-01-01 10:00:00'),
    (1, '2023-01-02 11:05:00'),
    (1, '2023-01-03 15:20:00'),
    (2, '2023-01-04 16:25:00'),
    (2, '2023-01-05 09:15:00'),
    (3, '2023-01-05 17:35:00'),
    (3, '2023-01-06 13:45:00');

