insert into users (id, name, surname, tax_id) values (1, 'Jane', 'Doe', 'JDN1970');
insert into accounts (id, account_number, amount, creation_date, update_date, fk_user_id) values (1, 'B', 1600, now(), now(),1);
