insert into users
(id, username, password) values
(0, 'docflow_admin', 'Labodobik228?');
insert into user_roles
(user_id, roles) values
(0, 'ADMIN');
insert into participants
(id, owner_id) values
(0, 0);
update users
set client_id = 0
where id = 0;