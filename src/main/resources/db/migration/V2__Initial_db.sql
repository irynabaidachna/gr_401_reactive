CREATE TABLE clients (
                         id BIGSERIAL PRIMARY KEY,
                         first_name VARCHAR(100) NOT NULL,
                         last_name VARCHAR(100) NOT NULL,
                         age INTEGER
);

CREATE TABLE administrators (
                                id BIGSERIAL PRIMARY KEY,
                                first_name VARCHAR(100) NOT NULL,
                                last_name VARCHAR(100) NOT NULL,
                                email VARCHAR(255) NOT NULL
);

CREATE TABLE accounts (
                          id BIGSERIAL PRIMARY KEY,
                          client_id BIGINT NOT NULL,
                          balance NUMERIC(15, 2) NOT NULL DEFAULT 0,
                          blocked BOOLEAN NOT NULL DEFAULT FALSE,
                          CONSTRAINT fk_account_client
                              FOREIGN KEY (client_id) REFERENCES clients(id)
);

CREATE TABLE credit_cards (
                              id BIGSERIAL PRIMARY KEY,
                              client_id BIGINT NOT NULL,
                              account_id BIGINT NOT NULL,
                              card_number VARCHAR(19) NOT NULL,
                              expiration_date VARCHAR(7),
                              CONSTRAINT fk_card_client
                                  FOREIGN KEY (client_id) REFERENCES clients(id),
                              CONSTRAINT fk_card_account
                                  FOREIGN KEY (account_id) REFERENCES accounts(id)
);

CREATE TABLE payments (
                          id BIGSERIAL PRIMARY KEY,
                          account_id BIGINT NOT NULL,
                          amount NUMERIC(15, 2) NOT NULL,
                          payment_date TIMESTAMP,
                          status VARCHAR(30),
                          CONSTRAINT fk_payment_account
                              FOREIGN KEY (account_id) REFERENCES accounts(id)
);
