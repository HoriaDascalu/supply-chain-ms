create table orders (
                        order_id  bigserial not null,
                        creation_time timestamp,
                        is_canceled boolean not null,
                        is_deleted boolean default false not null,
                        order_number int4,
                        status varchar(255),
                        customer_id int8,
                        address_id int8,
                        user_id int4,
                        primary key (order_id)
);