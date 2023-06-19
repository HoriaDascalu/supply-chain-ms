package com.supply.chain.mapper;

import com.supply.chain.dto.OrderDtoGetAll;
import com.supply.chain.dto.OrderDto;
import com.supply.chain.dto.OrderItemDto;
import com.supply.chain.dto.AddressDto;
import com.supply.chain.model.Order;
import com.supply.chain.util.OrderFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class OrderMapperTests {

    @DisplayName("mapToOrderDto method")
    @Test
    public void givenOrderObject_whenMapToOrderDto_thenReturnOrderDtoObject(){
        Order order = OrderFactory.getBaseOrder();
        OrderDto orderDto = OrderMapper.mapToOrderDto(order);
        assertThat(orderDto).isNotNull();
        assertThat(orderDto.getOrderId()).isEqualTo(order.getOrderId());
        assertThat(orderDto.getOrderNumber()).isEqualTo(order.getOrderNumber());
    }

    @DisplayName("mapToOrderDto method")
    @Test
    public void givenOrderDtoObject_whenMapToOrder_thenReturnOrderObject(){
        List<OrderItemDto> orderItemsDto = new ArrayList<>();
        orderItemsDto.add(new OrderItemDto(null,"Tricou", 100));
        orderItemsDto.add(new OrderItemDto(null,"Camasa", 50));
        AddressDto addressDto = new AddressDto(null,"Street", "City", "000000", "Country","Horia","Dascalu");

        OrderDto orderDto = new OrderDto(1L,1,"NEW",false,null,orderItemsDto,addressDto);
        Order order = OrderMapper.mapToOrder(orderDto);
        assertThat(order).isNotNull();
        assertThat(order.getOrderId()).isEqualTo(orderDto.getOrderId());
    }

    @DisplayName("mapToOrderDtoForGetAll method")
    @Test
    public void givenOrderObject_whenMapToOrderDtoForGetAll_thenReturnOrderDtoObject(){
        Order order = OrderFactory.getBaseOrder();
        OrderDtoGetAll orderDto = OrderMapper.mapToOrderDtoForGetAll(order);
        assertThat(orderDto).isNotNull();
        assertThat(orderDto.getOrderSize()).isEqualTo(2);
        assertThat(orderDto.getOrderId()).isEqualTo(order.getOrderId());
        assertThat(orderDto.getIsCanceled()).isEqualTo(order.isCanceled());
        assertThat(orderDto.getOrderNumber()).isEqualTo(order.getOrderNumber());
    }

}
