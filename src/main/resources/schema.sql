DROP TABLE IF EXISTS user_role cascade;
DROP TABLE IF EXISTS meal cascade;
DROP TABLE IF EXISTS vote cascade;
DROP TABLE IF EXISTS restaurant cascade;
DROP TABLE IF EXISTS users cascade;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE IF NOT EXISTS global_seq START WITH 100000 INCREMENT BY 1;

CREATE TABLE restaurant
(
    id         INTEGER   DEFAULT NEXT VALUE FOR GLOBAL_SEQ PRIMARY KEY,
    name       VARCHAR(255)            NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL
);

CREATE TABLE users
(
    id         INTEGER   DEFAULT NEXT VALUE FOR GLOBAL_SEQ PRIMARY KEY,
    name       VARCHAR(255)           NOT NULL,
    email      VARCHAR(255)           NOT NULL,
    password   VARCHAR(255)           NOT NULL,
    registered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enabled    BOOLEAN   DEFAULT TRUE NOT NULL
);

CREATE UNIQUE INDEX users_unique_email_idx
    ON USERS (email);

CREATE TABLE user_role
(
    user_id INTEGER      NOT NULL,
    role    VARCHAR(255) NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE meal
(
    id            INTEGER DEFAULT NEXT VALUE FOR GLOBAL_SEQ PRIMARY KEY,
    restaurant_id INTEGER      NOT NULL,
    name          VARCHAR(255) NOT NULL,
    price         INTEGER      NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);

CREATE TABLE vote
(
    id            INTEGER DEFAULT NEXT VALUE FOR GLOBAL_SEQ PRIMARY KEY,
    restaurant_id INTEGER NOT NULL,
    user_id       INTEGER NOT NULL,
    voting_date   DATE    DEFAULT CURRENT_DATE,
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);
