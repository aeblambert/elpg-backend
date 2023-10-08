CREATE TABLE books (
    id serial PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    ISBN VARCHAR(13),
    image_path VARCHAR(255),
    owner_id INT REFERENCES users(id),
    is_available_to_lend BOOLEAN DEFAULT TRUE,
    is_available_to_gift BOOLEAN DEFAULT TRUE,
    date_of_publication DATE,
    reader_rec_min_age INT,
    reader_rec_max_age INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO books 
    (title, author, ISBN, image_path, owner_id, is_available_to_lend, is_available_to_gift, date_of_publication, reader_rec_min_age, reader_rec_max_age, created_at, updated_at)
VALUES
    ('Harry Potter and the Philosopher''s Stone', 'J.K. Rowling', '9780747532699', '/images/hp1.jpg', 1, TRUE, FALSE, '1997-06-26', 9, 99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('1984', 'George Orwell', '9780451524935', '/images/1984.jpg', 2, TRUE, TRUE, '1949-06-08', 16, 99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('To Kill a Mockingbird', 'Harper Lee', NULL, NULL, 1, TRUE, FALSE, '1960-07-11', 14, 99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Pride and Prejudice', 'Jane Austen', '9780679405648', '/images/pride.jpg', 2, TRUE, FALSE, '1813-01-28', 13, 99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Animal Farm', 'George Orwell', '9780141036137', '/images/animal.jpg', 3, TRUE, TRUE, '1945-08-17', 12, 99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('The Cat in the Hat', 'Dr. Seuss', NULL, NULL, 1, FALSE, TRUE, '1957-03-12', 4, 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Green Eggs and Ham', 'Dr. Seuss', '9780394800165', '/images/greeneggs.jpg', 1, TRUE, FALSE, '1960-08-12', 4, 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Brave New World', 'Aldous Huxley', '9780060850524', '/images/brave.jpg', 3, TRUE, FALSE, '1932-09-01', 15, 99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Where the Wild Things Are', 'Maurice Sendak', NULL, NULL, 2, TRUE, TRUE, '1963-04-19', 4, 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Charlotte''s Web', 'E.B. White', '9780061124952', '/images/charlotte.jpg', 2, TRUE, FALSE, '1952-10-15', 8, 12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Lord of the Flies', 'William Golding', '9780399501487', '/images/lord.jpg', 3, TRUE, FALSE, '1954-09-17', 12, 99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Matilda', 'Roald Dahl', '9780141346342', '/images/matilda.jpg', 3, TRUE, TRUE, '1988-10-01', 8, 12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('The Catcher in the Rye', 'J.D. Salinger', '9780140237504', '/images/catcher.jpg', 1, TRUE, FALSE, '1951-07-16', 14, 99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('The Very Hungry Caterpillar', 'Eric Carle', NULL, NULL, 1, TRUE, TRUE, '1969-06-03', 2, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Moby Dick', 'Herman Melville', '9780142437247', '/images/moby.jpg', 2, TRUE, TRUE, '1851-10-18', 16, 99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('The Gruffalo', 'Julia Donaldson', '9780142403877', '/images/gruffalo.jpg', 3, FALSE, TRUE, '1999-03-02', 3, 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('The Tale of Peter Rabbit', 'Beatrix Potter', NULL, NULL, 2, TRUE, TRUE, '1902-10-02', 3, 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Anne of Green Gables', 'Lucy Maud Montgomery', '9780553213133', '/images/anne.jpg', 1, TRUE, FALSE, '1908-06-01', 11, 99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Alice''s Adventures in Wonderland', 'Lewis Carroll', '9780141439761', '/images/alice.jpg', 3, TRUE, TRUE, '1865-11-26', 9, 99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Winnie-the-Pooh', 'A. A. Milne', '9780525444435', '/images/pooh.jpg', 1, TRUE, TRUE, '1926-10-14', 6, 99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

