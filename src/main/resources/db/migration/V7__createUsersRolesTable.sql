create table users_roles (
                             user_id int4 not null,
                             role_id int4 not null,
                             primary key (user_id, role_id)
);