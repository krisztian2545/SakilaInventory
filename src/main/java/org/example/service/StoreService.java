package org.example.service;

import org.example.exceptions.UnknownFilmException;
import org.example.exceptions.UnknownLanguageException;
import org.example.exceptions.UnknownStaffException;
import org.example.exceptions.UnknownStoreException;
import org.example.model.Film;
import org.example.model.Store;

import java.util.Collection;

public interface StoreService {
    Collection<Store> getAllStore();
    void recordStore(Store store) throws UnknownStaffException;
    void deleteStore(int id) throws UnknownStoreException;
}
