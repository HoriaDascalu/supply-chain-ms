package com.supply.chain.util;

import com.supply.chain.model.Address;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AddressFactory {
    public static Address getBaseAddress(){

        return new Address("Street", "City", "0000000000", "Country","Horia","Dascalu");
    }
}
