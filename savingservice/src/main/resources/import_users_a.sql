insert into users (id, name, surname, tax_id) values (1, 'John', 'Doe', 'JHD1970');
insert into accounts (id, account_number, amount, creation_date, update_date, fk_user_id) values (1, 'A', 1500, now(), now(),1);
