package com.supply.chain.service.impl;

import com.supply.chain.dto.AddressDto;
import com.supply.chain.exception.ResourceNotFoundException;
import com.supply.chain.mapper.AddressMapper;
import com.supply.chain.mapper.AddressMapperToDto;
import com.supply.chain.model.Address;
import com.supply.chain.model.Order;
import com.supply.chain.repository.AddressRepository;
import com.supply.chain.repository.OrderRepository;
import com.supply.chain.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final OrderRepository orderRepository;

    private final AddressMapperToDto mapper;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, OrderRepository orderRepository, AddressMapperToDto mapper) {
        this.addressRepository = addressRepository;
        this.orderRepository = orderRepository;
        this.mapper = mapper;
    }

    @Override
    public List<AddressDto> getAllAddresses(Collection<? extends GrantedAuthority> roles, String username) {
        List<String> roleNames = new ArrayList<>();
        for(GrantedAuthority role: roles){
            roleNames.add(role.getAuthority());
        }
        if(roleNames.contains("ROLE_ADMIN")){
            return addressRepository.findAll()
                    .stream()
                    .map(AddressMapper::mapToAddressDto)
                    .collect(Collectors.toList());
        }

        return orderRepository.findAllAddressesByUsername(username)
                .stream()
                .map(AddressMapper::mapToAddressDto)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDto saveAddress(AddressDto addressDto) {
        Address savedAddress = addressRepository.save(AddressMapper.mapToAddress(addressDto));
        return AddressMapper.mapToAddressDto(savedAddress);
    }

    @Override
    public AddressDto updateAddress(Long orderId, AddressDto addressDto) {
        Order order = getOrderById(orderId);

        Address address = order.getDeliveryAddress();
        mapper.updateAddressFromDto(addressDto,address);
        Address updatedAddress =  addressRepository.save(address);
        return AddressMapper.mapToAddressDto(updatedAddress);
    }

    private Order getOrderById(Long id){
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No order with id: " + id, "order-not-found"));

        if(order.isDeleted()){
            throw new ResourceNotFoundException("No order with id: " + id, "order-not-found");
        }

        return order;
    }
}
