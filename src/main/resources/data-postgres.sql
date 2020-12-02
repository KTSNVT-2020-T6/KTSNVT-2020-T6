insert into authority (role) values ('REGISTERED_USER');
insert into authority (role) values ('ADMIN');

insert into image (name, relative_path) values ('slika','path.jpg');
insert into image (name, relative_path) values ('slika2', 'path2.jpg');
insert into image (name, relative_path) values ('slika3', 'path3.jpg');
insert into image (name, relative_path) values ('slika4', 'path4.jpg');

insert into users (type, active, email, first_name, last_name, password, verified, image_id)
values ('admin', true,'admin@gmail.com', 'Marko','Markovic','sifra',true,1);
insert into users (type, active, email, first_name, last_name, password, verified, image_id)
values ('registered_user',true,'user@gmail.com', 'Ivan','Ivanovic','sifra123',true,2);
insert into users (type, active, email, first_name, last_name, password, verified, image_id)
values ('registered_user',true,'user2@gmail.com', 'Ivana','Ivanovic','sifra1234',true,3);

insert into users_authority (user_id, authority_id) values (1,2);
insert into users_authority (user_id, authority_id) values (2,1);
insert into users_authority (user_id, authority_id) values (3,1);

insert into category (name, description) values ('institution', 'institutions in serbia');
insert into category (name, description) values ('manifestation', 'manifestations in serbia');

insert into type (name, description, category_id) values ('museum', 'museums in serbia', 1);
insert into type (name, description, category_id) values ('gallery', 'galleries in serbia', 1);
insert into type (name, description, category_id) values ('festival', 'festivals in serbia', 2);
insert into type (name, description, category_id) values ('opera', 'operas in serbia', 2);

insert into cultural_offer (average_rate, date, description, lat, lon, name, type_id, admin_id)
values (5.0, '2020-12-25 19:30:00', 'opisneki', 44.05, 45.02, 'obilazak muzeja', 1, 1);
insert into cultural_offer (average_rate, date, description, lat, lon, name, type_id, admin_id)
values (5.0, '2021-07-11 19:30:00', 'opisneki', 44.05, 45.02, 'exit', 3, 1);

insert into rate (number, cultural_offer_id, registred_user_id) values (5, 1, 2);
insert into rate (number, cultural_offer_id, registred_user_id) values (5, 2, 3);
-- 




