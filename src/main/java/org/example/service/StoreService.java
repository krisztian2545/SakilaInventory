package org.example.service;

import org.example.exceptions.*;
import org.example.model.Film;
import org.example.model.Store;

import java.util.Collection;

public interface StoreService {
    Collection<Store> getAllStore();
    void recordStore(Store store) throws UnknownStaffException, UnknownAddressException;
    void deleteStore(int id) throws UnknownStoreException;
}
