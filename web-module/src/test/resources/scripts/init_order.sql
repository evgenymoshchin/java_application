INSERT INTO user (id, first_name, last_name, patronymic, username, password, role_id)
VALUES (1,'ivan','ivanov','c','d','e',1);

INSERT INTO orders (id, created_by, status_id, user_id)
VALUES (1, now(), 1, 1);
