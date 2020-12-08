package org.example.dao;

import org.example.exceptions.UnknownFilmException;
import org.example.exceptions.UnknownInventoryException;
import org.example.exceptions.UnknownLanguageException;
import org.example.exceptions.UnknownStoreException;
import org.example.model.Inventory;

import java.util.Collection;

public interface InventoryDao {

    void createInventory(Inventory inventory) throws UnknownFilmException, UnknownStoreException, UnknownLanguageException;
    Collection<Inventory> readAll();
//    void updateInventory(Inventory inventory, Inventory update) throws UnknownInventoryException, UnknownFilmException;
    void deleteInventory(Inventory inventory) throws UnknownInventoryException;
}
