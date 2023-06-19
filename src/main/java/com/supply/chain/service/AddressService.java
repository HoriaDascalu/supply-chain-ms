package com.supply.chain.service;

import com.supply.chain.dto.AddressDto;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public interface AddressService {
    List<AddressDto> getAllAddresses(Collection<? extends GrantedAuthority> roles, String username);

    AddressDto saveAddress(AddressDto addressDto);

    AddressDto updateAddress(Long orderId, AddressDto addressDto);
}
