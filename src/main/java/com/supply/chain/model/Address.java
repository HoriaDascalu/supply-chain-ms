package com.supply.chain.model;

import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    private String street;
    private String city;
    private String phoneNumber;
    private String country;

    private String firstName;

    private String lastName;

    public Address(String street, String city, String phoneNumber, String country, String firstName, String lastName) {
        this.street = street;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
