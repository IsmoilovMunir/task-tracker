create table users (
    id bigserial primary key,
    email varchar not null,
    password varchar not null,
    created_at timestamp
);