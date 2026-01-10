DELETE FROM user_role;
DELETE FROM restaurant;
DELETE FROM users;
DELETE FROM restaurant_users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User Anton', 'user@yandex.ru', '{noop}password'),
       ('Admin Andrey', 'admin@gmail.com', '{noop}admin'),
       ('Guest Sergey', 'guestSergey@gmail.com', '{noop}password'),
       ('Guest Sveta', 'guestSveta@gmail.com', '{noop}password')
;

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('USER', 100001),
       ('USER', 100002),
       ('USER', 100003);

INSERT INTO restaurant (name, menu)
VALUES ('ресторан0', 'борщ 50, колтета с рисом 100, компот 10'),
       ('ресторан1', 'суп куриный 40, сосиска с картофелем 80, чай 10'),
       ('ресторан1', 'суп куриный 400, сосиска с картофелем 800, чай 100'),
       ('ресторан2', 'суп харчо 600, плов 900, сок 100'),
       ('ресторан2', 'суп харчо 60, плов 90, сок 10'),
       ('ресторан3', 'суп сырный 40, лазанья 80, компот 10'),
       ('ресторан2', 'суп харчо 6, плов 9, сок 1');


UPDATE RESTAURANT
SET REGISTERED = '2025-12-11 13:50:49' WHERE ID = 100006;
UPDATE RESTAURANT
SET REGISTERED = '2025-12-11 13:50:49' WHERE ID = 100007;
UPDATE RESTAURANT
SET REGISTERED = '2025-12-11 13:50:49' WHERE ID = 100008;


INSERT INTO restaurant_users (RESTAURANT_ID, USER_ID, VOTING_DATE)
VALUES (100005, 100000, CURRENT_DATE),
       (100006, 100002, Date '2025-12-11'),
       (100007, 100002, Date '2025-12-11'),
       (100008, 100002, Date '2025-12-11'),
       (100009, 100001, CURRENT_DATE);




