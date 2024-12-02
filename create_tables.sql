CREATE TYPE ticket_type AS ENUM ('DAY', 'MONTH', 'YEAR');

CREATE TABLE Users (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE Tickets (
                         id SERIAL PRIMARY KEY,
                         user_id INT NOT NULL,
                         ticket_type ticket_type NOT NULL,
                         creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                         CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);