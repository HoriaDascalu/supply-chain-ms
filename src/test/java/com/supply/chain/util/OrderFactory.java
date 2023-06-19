package com.supply.chain.util;

import com.supply.chain.model.Order;
import com.supply.chain.model.OrderItem;
import com.supply.chain.model.Address;
import com.supply.chain.model.Role;
import com.supply.chain.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrderFactory {
    public static Order getBaseOrder() {
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem("Bluza", 10));
        orderItems.add(new OrderItem("Vesta", 5));
        Address address = new Address("Main", "City", "11111", "Romania","Horia","Dascalu");
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ROLE_USER"));
        User user = new User(1,"bill","$2a$12$KOrqURcirdaPldaXPjTI.eJjxB8cVeWpW38cONK5.dzsY52r0C3h6",true, roles);

        return new Order(1L,
                1,
                false,
                "NEW",
                null,
                false,
                user,
                orderItems,
                address);
    }

    public static Order getDeletedOrder() {
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem("Tricou", 100));
        orderItems.add(new OrderItem("Camasa", 50));
        Address address = new Address("Street", "City", "000000", "Country","Horia","Dascalu");
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ROLE_USER"));
        User user = new User(1,"bill","$2a$12$KOrqURcirdaPldaXPjTI.eJjxB8cVeWpW38cONK5.dzsY52r0C3h6", true,roles);

        return new Order(1L,
                1,
                true,
                "NEW",
                null,
                false,
                user,
                orderItems,
                address);
    }

    public static Order getCanceledOrder() {
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem("Tricou", 100));
        orderItems.add(new OrderItem("Camasa", 50));
        Address address = new Address("Street", "City", "000000", "Country","Horia","Dascalu");
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ROLE_USER"));
        User user = new User(1,"bill","$2a$12$KOrqURcirdaPldaXPjTI.eJjxB8cVeWpW38cONK5.dzsY52r0C3h6", true,roles);

        return new Order(1L,
                1,
                false,
                "NEW",
                null,
                true,
                user,
                orderItems,
                address);
    }

}
