CREATE TABLE IF NOT EXISTS role
(
    id   INTEGER PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);


CREATE TABLE IF NOT EXISTS user_account
(
    id         INTEGER PRIMARY KEY,
    username   VARCHAR(255)    NOT NULL,
    password   VARCHAR(255)    NOT NULL,
    first_name VARCHAR(255)    NOT NULL,
    last_name  VARCHAR(255)    NOT NULL,
    status     VARCHAR(255)    NOT NULL,
    created_at timestamp NOT NULL
);


CREATE TABLE IF NOT EXISTS user_account_roles
(
    user_id  BIGINT REFERENCES user_account (id) DEFERRABLE INITIALLY DEFERRED NOT NULL,
    roles_id BIGINT REFERENCES role (id) DEFERRABLE INITIALLY DEFERRED         NOT NULL

);



INSERT INTO role
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');

INSERT INTO user_account
VALUES (1, 'Sara', 'A121', 'Sara', 'Conor', 'ACTIVE', '2021-01-04 23:23:30.000000'),
       (2, 'ToniW', 'A122', 'Toni', 'Faro', 'ACTIVE', '2021-01-04 23:23:30.000000'),
       (3, 'LanaR', 'A123', 'Lana', 'Picasso', 'ACTIVE', '2021-01-04 23:23:30.000000'),
       (4, 'BobT', 'A124', 'Bob', 'Busch', 'INACTIVE', '2021-01-04 23:23:30.000000'),
       (5, 'LuckS', 'A125', 'Luck', 'Deans', 'ACTIVE', '2021-01-04 23:23:30.000000');

INSERT INTO user_account_roles
VALUES (1, '1'),
       (2, '2'),
       (3, '2'),
       (4, '1'),
       (5, '1');







