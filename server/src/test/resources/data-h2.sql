--$2a$10$RVzuprKddsjdq6P8QWmqF.sCj2uYPIUlbFVB.b7tJ9RdFNOOBNoXO

insert into authority (role) values ('ROLE_REGISTERED_USER');
insert into authority (role) values ('ROLE_ADMIN');

insert into image (name, relative_path, active) values ('slika','path.jpg', true);
insert into image (name, relative_path, active) values ('slika2', 'path2.jpg', true);
insert into image (name, relative_path, active) values ('slika3', 'path3.jpg', true);
insert into image (name, relative_path, active) values ('slika4', 'path4.jpg', true);
insert into image (name, relative_path, active) values ('slika5','path5.jpg', true);
insert into image (name, relative_path, active) values ('slika6','path6.jpg', true);

insert into users (type, active, email, first_name, last_name, password, verified, image_id)
values ('admin', true,'admin@gmail.com', 'Marko','Markovic','$2a$10$RVzuprKddsjdq6P8QWmqF.sCj2uYPIUlbFVB.b7tJ9RdFNOOBNoXO',true,1);
insert into users (type, active, email, first_name, last_name, password, verified, image_id)
values ('registered_user',true,'trajkovicka.9909@gmail.com', 'Ivan','Ivanovic','$2a$10$RVzuprKddsjdq6P8QWmqF.sCj2uYPIUlbFVB.b7tJ9RdFNOOBNoXO',true,2);
insert into users (type, active, email, first_name, last_name, password, verified, image_id)
values ('registered_user',true,'user2@gmail.com', 'Ivana','Ivanovic','$2a$10$RVzuprKddsjdq6P8QWmqF.sCj2uYPIUlbFVB.b7tJ9RdFNOOBNoXO',true,3);

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
values (5.0, '2020-12-25 19:30:00', 'opisneki', 44.05, 45.02,'Belgrade', 'obilazak muzeja', 1, 1, true);
insert into cultural_offer (average_rate, date, description, lat, lon, city, name, type_id, admin_id, active)
values (5.0, '2021-07-11 19:30:00', 'opisneki', 44.05, 45.02, 'Novi sad', 'exit', 3, 1, false);
insert into cultural_offer (average_rate, date, description, lat, lon, city, name, type_id, admin_id, active)
values (5.0, '2021-07-11 19:30:00', 'opisneki', 44.05, 45.02, 'Belgrade', 'djiasjasij', 3, 1, true);

insert into users_favorite_cultural_offers (registered_user_id, favorite_cultural_offers_id)
values (2, 1);
insert into users_favorite_cultural_offers (registered_user_id, favorite_cultural_offers_id)
values (2, 2);

insert into post (date, text, image_id, cultural_offer_id, active)
values('2020-12-24 19:30:00', 'gosti iznenadjenja', 4, 2, true);
insert into post (date, text, image_id, cultural_offer_id, active)
values('2020-10-24 19:30:00', 'promocija knjige', 1, 1, true);

insert into comment (date, text, image_id, cultural_offer_id, registred_user_id, active)
values ('2020-10-24 19:30:00', 'puno eksponata ima', 1, 1, 2, true);
insert into comment (date, text, image_id, cultural_offer_id, registred_user_id, active)
values ('2020-10-24 19:30:00', 'mnogo velika guzva', 4, 2, 3, true);

insert into rate (number, cultural_offer_id, registred_user_id, active) values (5, 1, 2, true);
insert into rate (number, cultural_offer_id, registred_user_id, active) values (5, 2, 3, true);
-- 

