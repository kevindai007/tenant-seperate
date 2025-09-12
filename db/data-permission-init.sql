create table user_info
(
    id         bigint generated always as identity primary key,
    username   varchar(50) not null,
    created_at timestamp            default current_timestamp,
    updated_at timestamptz not null default now()
);


create table role_info
(
    id         bigint generated always as identity primary key,
    role_name  varchar(50) not null,
    created_at timestamp            default current_timestamp,
    updated_at timestamptz not null default now()
);


create table role_data_scope
(
    id         bigint generated always as identity primary key,
    role_id    bigint      not null,
    data_scope varchar(50) not null,
    created_at timestamp            default current_timestamp,
    updated_at timestamptz not null default now()
);

create table entity_info
(
    id          bigint generated always as identity primary key,
    name        varchar(255) not null,
    description text,
    parent_id   bigint       references entity_info (id) on delete set null,
    created_at  timestamp             default current_timestamp,
    updated_at  timestamptz  not null default now()
);

create table entity_closure
(
    ancestor_id   bigint not null references entity_info (id) on delete cascade,
    descendant_id bigint not null references entity_info (id) on delete cascade,
    depth         int    not null,
    primary key (ancestor_id, descendant_id)
);

create table user_role
(
    user_id bigint not null references user_info (id) on delete cascade,
    role_id bigint not null references role_info (id) on delete cascade,
    primary key (user_id, role_id)
);

create table user_entity
(
    user_id   bigint not null references user_info (id) on delete cascade,
    entity_id bigint not null references entity_info (id) on delete cascade,
    primary key (user_id, entity_id)
);


insert into entity_info(name, description, parent_id)
values ('Root Entity', 'The root of all entities', null),
       ('dept1', 'Department 1', 1),
       ('dept2', 'Department 2', 1),
       ('dept1-1', 'Department 1-1', 2),
       ('dept1-2', 'Department 1-2', 2),
       ('dept2-1', 'Department 2-1', 3);


insert into entity_closure (ancestor_id, descendant_id, depth)
values (1, 1, 0),
       (1, 2, 1),
       (1, 3, 1),
       (1, 4, 2),
       (1, 5, 2),
       (1, 6, 2),
       (2, 2, 0),
       (2, 4, 1),
       (2, 5, 1),
       (3, 3, 0),
       (3, 6, 1),
       (4, 4, 0),
       (5, 5, 0),
       (6, 6, 0);