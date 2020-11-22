create table article(
id serial primary key,
content text,
url text unique,
created_at timestamp
);

create table users(
id serial primary key,
company int,
email text unique,
config json
);

insert into users values (1, 14, 'felipexavier20015@gmail.com', '{"phrase":"aviso", "operator": "EQUALS", "affirmations":["licitação","construção"],"denials":[]}');