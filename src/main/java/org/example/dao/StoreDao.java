package org.example.dao;

import org.example.exceptions.UnknownAddressException;
import org.example.exceptions.UnknownCountryException;
import org.example.exceptions.UnknownStaffException;
import org.example.exceptions.UnknownStoreException;
import org.example.model.Address;
import org.example.model.Store;

import java.util.Collection;

public interface StoreDao {

    void createStore(Store store) throws UnknownStaffException;
    Collection<Store> readAll();
    void deleteStore(int id) throws UnknownStoreException;
}
