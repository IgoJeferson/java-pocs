create sequence hibernate_sequence;
create table post (
    id bigint not null primary key,
    title varchar(128) not null,
    content varchar(128) not null
);

create table comment (
    id bigint not null primary key,
    post_id bigint not null references post (id),
    comment varchar(160)
);
