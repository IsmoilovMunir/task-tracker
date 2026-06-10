create table tasks(
    id bigserial primary key,
    title varchar not null,
    description text,
    done boolean default false,
    user_id bigint,
    crated_at timestamp,
    foreign key (user_id) references users(id)
);