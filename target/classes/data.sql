delete from  shop_user;
delete from transaction_type;
delete from role;
delete from business_entity;
delete from product;
alter sequence role_seq restart with 1;
alter sequence user_seq restart with 1;
alter sequence trans_type_seq restart with 1;

insert into role (id, name) values (nextval('role_seq'), 'admin');
insert into role (id, name) values (nextval('role_seq'), 'accountant');
insert into role (id, name) values (nextval('role_seq'), 'cashier');

insert into shop_user(id, login, password, role_id) values (nextval('user_seq'), 'accountant2', 'password', 2);
insert into shop_user(id, login, password, role_id) values (nextval('user_seq'), 'accountant3', 'password', 2);
insert into shop_user(id, login, password, role_id) values (nextval('user_seq'), 'accountant4', 'password', 2);
insert into shop_user(id, login, password, role_id) values (nextval('user_seq'), 'admin', 'password', 1);
insert into shop_user(id, login, password, role_id) values (nextval('user_seq'), 'cashier2', 'password', 3);
insert into shop_user(id, login, password, role_id) values (nextval('user_seq'), 'cashier3', 'password', 3);
insert into shop_user(id, login, password, role_id) values (nextval('user_seq'), 'cashier4', 'password', 3);

insert into business_entity(inn, address, email, giro, name, phone)
 values (435432534, 'Ульяновск', 'email', 531254321, 'Company 1', '103252');
 insert into business_entity(inn, address, email, giro, name, phone)
 values (214432532, 'Ульяновск', 'email', 235254321, 'Company 2', '921329');
 insert into business_entity(inn, address, email, giro, name, phone)
 values (59243253, 'Ульяновск', 'email', 510254321, 'Company 3', '603534');
 insert into business_entity(inn, address, email, giro, name, phone)
 values (61543253, 'Самара', 'email', 640254321, 'Company 4', '473032');
 insert into business_entity(inn, address, email, giro, name, phone)
 values (34543253, 'Москва', 'email', 700254321, 'Company 5', '352432');
 insert into business_entity(inn, address, email, giro, name, phone)
 values (13243253, 'Казань', 'email', 924254321, 'Company 6', '321322');

insert into product(id, name, quantity, sell_price)
values ('FDS4321', 'Ноутбук', 5, 25000);
insert into product(id, name, quantity, sell_price)
values ('FS24321', 'Монопод', 5, 1500);
insert into product(id, name, quantity, sell_price)
values ('FG64321', 'Планшет', 5, 17000);
insert into product(id, name, quantity, sell_price)
values ('FO34321', 'Видеокарта', 5, 19000);
insert into product(id, name, quantity, sell_price)
values ('F324321', 'Чехол', 5, 1000);
insert into product(id, name, quantity, sell_price)
values ('FCC4321', 'Колонки', 5, 4500);
insert into product(id, name, quantity, sell_price)
values ('F5J4321', 'Наушники', 5, 2000);

insert into transaction_type (id, name) values (nextval('trans_type_seq'), 'buy');
insert into transaction_type (id, name) values (nextval('trans_type_seq'), 'sell');