--$2a$10$RVzuprKddsjdq6P8QWmqF.sCj2uYPIUlbFVB.b7tJ9RdFNOOBNoXO

insert into authority (role) values ('ROLE_REGISTERED_USER');
insert into authority (role) values ('ROLE_ADMIN');

insert into image (name, relative_path, active) values ('file1','src/main/resources/static/images/file1.jpg', true);
insert into image (name, relative_path, active) values ('file2', 'src/main/resources/static/images/file2.jpg', true);
insert into image (name, relative_path, active) values ('file3', 'src/main/resources/static/images/file2.jpg', true);
insert into image (name, relative_path, active) values ('slika4', 'path4.jpg', true);
insert into image (name, relative_path, active) values ('slika5','path5.jpg', true);
insert into image (name, relative_path, active) values ('slika6','path6.jpg', true);



insert into users (type, active, email, first_name, last_name, password, verified, image_id)
values ('admin', true,'admin@gmail.com', 'Marko','Markovic','$2a$10$RVzuprKddsjdq6P8QWmqF.sCj2uYPIUlbFVB.b7tJ9RdFNOOBNoXO',true,1);
insert into users (type, active, email, first_name, last_name, password, verified, image_id)
values ('registered_user',true,'at@gmail.com', 'Ivan','Ivanovic','$2a$10$RVzuprKddsjdq6P8QWmqF.sCj2uYPIUlbFVB.b7tJ9RdFNOOBNoXO',true,2);
insert into users (type, active, email, first_name, last_name, password, verified, image_id)
values ('registered_user',true,'user2@gmail.com', 'Ivana','Ivanovic','$2a$10$RVzuprKddsjdq6P8QWmqF.sCj2uYPIUlbFVB.b7tJ9RdFNOOBNoXO',true,null);

insert into users_authority (user_id, authority_id) values (1,2);
insert into users_authority (user_id, authority_id) values (2,1);
insert into users_authority (user_id, authority_id) values (3,1);

insert into category (name, description, active) values ('institution', 'institutions in serbia', true);
insert into category (name, description, active) values ('manifestation', 'manifestations in serbia', true);

insert into type (name, description, category_id, active) values ('museum', 'museums in serbia', 1, true);
insert into type (name, description, category_id, active) values ('gallery', 'galleries in serbia', 1, true);
insert into type (name, description, category_id, active) values ('festival', 'festivals in serbia', 2, true);
insert into type (name, description, category_id, active) values ('opera', 'operas in serbia', 2, true);

insert into cultural_offer (average_rate, date, description, lat, lon, city, name, type_id, admin_id, active)
values (5.0, '2020-12-25 19:30:00', 'The Gallery was established on the 14th of October 1847.', 44.05, 45.02,'Novi Sad', 'Gallery of Matica Srpska', 1, 1, true);
insert into cultural_offer (average_rate, date, description, lat, lon, city, name, type_id, admin_id, active)
values (3.0, '2021-07-11 19:30:00', 'opisneki', 42.05, 40.02, 'Novi Sad', 'exit', 3, 1, false);
insert into cultural_offer (average_rate, date, description, lat, lon, city, name, type_id, admin_id, active)
values (4.0, '2021-07-11 19:30:00', 'opisneki', 40.05, 45.02, 'Belgrade Serbia', 'djiasjasij', 3, 1, true);
insert into cultural_offer (average_rate, date, description, lat, lon, city, name, type_id, admin_id, active)
values (2.0, '2020-12-25 19:30:00', 'The Gallery was established on the 14th of October 1847.', 41.05, 45.02,'Novi Sad', 'Gallery of', 1, 1, true);
insert into cultural_offer (average_rate, date, description, lat, lon, city, name, type_id, admin_id, active)
values (5.0, '2020-12-25 19:30:00', 'The Gallery was established on the 14th of October 1847.', 39.05, 45.02,'Novi Sad', 'Gallery of Srpska', 1, 1, true);
insert into cultural_offer (average_rate, date, description, lat, lon, city, name, type_id, admin_id, active)
values (3.0, '2020-12-25 19:30:00', 'The Gallery was established on the 14th of October 1847.', 44.05, 20.02,'Novi Sad', 'Gallery Matica Srpska', 1, 1, true);
insert into cultural_offer (average_rate, date, description, lat, lon, city, name, type_id, admin_id, active)
values (4.0, '2020-12-25 19:30:00', 'The Gallery was established on the 14th of October 1847.', 44.05, 50.02,'Novi Sad', 'Gallery of srpska ', 1, 1, true);
insert into cultural_offer (average_rate, date, description, lat, lon, city, name, type_id, admin_id, active)
values (5.0, '2020-12-25 19:30:00', 'The Gallery was established on the 14th of October 1847.', 44.05, 43.02,'Novi Sad', 'Gallery Srpska', 1, 1, true);
insert into cultural_offer (average_rate, date, description, lat, lon, city, name, type_id, admin_id, active)
values (5.0, '2020-12-25 19:30:00', 'The Gallery was established on the 14th of October 1847.', 44.05, 48.02,'Novi Sad', 'of Matica Srpska', 1, 1, true);
insert into cultural_offer (average_rate, date, description, lat, lon, city, name, type_id, admin_id, active)
values (5.0, '2020-12-25 19:30:00', 'The Gallery was established on the 14th of October 1847.', 44.05, 30.02,'Novi Sad', 'Matica Srpska', 1, 1, true);


