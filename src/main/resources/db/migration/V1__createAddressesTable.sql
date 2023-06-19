create table addresses (
                           address_id  bigserial not null,
                           city varchar(255),
                           country varchar(255),
                           phone_number varchar(255),
                           street varchar(255),
                           primary key (address_id)
);