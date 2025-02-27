
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
                                                    ('admin', '$2y$10$S2scxbeK2B9rqV5iXQ9LDeVasmqEuhDWUSHUbaCCP0bi1ioRe3ifa'),
                                                    ('user', '$2y$10$ZSO7oZ/PaWKJHFyVpiNowe1vEXxMqw3xRUdDV3NTWnWBmTrdfRB4K');

INSERT INTO authorities (username, authority) VALUES
                                                  ('admin', 'ROLE_ADMINISTRATOR'),
                                                  ('user', 'ROLE_VIEWER');