DELETE
FROM users;
DELETE
FROM role_permissions;
DELETE
FROM roles;
DELETE
FROM permissions;

INSERT INTO permissions(id, name)
VALUES (1, 'LOGIN'),
       (2, 'GET'),
       (3, 'UPDATE');

SELECT setval('permissions_id_seq', (SELECT max(id) FROM "permissions"));

INSERT INTO roles(id, name, priority)
VALUES (1, 'ADMIN', 1),
       (2, 'USER', 2);

SELECT setval('roles_id_seq', (SELECT max(id) FROM "roles"));

INSERT INTO role_permissions
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (2, 2);

-- password for users and admin: 'pass'
INSERT INTO users(id, address, lat, lon, name, password, secret, email, role_id, status)
VALUES (1, null, null, null, 'admin', '$2a$04$8j4Zq6lbQa0dQ8vNajhWLOPyckkC4Uwfpu7f5VBkIFnKn78Jpr0fG', 'admin',
        'admin@mail.com', 1, 'ACTIVE'),
       (2, null, null, null, 'user', '$2a$04$dawA/6588L5oHcQ3U7gRPO9wCKGcxpSuhH605LemBPQgY5seJ7zUC', 'user',
        'user@mail.com', 2, 'ACTIVE'),
       (3, null, null, null, 'user1', '$2a$04$GVuN8uGFGu4FyXFCSKwo8.NnYIGcBHlnYBoQe3Ci405HGi.vjoEfK', 'user1',
        'user1@mail.com', 2, 'ACTIVE'),
       (4, null, null, null, 'user2', '$2a$04$6Bp43Km7X8EuDXqTaWaEaeYYVCj90pwIFU9XPFs7HWiYcXWFr325m', 'user2',
        'user2@mail.com', 2, 'ACTIVE');

SELECT setval('users_id_seq', (SELECT max(id) FROM "users"));