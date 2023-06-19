package com.supply.chain.mapper;

import com.supply.chain.dto.AddressDto;
import com.supply.chain.model.Address;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AddressMapper {
    public static Address mapToAddress(AddressDto addressDto){
        return ((addressDto != null) ? (Address.builder()
                .addressId(addressDto.getAddressId())
                .street(addressDto.getStreet())
                .city(addressDto.getCity())
                .phoneNumber(addressDto.getPhoneNumber())
                .country(addressDto.getCountry())
                .firstName(addressDto.getFirstName())
                .lastName(addressDto.getLastName())
                .build()) : null);
    }

    public static AddressDto mapToAddressDto(Address address){
        return AddressDto.builder()
                .addressId(address.getAddressId())
                .street(address.getStreet())
                .city(address.getCity())
                .phoneNumber(address.getPhoneNumber())
                .country(address.getCountry())
                .firstName(address.getFirstName())
                .lastName(address.getLastName())
                .build();
    }
}
