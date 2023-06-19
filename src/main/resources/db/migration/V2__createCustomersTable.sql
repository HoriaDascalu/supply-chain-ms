create table customers (
                           customer_id  bigserial not null,
                           first_name varchar(255),
                           last_name varchar(255),
                           primary key (customer_id)
);