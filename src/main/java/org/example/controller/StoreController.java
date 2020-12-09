package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.dto.GetInventoryDto;
import org.example.controller.dto.GetStoreDto;
import org.example.controller.dto.InventoryDto;
import org.example.controller.dto.StoreDto;
import org.example.exceptions.*;
import org.example.model.Inventory;
import org.example.model.Store;
import org.example.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService service;

    @GetMapping("/store")
    public Collection<GetStoreDto> listStores() {
        return service.getAllStore()
                .stream()
                .map(model -> GetStoreDto.builder()
                        .id(model.getId())
                        .managerFirstName(model.getManagerFirstName())
                        .managerLastName(model.getManagerLastName())
                        .address(model.getAddress())
                        .district(model.getDistrict())
                        .city(model.getCity())
                        .country(model.getCountry())
                        .build())
                .collect(Collectors.toList());
    }

    @PostMapping("/store")
    public void record(@RequestBody StoreDto storeDto) {
        try {
            service.recordStore(new Store(
                    storeDto.getManagerFirstName(),
                    storeDto.getManagerLastName(),
                    storeDto.getAddress(),
                    storeDto.getDistrict(),
                    storeDto.getCity(),
                    storeDto.getCountry()
            ));
        } catch (UnknownStaffException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/store")
    private void deleteById(@RequestBody int id) {
        try {
            service.deleteStore(id);
        } catch (UnknownStoreException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
