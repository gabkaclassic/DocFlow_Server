insert into users (id, online, password, username) values
(0, 't', 'Lobadobik?900', 'docflow_admin');
insert into user_roles (user_id, roles) values
(0, 'ADMIN'),
(0, 'USER');

insert into participants (id, owner_id) values
(0, 0);

update users set client_id = 0 where id = 0;