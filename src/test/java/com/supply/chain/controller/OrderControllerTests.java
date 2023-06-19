package com.supply.chain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supply.chain.dto.OrderDto;
import com.supply.chain.dto.OrderDtoGetAll;
import com.supply.chain.mapper.OrderMapper;
import com.supply.chain.model.Order;
import com.supply.chain.model.Role;
import com.supply.chain.model.User;
import com.supply.chain.security.SecurityRole;
import com.supply.chain.service.impl.OrderServiceImpl;
import com.supply.chain.service.impl.UserDetailsServiceImpl;
import com.supply.chain.util.OrderFactory;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;


import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebAppConfiguration
@ExtendWith(SpringExtension.class)
public class OrderControllerTests {

    private MockMvc mvc;

    @Mock
    private OrderServiceImpl orderService;

    @InjectMocks
    private OrderController orderController;

    private Order order;
    private OrderDto orderDto;
    private ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(orderController)
                .build();

        order = OrderFactory.getBaseOrder();
        orderDto = OrderMapper.mapToOrderDto(order);
        objectMapper = new ObjectMapper();
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ROLE_USER"));
        user = new User(1,"bill","$2a$12$KOrqURcirdaPldaXPjTI.eJjxB8cVeWpW38cONK5.dzsY52r0C3h6", true,roles);
    }

    @DisplayName("getAllOrders endpoint")
    @Test
    @WithMockUser(username = "bill")
    public void givenOrderList_WhenGetAllOrders_thenReturnOrderList() throws Exception {
        //given
        OrderDtoGetAll firstOrderDto = new OrderDtoGetAll(1L,2,"NEW",false,null,"Andreea","Popescu",4);
        OrderDtoGetAll secondOrderDto = new OrderDtoGetAll(2L,2,"NEW",false,null,"Horia","Dascalu",3);
        SecurityRole grantedAuthority = new SecurityRole(new Role("ROLE_USER"));
        Collection<SecurityRole> col = new ArrayList<>();
        col.add(grantedAuthority);
        given(orderService.getAllOrders(col,"bill")).willReturn(Arrays.asList(firstOrderDto,secondOrderDto));

        //when
        ResultActions response = mvc.perform(
                get("/orders"));

        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("saveOrder endpoint")
    @Test
    @WithMockUser(username = "bill")
    public void givenOrderObject_whenSaveObject_thenReturnSavedObject() throws Exception {
        //given
        given(orderService.saveOrder(orderDto,user.getUsername())).willReturn(orderDto);

        //when
        ResultActions response = mvc.perform(
                post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto))
                        .characterEncoding("utf-8"));

        //then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderNumber",
                        CoreMatchers.is(orderDto.getOrderNumber())))
                .andExpect(jsonPath("$.canceled",
                        CoreMatchers.is(orderDto.isCanceled())));
    }


    @DisplayName("deleteOrder endpoint")
    @Test
    @WithMockUser(username = "bill")
    public void givenOrderId_WhenDeleteOrder_thenStatusOK() throws Exception {
        ResultActions response = mvc.perform(
                delete("/orders/1"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("cancelOrder endpoint")
    @Test
    @WithMockUser(username = "bill")
    public void givenOrderObjectId_whenCancelOrder_thenReturnCanceledOrder() throws Exception {
        OrderDto canceledOrder = OrderMapper.mapToOrderDto(OrderFactory.getCanceledOrder());
        given(orderService.cancelOrderById(order.getOrderId(), user.getUsername())).willReturn(canceledOrder);
        ResultActions response = mvc.perform(
                patch("/orders/1"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.canceled",
                        CoreMatchers.is(true)));
    }

    @DisplayName("updateOrder endpoint")
    @Test
    @WithMockUser(username = "bill")
    public void givenOrderObject_whenUpdateOrder_thenReturnUpdatedOrder() throws Exception {
        OrderDto orderDtoForUpdate = new OrderDto(1L,3,"NEW",false,null,null,null);
        OrderDto updatedOrder = OrderMapper.mapToOrderDto(OrderFactory.getBaseOrder());
        updatedOrder.setOrderNumber(orderDtoForUpdate.getOrderNumber());
        updatedOrder.setCanceled(orderDtoForUpdate.isCanceled());
        given(orderService.updateOrder(orderDtoForUpdate,order.getOrderId(),user.getUsername())).willReturn(updatedOrder);
        ResultActions response = mvc.perform(
                put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDtoForUpdate))
                        .characterEncoding("utf-8"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.canceled",
                        CoreMatchers.is(orderDtoForUpdate.isCanceled())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderNumber",
                        CoreMatchers.is(orderDtoForUpdate.getOrderNumber())));

    }

    @DisplayName("getorderById endpoint")
    @Test
    @WithMockUser(username = "bill")
    public void givenOrderId_whenGetOrderById_thenReturnOrder() throws Exception {
        given(orderService.getOrderById(order.getOrderId(),user.getUsername())).willReturn(orderDto);

        MvcResult result = this.mvc.perform(get("/orders/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        OrderDto order = objectMapper.readValue(json, OrderDto.class);

        assertThat(order).isNotNull();
        assertThat(order.getOrderNumber()).isEqualTo(orderDto.getOrderNumber());
    }

    @DisplayName("updateOrderStatus endpoint")
    @Test
    @WithMockUser(username = "bill")
    public void givenOrderId_whenUpdateOrderStatus_thenReturnOrder() throws Exception {
        OrderDto updatedStatusOrder = OrderMapper.mapToOrderDto(OrderFactory.getBaseOrder());
        updatedStatusOrder.setStatus("ANALYSIS");
        given(orderService.updateOrderStatus(order.getOrderId(),user.getUsername(),"NEW")).willReturn(updatedStatusOrder);

        MvcResult result = this.mvc.perform(patch("/orders/1/status?statusName=analysis"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }


}