insert into image (name, relative_path, active, cultural_offer_id) values ('file7','src/main/resources/static/images/file7.jpg', true, 3);
insert into image (name, relative_path, active, cultural_offer_id) values ('file8','src/main/resources/static/images/file8.jpg', true, 1);
insert into image (name, relative_path, active, cultural_offer_id) values ('file9','src/main/resources/static/images/file9.jpg', true, 1);

insert into users_favorite_cultural_offers (registered_user_id, favorite_cultural_offers_id)
values (2, 1);
insert into users_favorite_cultural_offers (registered_user_id, favorite_cultural_offers_id)
values (2, 2);

insert into post (date, text, image_id, cultural_offer_id, active)
values('2020-12-24 19:30:00', 'Nova izlozba Djure Jaksica ce biti u galeriji od prekosutra. Ocekujemo veliki broj zainteresovanih, ne propustite!!', 2, 1, true);
insert into post (date, text, image_id, cultural_offer_id, active)
values('2020-10-24 19:30:00', 'promocija knjige', 1, 1, true);
insert into post (date, text, image_id, cultural_offer_id, active)
values('2020-10-24 19:30:00', 'promocija knjige', null, 1, true);
insert into post (date, text, image_id, cultural_offer_id, active)
values('2020-10-24 19:30:00', 'promocija knjige', null, 1, true);
insert into post (date, text, image_id, cultural_offer_id, active)
values('2020-10-24 19:30:00', 'promocija knjige', null, 1, false);

insert into comment (date, text, image_id, cultural_offer_id, registred_user_id, active)
values ('2020-10-24 19:30:00', 'Izlozba je bila vrh. Evo jedne slike od mene.', 1, 1, 2, true);
insert into comment (date, text, image_id, cultural_offer_id, registred_user_id, active)
values ('2020-10-24 19:30:00', 'mnogo velika guzva', 1, 1, 3, true);
insert into comment (date, text, image_id, cultural_offer_id, registred_user_id, active)
values ('2020-10-24 19:30:00', 'Mnogo velika guzva', null , 1, 3, true);
insert into comment (date, text, image_id, cultural_offer_id, registred_user_id, active)
values ('2020-10-24 19:30:00', 'Bila je velika guzva', 1, 1, 3, true);
insert into comment (date, text, image_id, cultural_offer_id, registred_user_id, active)
values ('2020-10-24 19:30:00', 'Ljudi nisu marili za COVID19', 1, 1, 3, true);
insert into comment (date, text, image_id, cultural_offer_id, registred_user_id, active)
values ('2020-10-24 19:30:00', 'Mnogo velika guzva', null , 1, 3, false);

insert into rate (number, cultural_offer_id, registred_user_id, active) values (5, 1, 2, true);
insert into rate (number, cultural_offer_id, registred_user_id, active) values (5, 2, 3, true);
-- 




