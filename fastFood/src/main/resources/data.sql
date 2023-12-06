
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

--create address
insert into address (street, country_id, city_id) values ('Buyuk Ipak Yuli', 1, 1);
insert into address (street, country_id, city_id, longitude, latitude) values ('MEGA', 1, 1, 41.36970942223147, 69.29096999864346);
insert into address (street, country_id, city_id) values ('Pushkin', 1, 1);
insert into address (street, country_id, city_id) values ('Vokzal', 1, 1);
insert into address (street, country_id, city_id, longitude, latitude, restaurant_id) values ('ECO', 1, 1, 41.35473807941441, 69.35377212618005, 1);
insert into address (street, country_id, city_id, longitude, latitude, restaurant_id) values ('SETOR', 1, 1, 41.37877290431757, 69.28014154570097, 1);


-- create user profile
insert into user_profile (name, surname, user_id, address_id) values ('Admin', 'Admin', 1, 1);
insert into user_profile (name, surname, user_id, address_id) values ('Freddy', 'Barens', 2, 2);
insert into user_profile (name, surname, user_id, address_id) values ('John', 'Doe', 3, 3);
insert into user_profile (name, surname, user_id, address_id) values ('Bugi', 'Man', 4, 4);

-- create menu
insert into menu (name, cooking_time, price, restaurant_id) values ('Burger', 4, 45000, 1);
insert into menu (name, cooking_time, price, restaurant_id) values ('Pizza', 5, 30000, 1);
insert into menu (name, cooking_time, price, restaurant_id) values ('Pasta', 6, 55000, 1);
insert into menu (name, cooking_time, price, restaurant_id) values ('Fish', 7, 87000, 1);