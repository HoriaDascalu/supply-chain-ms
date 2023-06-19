package com.supply.chain.mapper;

import com.supply.chain.dto.OrderItemDto;
import com.supply.chain.model.OrderItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class OrderItemMapperTests {

    @DisplayName("mapToOrderItemDto method")
    @Test
    public void givenOrderItemObject_whenMapToOrderItemDto_thenReturnOrderItemDtoObject(){
        OrderItem orderItem = new OrderItem("tricou",10);
        OrderItemDto orderItemDto = OrderItemMapper.mapToOrderItemDto(orderItem);
        assertThat(orderItemDto).isNotNull();
        assertThat(orderItemDto.getOrderItemId()).isEqualTo(orderItem.getOrderItemId());
        assertThat(orderItemDto.getName()).isEqualTo(orderItem.getName());
        assertThat(orderItemDto.getQuantity()).isEqualTo(orderItem.getQuantity());
    }

    @DisplayName("mapToOrderItem method")
    @Test
    public void givenOrderItemDtoObject_whenMapToOrderItem_thenReturnOrderItemObject(){
        OrderItemDto orderItemDto = new OrderItemDto(null,"tricou",10);
        OrderItem orderItem = OrderItemMapper.mapToOrderItem(orderItemDto);
        assertThat(orderItem).isNotNull();
        assertThat(orderItem.getOrderItemId()).isEqualTo(orderItemDto.getOrderItemId());
        assertThat(orderItem.getName()).isEqualTo(orderItemDto.getName());
        assertThat(orderItem.getQuantity()).isEqualTo(orderItemDto.getQuantity());
    }
}
