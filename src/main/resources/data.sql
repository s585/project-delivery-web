INSERT INTO roles(name)
VALUES ('ADMIN');

INSERT INTO users(address, latitude, longitude, name, password, secret, email, role_id, status)
VALUES (null, null, null, 'admin', 'pass', 'admin', 'admin@mail.com', 1, 'ACTIVE')