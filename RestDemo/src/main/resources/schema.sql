create table IF NOT EXISTS lookup(
    id integer primary key not null,
    type varchar(255) not null,
    name varchar(255) not null,
    info varchar(255) not null
);

create table IF NOT EXISTS product(
    id integer primary key not null auto_increment,
    name varchar(255) not null,
    price integer not null,
    stock integer not null
);
