package com.supply.chain.dto;

import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class AddressDto {
    private Long addressId;

    @NotEmpty(message = "street value must not be null")
    private String street;

    @NotEmpty(message = "city value must not be null")
    private String city;

    @NotEmpty(message = "phone number must not be null")
    @Size(min = 10, max = 12, message = "phone number must contain min 10 and max 12 digits")
    private String phoneNumber;

    @NotEmpty(message = "country value must not be null")
    private String country;

    private String firstName;

    private String lastName;
}
