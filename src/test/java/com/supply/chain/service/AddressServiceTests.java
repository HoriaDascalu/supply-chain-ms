package com.supply.chain.service;

import com.supply.chain.dto.AddressDto;

import com.supply.chain.mapper.AddressMapper;
import com.supply.chain.mapper.AddressMapperToDto;

import com.supply.chain.model.Address;
import com.supply.chain.model.Order;
import com.supply.chain.model.Role;
import com.supply.chain.model.User;
import com.supply.chain.repository.AddressRepository;
import com.supply.chain.repository.OrderRepository;
import com.supply.chain.security.SecurityRole;
import com.supply.chain.service.impl.AddressServiceImpl;

import com.supply.chain.util.AddressFactory;
import com.supply.chain.util.OrderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTests {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AddressMapperToDto mapper;

    @InjectMocks
    private AddressServiceImpl addressService;

    private Address address;

    private AddressDto addressDto;

    private Order order;

    @BeforeEach
    public void setup(){
        order = OrderFactory.getBaseOrder();
        address = AddressFactory.getBaseAddress();
        addressDto = AddressMapper.mapToAddressDto(address);
    }

    @DisplayName("getAllAddresses method")
    @Test
    public void givenAddressList_WhenGetAllAddresses_thenReturnAddressList(){
        //given
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ROLE_ADMIN"));
        User user = new User(1,"bill","$2a$12$KOrqURcirdaPldaXPjTI.eJjxB8cVeWpW38cONK5.dzsY52r0C3h6",true, roles);
        SecurityRole grantedAuthority = new SecurityRole(new Role("ROLE_ADMIN"));
        Collection<SecurityRole> col = new ArrayList<>();
        col.add(grantedAuthority);
        Address secondAddress = new Address("Main","New York","111111","USA","Horia","Dascallu");
        given(addressRepository.findAll()).willReturn(Arrays.asList(address,secondAddress));

        //when
        List<AddressDto> addresses = addressService.getAllAddresses(col,user.getUsername());

        //then
        assertThat(addresses.size()).isEqualTo(2);
        assertThat(addresses.get(0).getStreet()).isEqualTo("Street");
    }

    @DisplayName("updateAddress method")
    @Test
    public void givenAddressObject_whenUpdateAddress_thenReturnUpdatedAddress(){
        //given
        AddressDto dtoForEdit = new AddressDto(null,"Garii",null,null,null,"Horia","Dascalu");

        given(orderRepository.findById(Mockito.any())).willReturn(Optional.of(order));
        given(addressRepository.save(Mockito.any())).willReturn(order.getDeliveryAddress());

        //when
        AddressDto editedAddress = addressService.updateAddress(order.getOrderId(),dtoForEdit);

        //then
        assertThat(editedAddress).isNotNull();
    }

    @DisplayName("saveAddress method")
    @Test
    public void givenAddressObject_whenSaveObject_thenReturnSavedObject(){
        //given
        given(addressRepository.save(Mockito.any())).willReturn(address);
        //when
        AddressDto savedAddress = addressService.saveAddress(addressDto);

        //then
        assertThat(savedAddress).isNotNull();
        assertThat(savedAddress.getStreet()).isEqualTo(addressDto.getStreet());

    }
}
