insert into users (id, name, surname, tax_id) values (1, 'Martin', 'McFly', 'MCFF1985');
insert into users (id, name, surname, tax_id) values (2, 'Emmet', 'Brow', 'EMBR1955');
insert into accounts (id, account_number, amount, creation_date, update_date, fk_user_id) values (1, 'DE15101985', 1500, now(), now(),1);
insert into accounts (id, account_number, amount, creation_date, update_date, fk_user_id) values (2, 'DE15101955', 1600, now(), now(),2);
