package org.example.service;

import org.example.exceptions.UnknownFilmException;
import org.example.exceptions.UnknownInventoryException;
import org.example.exceptions.UnknownStoreException;
import org.example.model.Inventory;

import java.util.Collection;

public interface InventoryService {

    Collection<Inventory> getAllInventory();
    Collection<Inventory> getFilmInStore(int storeId);

    void recordInventory(Inventory inventory) throws UnknownFilmException, UnknownStoreException;
    void deleteInventory(Inventory inventory) throws UnknownInventoryException;

}
