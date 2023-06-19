package com.supply.chain.mapper;

import com.supply.chain.dto.AddressDto;
import com.supply.chain.model.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AddressMapperTests {

    @DisplayName("mapToAddressDto method")
    @Test
    public void givenAddressObject_whenMapToAddressDto_thenReturnAddressDtoObject(){
        Address address = new Address("Street", "City", "000000", "Country","Horia","Dascalu");
        AddressDto addressDto = AddressMapper.mapToAddressDto(address);
        assertThat(addressDto).isNotNull();
        assertThat(addressDto.getAddressId()).isEqualTo(address.getAddressId());
        assertThat(addressDto.getStreet()).isEqualTo(address.getStreet());
    }

    @DisplayName("mapToAddress method")
    @Test
    public void givenAddressDtoObject_whenMapToAddress_thenReturnAddressObject(){
        AddressDto addressDto = new AddressDto(null,"Street", "City", "000000", "Country","Horia","Dascalu");
        Address address = AddressMapper.mapToAddress(addressDto);
        assertThat(address).isNotNull();
        assertThat(address.getAddressId()).isEqualTo(addressDto.getAddressId());
        assertThat(address.getStreet()).isEqualTo(addressDto.getStreet());
    }
}
