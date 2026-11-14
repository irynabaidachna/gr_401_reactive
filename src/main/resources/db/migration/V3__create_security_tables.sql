CREATE TABLE users (
                       id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       username VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE roles (
                       id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE roles_has_users (
                                 id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                 user_id BIGINT NOT NULL,
                                 role_id BIGINT NOT NULL,

                                 CONSTRAINT fk_security_user
                                     FOREIGN KEY (user_id) REFERENCES users(id),

                                 CONSTRAINT fk_security_role
                                     FOREIGN KEY (role_id) REFERENCES roles(id),

                                 CONSTRAINT uq_user_role UNIQUE (user_id, role_id)
);
