package com.supply.chain.controller;

import com.supply.chain.dto.OrderDto;
import com.supply.chain.dto.OrderDtoGetAll;
import com.supply.chain.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/orders")
@SecurityRequirement(name = "supply-chain-ms")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderDto> saveOrder(@Valid @RequestBody OrderDto orderDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(orderService.saveOrder(orderDto, username), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER','MANUFACTURER')")
    public ResponseEntity<List<OrderDtoGetAll>> getAllOrders(){
        Collection<? extends GrantedAuthority> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(orderService.getAllOrders(roles, username), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteOrder(@PathVariable("id") Long orderId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        orderService.deleteOrder(orderId, username);
        return new ResponseEntity<>("order with id: " + orderId + " deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderDto> updateOrder(@RequestBody OrderDto editOrderDto, @PathVariable("id") Long orderId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(orderService.updateOrder(editOrderDto,orderId,username), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderDto> cancelOrder(@PathVariable("id") Long orderId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(orderService.cancelOrderById(orderId,username), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") Long orderId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(orderService.getOrderById(orderId,username), HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('MANUFACTURER')")
    public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable("id") Long orderId, @RequestParam("statusName") String statusName){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(orderService.updateOrderStatus(orderId,username,statusName), HttpStatus.OK);
    }

}
