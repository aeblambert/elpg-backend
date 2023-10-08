CREATE TABLE transactions (
    transaction_id serial PRIMARY KEY,
    book_id INT REFERENCES books(id),
    lender_id INT REFERENCES users(id),
    receiver_id INT REFERENCES users(id),
    transaction_type VARCHAR(10) CHECK (transaction_type IN ('Lend', 'Gift')),
    status VARCHAR(20) CHECK (status IN ('In Process', 'Completed', 'Cancelled')),
    confirmation_status VARCHAR(20) CHECK (confirmation_status IN ('Pending', 'Confirmed', 'Denied')),
    confirmation_timestamp TIMESTAMP,
    credits_accrued INT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Inserting sample data into transactions
INSERT INTO transactions (
    transaction_id,
    book_id,
    lender_id,
    receiver_id,
    transaction_type,
    status,
    confirmation_status,
    confirmation_timestamp,
    credits_accrued,
    timestamp
)
VALUES
    (1, 1, 1, 2, 'Lend', 'In Process', 'Pending', NULL, 1, '2023-10-06 12:00:00'),
    (2, 2, 2, 3, 'Lend', 'Completed', 'Confirmed', '2023-10-06 14:30:00', 1, '2023-10-05 11:30:00'),
    (3, 3, 1, 3, 'Gift', 'In Process', 'Pending', NULL, 3, '2023-10-06 12:10:00'),
    (4, 4, 3, 1, 'Lend', 'Completed', 'Confirmed', '2023-10-06 15:00:00', 1, '2023-10-05 12:45:00'),
    (5, 5, 2, 1, 'Gift', 'Completed', 'Confirmed', '2023-10-06 16:00:00', 3, '2023-10-05 13:20:00');

