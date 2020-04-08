create table literals (
    id serial primary key,
    type varchar(20) not null,
    source varchar(20) not null,
    destination varchar(20) not null,
    perception integer not null,
    word text not null,
    translation text not null,
    label_id integer references labels(id) on update cascade on delete cascade,
    user_id integer not null references users(id) on update cascade on delete cascade
);