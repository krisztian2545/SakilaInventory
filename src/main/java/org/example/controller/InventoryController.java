package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.dto.InventoryDto;
import org.example.service.InventoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService service;

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name", defaultValue = "World", required = false) String name){
        return String.format("Hello %s!", name);
    }

    @GetMapping("/inventory")
    public Collection<InventoryDto> listInventories() {
        return service.getAllInventory()
                .stream()
                .map(model -> InventoryDto.builder()
                        .film(model.getFilm())
                        .storeId(model.getStoreId())
                        .build())
                .collect(Collectors.toList());
    }

}
