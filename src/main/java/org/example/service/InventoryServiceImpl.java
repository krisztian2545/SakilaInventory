package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.InventoryDao;
import org.example.exceptions.*;
import org.example.model.Inventory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryDao inventoryDao;

    @Override
    public Collection<Inventory> getAllInventory() {
        return inventoryDao.readAll();
    }

    @Override
    public Collection<Inventory> getFilmInStore(int storeId) {
        return inventoryDao.readAll().stream()
                .filter(inventory -> inventory.getStoreId() == storeId)
                .collect(Collectors.toList());
    }

    @Override
    public void recordInventory(Inventory inventory) throws UnknownFilmException, UnknownStoreException, UnknownLanguageException {
        inventoryDao.createInventory(inventory);
    }

    @Override
    public void deleteInventory(Inventory inventory) throws UnknownInventoryException {
        inventoryDao.deleteInventory(inventory);
    }
}
