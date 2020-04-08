create table users (
    id serial primary key,
    name varchar(100) not null unique,
    password varchar(128) not null
);