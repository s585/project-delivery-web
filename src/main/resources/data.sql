DELETE
FROM users;
DELETE
FROM vendors;
DELETE
FROM role_permissions;
DELETE
FROM roles;
DELETE
FROM permissions;

INSERT INTO permissions(id, name)
VALUES (1, 'LOGIN'),
       (2, 'CREATE_USERS'),
       (3, 'GET_USERS'),
       (4, 'GET_VENDORS'),
       (5, 'UPDATE_USER_INFO'),
       (6, 'UPDATE_PRODUCT_INFO'),
       (7, 'DELETE_USER_BY_ID'),
       (8, 'SET_USER_STATUS_ACTIVE'),
       (9, 'MAKE_PURCHASES'),
       (10, 'DELETE_CART');

SELECT setval('permissions_id_seq', (SELECT max(id) FROM "permissions"));

INSERT INTO roles(id, name)
VALUES (1, 'ADMIN'),
       (2, 'USER'),
       (3, 'VENDOR');

SELECT setval('roles_id_seq', (SELECT max(id) FROM "roles"));

INSERT INTO role_permissions
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (2, 1),
       (2, 4),
       (2, 5),
       (2, 9),
       (3, 1),
       (3, 5),
       (3, 6);

-- password for users and admin: 'pass'
INSERT INTO users(id, address, lat, lon, name, password, secret, email, role_id, status)
VALUES (1, null, null, null, 'admin', '$2a$04$8j4Zq6lbQa0dQ8vNajhWLOPyckkC4Uwfpu7f5VBkIFnKn78Jpr0fG',
        '$2a$10$AvLw0cebzj./CIs1fzbYyuuiI7Hc6iNsEqXddT.npB8Y43zqYhoaS',
        'admin@mail.com', 1, 'ACTIVE'),
       (2, 'Казань, Ямашева 52', 49.124776, 55.828106, 'Белов',
        '$2a$04$dawA/6588L5oHcQ3U7gRPO9wCKGcxpSuhH605LemBPQgY5seJ7zUC',
        '$2a$10$AvLw0cebzj./CIs1fzbYyuuiI7Hc6iNsEqXddT.npB8Y43zqYhoaS',
        'belov@mail.com', 2, 'ACTIVE'),
       (3, 'Казань, Мусина 54', 49.124677, 55.832839, 'Воробей',
        '$2a$04$GVuN8uGFGu4FyXFCSKwo8.NnYIGcBHlnYBoQe3Ci405HGi.vjoEfK',
        '$2a$10$AvLw0cebzj./CIs1fzbYyuuiI7Hc6iNsEqXddT.npB8Y43zqYhoaS',
        'vorobei@mail.com', 2, 'ACTIVE'),
       (4, 'Казань, Бутлерова 39', 49.134307, 55.789006, 'Городец',
        '$2a$04$6Bp43Km7X8EuDXqTaWaEaeYYVCj90pwIFU9XPFs7HWiYcXWFr325m',
        '$2a$10$AvLw0cebzj./CIs1fzbYyuuiI7Hc6iNsEqXddT.npB8Y43zqYhoaS',
        'gorodets@mail.com', 2, 'ACTIVE');

SELECT setval('users_id_seq', (SELECT max(id) FROM "users"));

-- password for vendors: 'pass'
INSERT INTO vendors(id, address, lat, lon, name, password, secret, email, role_id, status)
VALUES (1, 'Казань, Чистопольская 19А', 49.114795, 55.81997, 'Cafe La Vita',
        '$2a$04$8j4Zq6lbQa0dQ8vNajhWLOPyckkC4Uwfpu7f5VBkIFnKn78Jpr0fG',
        '$2a$10$AvLw0cebzj./CIs1fzbYyuuiI7Hc6iNsEqXddT.npB8Y43zqYhoaS',
        'lavita@mail.com', 3, 'ACTIVE'),
       (2, 'Казань, Галактионова 6', 49.12633, 55.792706, 'Агафредо',
        '$2a$04$dawA/6588L5oHcQ3U7gRPO9wCKGcxpSuhH605LemBPQgY5seJ7zUC',
        '$2a$10$AvLw0cebzj./CIs1fzbYyuuiI7Hc6iNsEqXddT.npB8Y43zqYhoaS',
        'agafredo@mail.com', 3, 'ACTIVE'),
       (3, 'Казань, Дзержинского 13', 49.12633, 55.792706, 'Утка в котелке',
        '$2a$04$GVuN8uGFGu4FyXFCSKwo8.NnYIGcBHlnYBoQe3Ci405HGi.vjoEfK',
        '$2a$10$AvLw0cebzj./CIs1fzbYyuuiI7Hc6iNsEqXddT.npB8Y43zqYhoaS',
        'utka@mail.com', 3, 'ACTIVE'),
       (4, 'Казань, Лево-Булачная 24/1', 49.107366, 55.791653, 'Fusion of Asia',
        '$2a$04$6Bp43Km7X8EuDXqTaWaEaeYYVCj90pwIFU9XPFs7HWiYcXWFr325m',
        '$2a$10$AvLw0cebzj./CIs1fzbYyuuiI7Hc6iNsEqXddT.npB8Y43zqYhoaS',
        'fusion@mail.com', 3, 'ACTIVE');

SELECT setval('vendors_id_seq', (SELECT max(id) FROM "vendors"));

INSERT INTO deliverers (name, address, lon, lat, status)
VALUES ('Смелов', 'Казань, Абсалямова 37', 49.109549, 55.824673, 'ACTIVE'),
       ('Жеглов', 'Казань, Амирхана 5', 49.131288, 55.821254, 'ACTIVE'),
       ('Багдарян', 'Казань, Баумана 68', 49.131288, 55.821254, 'ACTIVE'),
       ('Сванидзе', 'Казань, Коротченко 2', 49.097835, 55.79387, 'ACTIVE'),
       ('Рамирес', 'Казань, Горького 15', 49.131091, 55.794097, 'ACTIVE'),
       ('Броневой', 'Казань, Гривская 45', 49.093352, 55.80826, 'ACTIVE'),
       ('Ткач', 'Казань, Сулеймановой 7', 49.088313, 55.81298, 'ACTIVE');

INSERT INTO products (id, category, imageuri, price, name, vendor_id)
VALUES (1, 'FOOD', null, 500, 'pizza', 1),
       (2, 'FOOD', null, 300, 'pasta', 1),
       (3, 'FOOD', null, 600, 'pizza', 2),
       (4, 'FOOD', null, 400, 'pasta', 2),
       (5, 'FOOD', null, 850, 'утка', 3),
       (6, 'FOOD', null, 550, 'оливье', 3),
       (7, 'FOOD', null, 490, 'том ям', 4),
       (8, 'FOOD', null, 420, 'курица терияки', 4);
