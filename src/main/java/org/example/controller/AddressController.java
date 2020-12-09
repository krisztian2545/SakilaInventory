package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.dto.AddressDto;
import org.example.controller.dto.AddressRecordRequestDto;
import org.example.controller.dto.InventoryDto;
import org.example.exceptions.UnknownAddressException;
import org.example.exceptions.UnknownCountryException;
import org.example.exceptions.UnknownInventoryException;
import org.example.model.Address;
import org.example.model.Inventory;
import org.example.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AddressController {

    private final AddressService service;

    @GetMapping("/address")
    public Collection<AddressDto> listAddresses(){
        return service.getAllAddress()
                .stream()
                .map(model -> AddressDto.builder()
                        .address(model.getAddress())
                        .address2(model.getAddress2())
                        .district(model.getDistrict())
                        .city(model.getCity())
                        .country(model.getCountry())
                        .build())
                .collect(Collectors.toList());
    }


    @PostMapping("/address")
    public void record(@RequestBody AddressRecordRequestDto requestDto) {
        try {
            service.recordAddress(new Address(
                    requestDto.getAddress(),
                    requestDto.getAddress2(),
                    requestDto.getDistrict(),
                    requestDto.getCity(),
                    requestDto.getCountry(),
                    requestDto.getPostalCode(),
                    requestDto.getPhone()
            ));
        } catch (UnknownCountryException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/address")
    private void deleteFirstMatch(@RequestBody AddressDto addressDto) {
        try {
            service.deleteAddress(new Address(
                    addressDto.getAddress(),
                    addressDto.getAddress2(),
                    addressDto.getDistrict(),
                    addressDto.getCity(),
                    addressDto.getCountry()
            ));
        } catch (UnknownAddressException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}