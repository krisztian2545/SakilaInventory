package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.InventoryDao;
import org.example.exceptions.UnknownFilmException;
import org.example.exceptions.UnknownInventoryException;
import org.example.exceptions.UnknownStoreException;
import org.example.model.Inventory;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
        return null;
    }

    @Override
    public void recordInventory(Inventory inventory) throws UnknownFilmException, UnknownStoreException {

    }

    @Override
    public void deleteInventory(Inventory inventory) throws UnknownInventoryException {

    }
}
