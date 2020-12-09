package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.dto.GetInventoryDto;
import org.example.controller.dto.InventoryDto;
import org.example.exceptions.UnknownFilmException;
import org.example.exceptions.UnknownInventoryException;
import org.example.exceptions.UnknownLanguageException;
import org.example.exceptions.UnknownStoreException;
import org.example.model.Inventory;
import org.example.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService service;

    @GetMapping("/inventory")
    public Collection<GetInventoryDto> listInventories() {
        return service.getAllInventory()
                .stream()
                .map(model -> GetInventoryDto.builder()
                        .id(model.getId())
                        .film(model.getFilm())
                        .language(model.getLanguage())
                        .storeId(model.getStoreId())
                        .build())
                .collect(Collectors.toList());
    }

    @PostMapping("/inventory")
    public void record(@RequestBody InventoryDto inventoryDto) {
        try {
            service.recordInventory(new Inventory(
                    inventoryDto.getFilm(),
                    inventoryDto.getLanguage(),
                    inventoryDto.getStoreId()
            ));
        } catch (UnknownFilmException | UnknownStoreException | UnknownLanguageException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/inventory")
    private void deleteFirstMatch(@RequestBody InventoryDto inventoryDto) {
        try {
            service.deleteInventory(new Inventory(
                    inventoryDto.getFilm(),
                    inventoryDto.getLanguage(),
                    inventoryDto.getStoreId()
            ));
        } catch (UnknownInventoryException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
