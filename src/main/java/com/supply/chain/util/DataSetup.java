package com.supply.chain.util;

import com.supply.chain.model.OrderItem;
import com.supply.chain.model.Address;
import com.supply.chain.model.Role;
import com.supply.chain.model.User;
import com.supply.chain.model.Order;
import com.supply.chain.repository.AddressRepository;
import com.supply.chain.repository.OrderRepository;
import com.supply.chain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@ConditionalOnExpression("${insert.test.data}")
public class DataSetup implements ApplicationRunner {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    private final AddressRepository addressRepository;


    @Autowired
    public DataSetup(OrderRepository orderRepository, AddressRepository addressRepository, UserRepository userRepository) {

        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
//        List<OrderItem> orderItems = List.of(new OrderItem("Tricou", 100), new OrderItem("Camasa", 50));
//
//        Address address = new Address("Street", "City", "000000", "Country","Horia","Dascalu");
        Set<Role> roles = Set.of(new Role("ROLE_ADMIN"));
        User user = new User(1,"admin","$2a$12$KOrqURcirdaPldaXPjTI.eJjxB8cVeWpW38cONK5.dzsY52r0C3h6",false, roles);
        userRepository.save(user);

//        addressRepository.save(address);
//
//        Order order = new Order(null,1,false,"NEW",null,false,user,orderItems,address);
//        orderRepository.save(order);

    }
}
