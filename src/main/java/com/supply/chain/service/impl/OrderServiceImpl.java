package com.supply.chain.service.impl;

import com.supply.chain.dto.OrderDto;
import com.supply.chain.dto.OrderDtoGetAll;
import com.supply.chain.exception.ResourceNotFoundException;
import com.supply.chain.mapper.OrderMapper;
import com.supply.chain.mapper.OrderMapperToDto;
import com.supply.chain.model.Order;
import com.supply.chain.model.User;
import com.supply.chain.repository.OrderRepository;
import com.supply.chain.repository.UserRepository;
import com.supply.chain.service.OrderService;
import com.supply.chain.util.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    private final OrderMapperToDto mapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderMapperToDto mapper, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<OrderDtoGetAll> getAllOrders(Collection<? extends GrantedAuthority> roles, String username) {

        List<String> roleNames = new ArrayList<>();
        for(GrantedAuthority role: roles){
            roleNames.add(role.getAuthority());
        }

        if(roleNames.contains("ROLE_ADMIN")){
            return orderRepository.findAllByIsDeletedIsFalse()
                    .stream()
                    .map(OrderMapper::mapToOrderDtoForGetAll)
                    .collect(Collectors.toList());
        }else{
            if(roleNames.contains("ROLE_MANUFACTURER")){
                return orderRepository.findByUsernameOrStatusNew(username)
                        .stream()
                        .map(OrderMapper::mapToOrderDtoForGetAll)
                        .collect(Collectors.toList());
            }
        }

        return orderRepository.findByUsername(username)
                .stream()
                .map(OrderMapper::mapToOrderDtoForGetAll)
                .collect(Collectors.toList());

    }

    @Override
    public OrderDto saveOrder(OrderDto orderDto, String username) {
        Order order = OrderMapper.mapToOrder(orderDto);
        User user = userRepository.findUserByUsername(username).get();
        Integer max = orderRepository.getOrderNumberMaxValue();
        order.setOrderNumber(((max != null) ? max + 1 : 1));
        order.setUser(user);
        order.setStatus("NEW");
        return OrderMapper.mapToOrderDto(orderRepository.save(order));
    }

    @Override
    public void deleteOrder(Long orderId, String username) {
        Order order = findById(orderId);
        if(!order.getUser().getUsername().equals(username)){
            throw new ResourceNotFoundException("No order with id: " + orderId + " for user with username: " + username,
                    "order-not-found");
        }
        order.setDeleted(true);
        orderRepository.save(order);
    }

    @Override
    public OrderDto updateOrder(OrderDto orderDto, Long orderId, String username) {
        Order order = findById(orderId);

        if(!order.getUser().getUsername().equals(username)){
            throw new ResourceNotFoundException("No order with id: " + orderId + " for user with username: " + username,
                    "order-not-found");
        }

        mapper.updateOrderFromDto(orderDto,order);
        Order updatedOrder = orderRepository.save(order);

        return OrderMapper.mapToOrderDto(updatedOrder);
    }

    @Override
    public OrderDto cancelOrderById(Long orderId, String username) {
        Order order = findById(orderId);

        if(!order.getUser().getUsername().equals(username)){
            throw new ResourceNotFoundException("No order with id: " + orderId + " for user with username: " + username,
                    "order-not-found");
        }

        order.setCanceled(true);
        Order canceledOrder =  orderRepository.save(order);
        return OrderMapper.mapToOrderDto(canceledOrder);
    }

    @Override
    public OrderDto getOrderById(Long orderId,String username) {
        Order order = findById(orderId);

        if(!order.getUser().getUsername().equals(username)){
            throw new ResourceNotFoundException("No order with id: " + orderId + " for user with username: " + username,
                    "order-not-found");
        }
        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    public OrderDto updateOrderStatus(Long orderId, String username, String statusName) {
        User user = userRepository.findUserByUsername(username).get();
        Order order = findById(orderId);
        String orderStatus = order.getStatus();
        if(OrderStatus.ANALYSIS.name().equalsIgnoreCase(statusName)){
            if(orderStatus.equals(OrderStatus.NEW.name())){
                order.setStatus(OrderStatus.ANALYSIS.name());
            }else{
                throw new ResourceNotFoundException("No order with id: " + orderId + " for user with username: " + username
                        + " to be marked as in analysis", "order-not-found");
            }
        }else if(OrderStatus.NEW.name().equalsIgnoreCase(statusName)){
            if(orderStatus.equals(OrderStatus.ANALYSIS.name())){
                order.setStatus(OrderStatus.NEW.name());
            }else{
                throw new ResourceNotFoundException("No order with id: " + orderId + " for user with username: " + username
                        + " to be marked as new", "order-not-found");
            }
        }else if(OrderStatus.IN_PROGRESS.name().equalsIgnoreCase(statusName)){
            if(orderStatus.equals(OrderStatus.ANALYSIS.name())){
                    order.setStatus(OrderStatus.IN_PROGRESS.name());
                    order.setUser(user);
            }else{
                throw new ResourceNotFoundException("No order with id: " + orderId + " for user with username: " + username
                        + " to be marked as in progress", "order-not-found");
            }
        }else if(OrderStatus.READY_FOR_DELIVERY.name().equalsIgnoreCase(statusName)){
            if(!order.getUser().getUsername().equals(username)){
                throw new ResourceNotFoundException("No order with id: " + orderId + " for user with username: " + username
                        + " to be marked as ready for delivery", "order-not-found");
            }else{
                order.setStatus(OrderStatus.READY_FOR_DELIVERY.name());
            }

        }
        return OrderMapper.mapToOrderDto(orderRepository.save(order));
    }


    private Order findById(Long orderId){
        Optional<Order> order = orderRepository.findById(orderId);

        if(order.isEmpty() || order.get().isDeleted()){
            throw new ResourceNotFoundException("No order with id: " + orderId, "order-not-found");
        }
        return order.get();
    }

}

