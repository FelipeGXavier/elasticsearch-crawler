create table article(
id serial primary key,
content text,
url text unique,
created_at timestamp
);



