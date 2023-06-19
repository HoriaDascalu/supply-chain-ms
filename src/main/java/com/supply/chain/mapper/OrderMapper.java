package com.supply.chain.mapper;

import com.supply.chain.dto.OrderDto;
import com.supply.chain.dto.OrderDtoGetAll;
import com.supply.chain.model.Order;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrderMapper {

    public static Order mapToOrder(OrderDto orderDto){
        return Order.builder()
                .orderId(orderDto.getOrderId())
                .isCanceled(orderDto.isCanceled())
                .orderItems(orderDto.getOrderItems()
                        .stream()
                        .map(OrderItemMapper::mapToOrderItem)
                        .collect(Collectors.toList()))
                .deliveryAddress(AddressMapper.mapToAddress(orderDto.getAddress()))
                .build();
    }

    public static OrderDto mapToOrderDto(Order order){
        return OrderDto.builder()
                .orderId(order.getOrderId())
                .isCanceled(order.isCanceled())
                .orderNumber(order.getOrderNumber())
                .status(order.getStatus())
                .orderItems(order.getOrderItems()
                        .stream()
                        .map((OrderItemMapper::mapToOrderItemDto))
                        .collect(Collectors.toList()))
                .address(AddressMapper.mapToAddressDto(order.getDeliveryAddress()))
                .build();
    }

    public static OrderDtoGetAll mapToOrderDtoForGetAll(Order order){
        return OrderDtoGetAll.builder()
                .orderId(order.getOrderId())
                .isCanceled(order.isCanceled())
                .orderNumber(order.getOrderNumber())
                .status(order.getStatus())
                .creationTime(order.getCreationTime())
                .orderSize(order.getOrderItems().size())
                .build();
    }
}
