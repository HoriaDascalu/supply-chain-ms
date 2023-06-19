package com.supply.chain.mapper;

import com.supply.chain.dto.OrderItemDto;
import com.supply.chain.model.OrderItem;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrderItemMapper {
    public static OrderItem mapToOrderItem(OrderItemDto orderItemDto){
        return ((orderItemDto != null) ? (OrderItem.builder()
                .orderItemId(orderItemDto.getOrderItemId())
                .name(orderItemDto.getName())
                .quantity(orderItemDto.getQuantity())
                .build()) : null);
    }

    public static OrderItemDto mapToOrderItemDto(OrderItem orderItem){
        return OrderItemDto.builder()
                .orderItemId(orderItem.getOrderItemId())
                .name(orderItem.getName())
                .quantity(orderItem.getQuantity())
                .build();
    }
}
