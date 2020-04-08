create table exercises (
    id serial primary key,
    version integer not null,
    description text,
    template text not null
);