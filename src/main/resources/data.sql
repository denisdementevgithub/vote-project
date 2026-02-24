DELETE
FROM user_role;
DELETE
FROM restaurant;
DELETE
FROM users;
DELETE
FROM vote;
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

INSERT INTO restaurant (name)
VALUES ('Столовая'),
       ('Семейный ресторан '),
       ('Сказка Востока'),
       ('Almond'),
       ('Цзао Ван'),
       ('Прованс'),
       ('Ферма Бенуа');

UPDATE RESTAURANT
SET REGISTERED = '2025-12-10 13:50:49'
WHERE ID = 100005;
UPDATE RESTAURANT
SET REGISTERED = '2025-12-10 13:50:49'
WHERE ID = 100007;

INSERT INTO meal (restaurant_id, name, price)
VALUES (100004, 'борщ', 50),
       (100004, 'колтета с рисом', 100),
       (100004, 'компот', 10),
       (100005, 'суп куриный', 40),
       (100005, 'сосиска с картофелем', 80),
       (100005, 'чай', 10),
       (100006, 'суп куриный', 400),
       (100006, 'сосиска с картофелем', 800),
       (100006, 'чай', 100),
       (100006, 'суп харчо', 600),
       (100006, 'плов', 900),
       (100006, 'сок', 100),
       (100007, 'суп харчо', 60),
       (100007, 'плов', 90),
       (100007, 'сок', 10),
       (100007, 'суп сырный', 40),
       (100007, 'лазанья', 80),
       (100007, 'компот', 10),
       (100010, 'суп харчо', 6),
       (100010, 'плов', 9),
       (100010, 'сок', 1);

UPDATE MEAL
SET REGISTERED = '2025-12-11 13:50:49'
WHERE ID = 100020;
UPDATE MEAL
SET REGISTERED = '2025-12-11 13:50:49'
WHERE ID = 100021;
UPDATE MEAL
SET REGISTERED = '2025-12-11 13:50:49'
WHERE ID = 100022;
UPDATE MEAL
SET REGISTERED = '2025-12-11 13:50:49'
WHERE ID = 100026;
UPDATE MEAL
SET REGISTERED = '2025-12-11 13:50:49'
WHERE ID = 100027;
UPDATE MEAL
SET REGISTERED = '2025-12-11 13:50:49'
WHERE ID = 100028;

INSERT INTO vote (RESTAURANT_ID, USER_ID, VOTING_DATE)
VALUES (100004, 100000, CURRENT_DATE),
       (100005, 100002, Date '2025-12-11'),
       (100007, 100000, Date '2025-12-11'),
       (100007, 100002, CURRENT_DATE),
       (100010, 100001, CURRENT_DATE);
