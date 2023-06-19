package com.supply.chain.service;

import com.supply.chain.dto.OrderDto;
import com.supply.chain.dto.OrderDtoGetAll;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public interface OrderService {
    List<OrderDtoGetAll> getAllOrders(Collection<? extends GrantedAuthority> roles, String username);

    OrderDto saveOrder(OrderDto orderDto, String username);

    void deleteOrder(Long orderId, String username);

    OrderDto updateOrder(OrderDto orderDto, Long orderId, String username);

    OrderDto cancelOrderById(Long orderId, String username);

    OrderDto getOrderById(Long orderId,String username);

    OrderDto updateOrderStatus(Long orderId, String username, String statusName);

}
