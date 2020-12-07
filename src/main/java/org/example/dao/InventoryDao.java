package org.example.dao;

import org.example.exceptions.UnknownFilmException;
import org.example.exceptions.UnknownInventoryException;
import org.example.exceptions.UnknownStoreException;
import org.example.model.Inventory;

import java.util.Collection;

public interface InventoryDao {

    void createInventory(Inventory inventory) throws UnknownFilmException, UnknownStoreException;
    Collection<Inventory> readAll();

    void deleteInventory(Inventory inventory) throws UnknownInventoryException;
}
