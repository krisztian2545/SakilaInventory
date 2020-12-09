package org.example.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {
    private String managerFirstName;
    private String managerLastName;
    private String address;
    private String district;
    private String city;
    private String country;
}
