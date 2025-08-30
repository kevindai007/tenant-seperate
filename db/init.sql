create database tenant-separate;

CREATE SCHEMA r100_jane;
CREATE SCHEMA r100_mike;


create table r100_jane.goods (
                       id            bigint generated always as identity primary key,
                       goods_name varchar(256),
                       created_at    timestamptz not null default now(),
                       updated_at    timestamptz not null default now(),
                       tenant_id varchar(255)
);


create table r100_mike.goods (
                            id            bigint generated always as identity primary key,
                            goods_name varchar(256),
                            created_at    timestamptz not null default now(),
                            updated_at    timestamptz not null default now(),
                            tenant_id varchar(255)
);


create table public.goods (
                              id            bigint generated always as identity primary key,
                              goods_name varchar(256),
                              created_at    timestamptz not null default now(),
                              updated_at    timestamptz not null default now(),
                              tenant_id varchar(255)
);


create table public.price (
                              id            bigint generated always as identity primary key,
                              goods_name varchar(256),
                              price numeric(12,2) DEFAULT 0 NOT NULL,
                              created_at    timestamptz not null default now(),
                              updated_at    timestamptz not null default now(),
                              tenant_id varchar(255)
);

create table r100_mike.price (
                                 id            bigint generated always as identity primary key,
                                 goods_name varchar(256),
                                 price numeric(12,2) DEFAULT 0 NOT NULL,
                                 created_at    timestamptz not null default now(),
                                 updated_at    timestamptz not null default now()
);


create table r100_jane.price (
                                 id            bigint generated always as identity primary key,
                                 goods_name varchar(256),
                                 price numeric(12,2) DEFAULT 0 NOT NULL,
                                 created_at    timestamptz not null default now(),
                                 updated_at    timestamptz not null default now()
);