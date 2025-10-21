INSERT INTO users (id, login, password, email) VALUES (1, 'login', '$2a$10$yyR9EQLe487rSZfCmRP0YOu8x7UCed8/j4WzxwvC76enA2BiJ9AyK', 'email@mail.ru');

INSERT INTO clients (id, client_id, user_id, first_name, middle_name, last_name, date_of_birth, document_type, document_id, document_prefix, document_suffix)
       VALUES (1, '111111111111', 1, 'Ivan', 'Ivanovich', 'Ivanov', '2025-10-10', 'PASSPORT', '11', '1', '1');

INSERT INTO roles (id, name) VALUES (1, 'ROLE_CURRENT_CLIENT'), (2, 'ROLE_MASTER');

INSERT INTO user_roles (role_id, user_id) VALUES (2, 1);

INSERT INTO products (id, name, product_key) VALUES (1, 'name', 'DC');