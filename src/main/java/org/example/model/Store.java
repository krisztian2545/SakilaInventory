package org.example.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
@EqualsAndHashCode
public class Store {
    private int id;
    private String managerFirstName;
    private String managerLastName;
    private int managerAddressId;
    private String address;
    private String district;
    private String city;
    private String country;

    public Store(String managerFirstName, String managerLastName, int managerAddressId, String address, String district, String city, String country) {
        this.managerFirstName = managerFirstName;
        this.managerLastName = managerLastName;
        this.managerAddressId = managerAddressId;
        this.address = address;
        this.district = district;
        this.city = city;
        this.country = country;
    }

    public Store(int id, String managerFirstName, String managerLastName, String address, String district, String city, String country) {
        this.id = id;
        this.managerFirstName = managerFirstName;
        this.managerLastName = managerLastName;
        this.address = address;
        this.district = district;
        this.city = city;
        this.country = country;
    }

    public Store(String managerFirstName, String managerLastName, String address, String district, String city, String country) {
        this.managerFirstName = managerFirstName;
        this.managerLastName = managerLastName;
        this.address = address;
        this.district = district;
        this.city = city;
        this.country = country;
    }
}
