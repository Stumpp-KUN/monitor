
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(100) NOT NULL
);

CREATE TABLE authorities (
                             username VARCHAR(50) NOT NULL REFERENCES users(username),
                             authority VARCHAR(20) NOT NULL
);

INSERT INTO users (username, password) VALUES
                                                    ('admin', '{bcrypt}$2a$10$E9u.P3kUqrGdQ.75ZbDPTe9i7W7T/6UAI6xjMZ6v8Lq1Jc6o5JQ0O'),
                                                    ('user', '{bcrypt}$2a$10$YkCOlS7l1z.fw6sC7Df4Ae3o0X6uK7Z0jN1d3Jm6YvL8kQ5dJ7V1C');

INSERT INTO authorities (username, authority) VALUES
                                                  ('admin', 'ROLE_ADMINISTRATOR'),
                                                  ('user', 'ROLE_VIEWER');