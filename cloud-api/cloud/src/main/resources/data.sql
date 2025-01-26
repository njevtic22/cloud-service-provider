insert into roles (id, name) values (1, 'ROLE_SUPER_ADMIN');
insert into roles (id, name) values (2, 'ROLE_ADMIN');
insert into roles (id, name) values (3, 'ROLE_USER');

alter sequence role_id_seq restart with 4;

insert into users(id, name, surname, email, username, password, archived, role_id) values (1, 'Name 1', 'Surname 1', 'user1@gmail.com', 'user1', 'password', false, 1);
insert into users(id, name, surname, email, username, password, archived, role_id) values (2, 'Name 2', 'Surname 2', 'user2@gmail.com', 'user2', 'password', false, 2);
insert into users(id, name, surname, email, username, password, archived, role_id) values (3, 'Name 3', 'Surname 3', 'user3@gmail.com', 'user3', 'password', false, 3);
