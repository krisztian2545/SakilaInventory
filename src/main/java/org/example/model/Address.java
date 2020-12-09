package org.example.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
@EqualsAndHashCode
public class Address {

    private String address;
    private String address2;
    private String district;
    private String city;
    private String country;
    private String postalCode;
    private String phone;

    public Address(String address, String address2, String district, String city, String country) {
        this.address = address;
        this.address2 = address2;
        this.district = district;
        this.city = city;
        this.country = country;
    }
}