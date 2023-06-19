package com.supply.chain.service;

import com.supply.chain.dto.OrderDto;
import com.supply.chain.dto.OrderDtoGetAll;
import com.supply.chain.exception.ResourceNotFoundException;
import com.supply.chain.mapper.OrderMapper;
import com.supply.chain.mapper.OrderMapperToDto;
import com.supply.chain.model.OrderItem;
import com.supply.chain.model.Order;
import com.supply.chain.model.User;
import com.supply.chain.model.Address;
import com.supply.chain.model.Role;
import com.supply.chain.repository.OrderRepository;
import com.supply.chain.repository.UserRepository;
import com.supply.chain.security.SecurityRole;
import com.supply.chain.service.impl.OrderServiceImpl;
import com.supply.chain.util.OrderFactory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderMapperToDto mapper;
    @InjectMocks
    private OrderServiceImpl orderService;
    private Order order;
    private OrderDto orderDto;

    private User user;

    private static Stream<Arguments> generator(){
        return  Stream.of(
                Arguments.of(Optional.of(OrderFactory.getDeletedOrder()),1L),
                Arguments.of(Optional.empty(), 2L)
        );
    }

    @BeforeEach
    public void setup(){
        order = OrderFactory.getBaseOrder();
        orderDto = OrderMapper.mapToOrderDto(order);
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ROLE_USER"));
        user = new User(1,"bill","$2a$12$KOrqURcirdaPldaXPjTI.eJjxB8cVeWpW38cONK5.dzsY52r0C3h6",true, roles);
    }

    @DisplayName("getAllOrders method")
    @Test
    public void givenOrderList_WhenGetAllOrders_thenReturnOrderList(){
        //given
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem("Bluza", 10));
        orderItems.add(new OrderItem("Vesta", 5));
        Address address = new Address("Main", "City", "11111", "Romania","Horia","Dascalu");
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ROLE_USER"));
        User user = new User(1,"bill","$2a$12$KOrqURcirdaPldaXPjTI.eJjxB8cVeWpW38cONK5.dzsY52r0C3h6",true, roles);
        Order secondOrder = new Order(null,1,false,"NEW",null,false,user,orderItems,address);
        given(orderRepository.findByUsername(user.getUsername())).willReturn(Arrays.asList(order,secondOrder));

        SecurityRole grantedAuthority = new SecurityRole(new Role("ROLE_USER"));
        Collection<SecurityRole> col = new ArrayList<>();
        col.add(grantedAuthority);
        //when
        List<OrderDtoGetAll> orders = orderService.getAllOrders(col,user.getUsername());

        //then
        assertThat(orders).isNotEmpty();
        assertThat(orders).size().isEqualTo(2);

    }

    @DisplayName("saveOrder method")
    @Test
    public void givenOrderObject_whenSaveObject_thenReturnSavedObject(){
        //given
        given(orderRepository.save(Mockito.any())).willReturn(order);
        given(userRepository.findUserByUsername(user.getUsername())).willReturn(Optional.of(user));

        //when
        OrderDto savedOrder = orderService.saveOrder(orderDto,user.getUsername());

        //then
        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getOrderNumber()).isEqualTo(order.getOrderNumber());
        assertThat(savedOrder.getOrderId()).isEqualTo(order.getOrderId());

    }

    @DisplayName("deleteOrder method")
    @Test
    public void givenOrderId_WhenDeleteOrder_thenReturnNothing(){
        //given
        given(orderRepository.findById(Mockito.any())).willReturn(Optional.of(order));
        given(orderRepository.save(Mockito.any())).willReturn(order);
        //when
        orderService.deleteOrder(order.getOrderId(), user.getUsername());

        //then
        verify(orderRepository, times(1)).findById(order.getOrderId());
    }

    @DisplayName("updateOrder method")
    @Test
    public void givenOrderObject_whenUpdateOrder_thenReturnUpdatedOrder(){
        //given
        OrderDto dtoForEdit = new OrderDto(null,2,"NEW",true,null,null,null);

        given(orderRepository.findById(Mockito.any())).willReturn(Optional.of(order));
        given(orderRepository.save(Mockito.any())).willReturn(order);

        //when
        OrderDto editedOrder = orderService.updateOrder(dtoForEdit,order.getOrderId(), user.getUsername());

        //then
        assertThat(editedOrder).isNotNull();
        assertThat(editedOrder.getOrderNumber()).isEqualTo(order.getOrderNumber());
    }

    @DisplayName("cancelOrder method")
    @Test
    public void givenOrderObjectId_whenCancelOrder_thenReturnCanceledOrder(){
        //given
        given(orderRepository.findById(Mockito.any())).willReturn(Optional.of(order));
        given(orderRepository.save(Mockito.any())).willReturn(order);

        //when
        OrderDto canceledOrder = orderService.cancelOrderById(order.getOrderId(),user.getUsername());

        //then
        assertThat(canceledOrder).isNotNull();
        assertTrue(canceledOrder.isCanceled());
    }

    @DisplayName("updateOrderStatus method")
    @Test
    public void givenOrderObjectId_whenUpdateOrderStatus_thenReturnUpdatedOrder(){
        //given
        given(orderRepository.findById(Mockito.any())).willReturn(Optional.of(order));
        given(orderRepository.save(Mockito.any())).willReturn(order);
        given(userRepository.findUserByUsername(user.getUsername())).willReturn(Optional.of(user));

        //when
        OrderDto updatedOrder = orderService.updateOrderStatus(order.getOrderId(),user.getUsername(),"analysis");

        //then
        assertThat(updatedOrder).isNotNull();
        assertThat(updatedOrder.getStatus()).isEqualTo("ANALYSIS");
    }

    @DisplayName("getOrderById method invalid id")
    @ParameterizedTest
    @MethodSource("generator")
    public void givenInvalidOrderId_whenGetOrderById_thenThrowResourceNotFoundException(Optional<Order> testOrder, Long orderId){

        //given
        given(orderRepository.findById(orderId)).willReturn(testOrder);
        Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            orderService.getOrderById(orderId, user.getUsername());
        });

        //when
        String expectedMessage = "No order with id: " + orderId;
        String actualMessage = exception.getMessage();

        //then
        assertTrue(actualMessage.contains(expectedMessage));
    }

}
