create table order_items (
                             order_item_id  bigserial not null,
                             name varchar(255),
                             quantity int4,
                             order_id int8,
                             primary key (order_item_id)
);