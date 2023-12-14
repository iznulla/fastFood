
-- create country
insert into country (name) values ('Uzbekistan');

--create city
insert into city (name, country_id) values ('Tashkent', 1);
insert into city (name, country_id) values ('Samarkand', 1);
insert into city (name, country_id) values ('Bukhara', 1);
insert into city (name, country_id) values ('Andijon', 1);
insert into city (name, country_id) values ('Jizzax', 1);
insert into city (name, country_id) values ('Navoiy', 1);
insert into city (name, country_id) values ('Namangan', 1);
insert into city (name, country_id) values ('Surxondaryo', 1);
insert into city (name, country_id) values ('Qashqadaryo', 1);
insert into city (name, country_id) values ('Xorazm', 1);
insert into city (name, country_id) values ('Fargona', 1);
insert into city (name, country_id) values ('Sirdaryo', 1);

-- create restaurant
insert into restaurant (name) values ('Belissimo');
insert into restaurant (name) values ('Evos');

--create address
insert into address (street, country_id, city_id) values ('Buyuk Ipak Yuli', 1, 1);
insert into address (street, country_id, city_id, longitude, latitude) values ('MEGA', 1, 1, 41.36970942223147, 69.29096999864346);
insert into address (street, country_id, city_id) values ('Pushkin', 1, 1);
insert into address (street, country_id, city_id) values ('Vokzal', 1, 1);
insert into address (street, country_id, city_id, longitude, latitude) values ('ECO Bozor', 1, 1, 41.35473807941441, 69.35377212618005);
insert into address (street, country_id, city_id, longitude, latitude) values ('SETOR steeet', 1, 1, 41.37877290431757, 69.28014154570097);
insert into address (street, country_id, city_id, longitude, latitude) values ('Yunusobod', 1, 1, 41.36150921509543, 69.27684379953357);
insert into address (street, country_id, city_id, longitude, latitude) values ('Parkentskiy', 1, 1, 41.31667834566728, 69.32820439953356);

-- create restaurant filial
insert into restaurant_filial (name, address_id, restaurant_id) values ('Belissimo ECO', 5, 1);
insert into restaurant_filial (name, address_id, restaurant_id) values ('Belissimo SETOR', 6, 1);
insert into restaurant_filial (name, address_id, restaurant_id) values ('Evos Yunusobod', 7, 2);
insert into restaurant_filial (name, address_id, restaurant_id) values ('Evos Parkentskiy', 8, 2);


-- create user profile
--insert into user_profile (name, surname, user_id, address_id) values ('Admin', 'Admin', 1, 1);
--insert into user_profile (name, surname, user_id, address_id) values ('Freddy', 'Barens', 2, 2);
--insert into user_profile (name, surname, user_id, address_id) values ('John', 'Doe', 3, 3);
--insert into user_profile (name, surname, user_id, address_id) values ('Bugi', 'Man', 4, 4);

-- create menu
-- Evos
insert into menu (name, cooking_time, price, restaurant_id) values ('Burger Evos', 4, 45000, 2);
insert into menu (name, cooking_time, price, restaurant_id) values ('Pizza Evos', 5, 30000, 2);
insert into menu (name, cooking_time, price, restaurant_id) values ('Pasta Evos', 6, 55000, 2);
insert into menu (name, cooking_time, price, restaurant_id) values ('Fish Evos', 7, 87000, 2);

-- Belissimo
insert into menu (name, cooking_time, price, restaurant_id) values ('Burger Belissimo', 4, 45000, 1);
insert into menu (name, cooking_time, price, restaurant_id) values ('Pizza Belissimo', 5, 30000, 1);
insert into menu (name, cooking_time, price, restaurant_id) values ('Pasta Belissimo', 6, 55000, 1);
insert into menu (name, cooking_time, price, restaurant_id) values ('Fish Belissimo', 7, 87000, 1);

-- create role
insert into role (name) values ('ADMIN');
insert into role (name) values ('EMPLOYEE');

-- create privilege
insert into privilege (name) values ('READ'); --1
insert into privilege (name) values ('CREATE'); --2
insert into privilege (name) values ('UPDATE'); --3
insert into privilege (name) values ('DELETE'); --4
insert into privilege (name) values ('READ_USER'); --5
insert into privilege (name) values ('DELETE_USER'); --6
insert into privilege (name) values ('UPDATE_USER'); --7
insert into privilege (name) values ('ORDER_SERVICE'); --8
insert into privilege (name) values ('CREATE_COUNTRY_AND_CITY'); --9
insert into privilege (name) values ('CREATE_RESTAURANT_AND_FILIAL'); --10


-- create role_privilege
insert into role_privilege (role_id, privilege_id) values (1, 1);
insert into role_privilege (role_id, privilege_id) values (1, 2);
insert into role_privilege (role_id, privilege_id) values (1, 3);
insert into role_privilege (role_id, privilege_id) values (1, 4);
insert into role_privilege (role_id, privilege_id) values (1, 5);
insert into role_privilege (role_id, privilege_id) values (1, 6);
insert into role_privilege (role_id, privilege_id) values (1, 7);
insert into role_privilege (role_id, privilege_id) values (1, 8);
insert into role_privilege (role_id, privilege_id) values (1, 9);
insert into role_privilege (role_id, privilege_id) values (1, 10);
insert into role_privilege (role_id, privilege_id) values (2, 1);
insert into role_privilege (role_id, privilege_id) values (2, 5);
insert into role_privilege (role_id, privilege_id) values (2, 8);
insert into role_privilege (role_id, privilege_id) values (2, 3);

--UPDATE users SET role_id = 1 WHERE id = 1;
--update users set role_id = 2 where id = 2;
