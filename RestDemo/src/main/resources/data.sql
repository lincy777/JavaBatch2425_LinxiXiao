truncate table product;
truncate table lookup;
insert into product (name, price, stock)
values('iPhone',1000, 700);

insert into product (name, price, stock)
values('iPad',500, 200);

insert into product (name, price, stock)
values('Mac',1000, 100);

insert into product (name, price, stock)
values('Airpods',100, 500);

insert into product (name, price, stock)
values('Chips',3, 800);

insert into product (name, price, stock)
values('Coffee',5, 900);

insert into product (name, price, stock)
values('Milk',3, 300);

insert into product (name, price, stock)
values('Cheese',5, 400);

insert into product (name, price, stock)
values('Egg',2, 100);

insert into product (name, price, stock)
values('Bag',30, 100);

insert into product (name, price, stock)
values('Shoe',50, 100);

insert into lookup (id, type, name, info)
values(1, 'PRODUCT_MESSAGE', 'PRODUCT_CREATED', 'Successfully Added Product');

insert into lookup (id, type, name, info)
values(2, 'PRODUCT_MESSAGE', 'PRODUCT_NOT_FOUND', 'Cannot Find Product');

insert into lookup (id, type, name, info)
values(3, 'PRODUCT_MESSAGE', 'PRODUCT_DELETED', 'Product is Deleted');