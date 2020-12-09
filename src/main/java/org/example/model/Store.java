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
    private String address;
    private String district;
    private String city;
    private String country;

    public Store(String managerFirstName, String managerLastName, String address, String district, String city, String country) {
        this.managerFirstName = managerFirstName;
        this.managerLastName = managerLastName;
        this.address = address;
        this.district = district;
        this.city = city;
        this.country = country;
    }
}
