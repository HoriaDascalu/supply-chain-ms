alter table orders
    add constraint UKt5ee3vjmonruwsp9g423dhrek unique (order_number);

alter table users
    add constraint UKr43af9ap4edm43mmtq01oddj6 unique (username);

alter table order_items
    add constraint FKbioxgbv59vetrxe0ejfubep1w
        foreign key (order_id)
            references orders;

alter table orders
    add constraint FKpxtb8awmi0dk6smoh2vp1litg
        foreign key (customer_id)
            references customers;

alter table orders
    add constraint FKhlglkvf5i60dv6dn397ethgpt
        foreign key (address_id)
            references addresses;

alter table orders
    add constraint FK32ql8ubntj5uh44ph9659tiih
        foreign key (user_id)
            references users;

alter table users_roles
    add constraint FKj6m8fwv7oqv74fcehir1a9ffy
        foreign key (role_id)
            references roles;

alter table users_roles
    add constraint FK2o0jvgh89lemvvo17cbqvdxaa
        foreign key (user_id)
            references users;