INSERT INTO user (id, first_name, last_name, patronymic, username, password, role_id)
VALUES (2,'a','b','c','d','e',1);

INSERT INTO article (id, article_body, name, summary, created_by, user_id)
VALUES (1, 'body', 'name', 'summary', now(), 2);
