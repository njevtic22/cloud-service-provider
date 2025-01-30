-- Passwords are hashed using BCrypt algorithm https://bcrypt-generator.com/
-- Passwords for all users are:
--
-- Scripts combined generate database for cloud-service-provider
-- It generates:
--	- 5 super admins
--	- 10 admins
--	- 20 users
--
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------- inserting roles --------------------------------------------------------------------------------------------
insert into roles (id, name) values (1, 'ROLE_SUPER_ADMIN');
insert into roles (id, name) values (2, 'ROLE_ADMIN');
insert into roles (id, name) values (3, 'ROLE_USER');
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------- Altering role_id_seq -----------------------------------------------------------------------------------------
alter sequence role_id_seq restart with 4;
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------- inserting users --------------------------------------------------------------------------------------------
insert into users(id, name, surname, email, username, password, archived, role_id) values (1, 'Dodie', 'Zieme', 'superadmin1@gmail.com', 'superadmin1', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 1);
insert into users(id, name, surname, email, username, password, archived, role_id) values (2, 'Joan', 'Kunde', 'superadmin2@gmail.com', 'superadmin2', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 1);
insert into users(id, name, surname, email, username, password, archived, role_id) values (3, 'Brandie', 'Rolfson', 'superadmin3@gmail.com', 'superadmin3', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 1);
insert into users(id, name, surname, email, username, password, archived, role_id) values (4, 'Amos', 'Huels', 'superadmin4@gmail.com', 'superadmin4', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 1);
insert into users(id, name, surname, email, username, password, archived, role_id) values (5, 'Jerrold', 'Schmitt', 'superadmin5@gmail.com', 'superadmin5', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 1);
insert into users(id, name, surname, email, username, password, archived, role_id) values (6, 'Felicia', 'Kunde', 'admin1@gmail.com', 'admin1', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 2);
insert into users(id, name, surname, email, username, password, archived, role_id) values (7, 'Ted', 'Torphy', 'admin2@gmail.com', 'admin2', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 2);
insert into users(id, name, surname, email, username, password, archived, role_id) values (8, 'Bruna', 'Halvorson', 'admin3@gmail.com', 'admin3', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 2);
insert into users(id, name, surname, email, username, password, archived, role_id) values (9, 'Sadye', 'Konopelski', 'admin4@gmail.com', 'admin4', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 2);
insert into users(id, name, surname, email, username, password, archived, role_id) values (10, 'Indira', 'Gerlach', 'admin5@gmail.com', 'admin5', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 2);
insert into users(id, name, surname, email, username, password, archived, role_id) values (11, 'Daryl', 'Hettinger', 'admin6@gmail.com', 'admin6', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 2);
insert into users(id, name, surname, email, username, password, archived, role_id) values (12, 'Jennine', 'Gaylord', 'admin7@gmail.com', 'admin7', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 2);
insert into users(id, name, surname, email, username, password, archived, role_id) values (13, 'Tyrone', 'Stroman', 'admin8@gmail.com', 'admin8', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 2);
insert into users(id, name, surname, email, username, password, archived, role_id) values (14, 'Cleo', 'Leannon', 'admin9@gmail.com', 'admin9', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 2);
insert into users(id, name, surname, email, username, password, archived, role_id) values (15, 'Ruthann', 'Shields', 'admin10@gmail.com', 'admin10', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 2);
insert into users(id, name, surname, email, username, password, archived, role_id) values (16, 'Giovanni', 'King', 'user1@gmail.com', 'user1', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 3);
insert into users(id, name, surname, email, username, password, archived, role_id) values (17, 'Allyson', 'DuBuque', 'user2@gmail.com', 'user2', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 3);
insert into users(id, name, surname, email, username, password, archived, role_id) values (18, 'Juan', 'Schumm', 'user3@gmail.com', 'user3', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 3);
insert into users(id, name, surname, email, username, password, archived, role_id) values (19, 'Assunta', 'Shanahan', 'user4@gmail.com', 'user4', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 3);
insert into users(id, name, surname, email, username, password, archived, role_id) values (20, 'Ryan', 'Ankunding', 'user5@gmail.com', 'user5', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 3);
insert into users(id, name, surname, email, username, password, archived, role_id) values (21, 'Javier', 'Schinner', 'user6@gmail.com', 'user6', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 3);
insert into users(id, name, surname, email, username, password, archived, role_id) values (22, 'Sid', 'Hermann', 'user7@gmail.com', 'user7', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 3);
insert into users(id, name, surname, email, username, password, archived, role_id) values (23, 'Hester', 'Abernathy', 'user8@gmail.com', 'user8', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 3);
insert into users(id, name, surname, email, username, password, archived, role_id) values (24, 'Barbara', 'Jakubowski', 'user9@gmail.com', 'user9', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 3);
insert into users(id, name, surname, email, username, password, archived, role_id) values (25, 'Ned', 'Hauck', 'user10@gmail.com', 'user10', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 3);
insert into users(id, name, surname, email, username, password, archived, role_id) values (26, 'Gina', 'Cole', 'user11@gmail.com', 'user11', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 3);
insert into users(id, name, surname, email, username, password, archived, role_id) values (27, 'Erik', 'Hodkiewicz', 'user12@gmail.com', 'user12', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 3);
insert into users(id, name, surname, email, username, password, archived, role_id) values (28, 'Seth', 'Schumm', 'user13@gmail.com', 'user13', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 3);
insert into users(id, name, surname, email, username, password, archived, role_id) values (29, 'Jose', 'Hilll', 'user14@gmail.com', 'user14', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 3);
insert into users(id, name, surname, email, username, password, archived, role_id) values (30, 'Julianne', 'Cronin', 'user15@gmail.com', 'user15', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 3);
insert into users(id, name, surname, email, username, password, archived, role_id) values (31, 'Aurelio', 'Denesik', 'user16@gmail.com', 'user16', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 3);
insert into users(id, name, surname, email, username, password, archived, role_id) values (32, 'Luise', 'Homenick', 'user17@gmail.com', 'user17', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 3);
insert into users(id, name, surname, email, username, password, archived, role_id) values (33, 'Tifany', 'Rempel', 'user18@gmail.com', 'user18', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 3);
insert into users(id, name, surname, email, username, password, archived, role_id) values (34, 'Jerry', 'Luettgen', 'user19@gmail.com', 'user19', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 3);
insert into users(id, name, surname, email, username, password, archived, role_id) values (35, 'Lorilee', 'Beier', 'user20@gmail.com', 'user20', '$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6', false, 3);
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------- Altering user_id_seq -----------------------------------------------------------------------------------------
alter sequence user_id_seq restart with 36;
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

insert into organizations(id, name, logo, description, archived) values (1, 'Name 1', 'logo', 'Description', false);

alter sequence organization_id_seq restart with 2;