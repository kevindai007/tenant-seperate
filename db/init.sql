CREATE SCHEMA jane;
CREATE SCHEMA mike;


create table jane.goods (
                       id            bigint generated always as identity primary key,
                       goods_name varchar(256),
                       created_at    timestamptz not null default now(),
                       updated_at    timestamptz not null default now()
);


create table mike.goods (
                            id            bigint generated always as identity primary key,
                            goods_name varchar(256),
                            created_at    timestamptz not null default now(),
                            updated_at    timestamptz not null default now()
);