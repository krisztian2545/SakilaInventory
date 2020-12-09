package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.StoreDao;
import org.example.exceptions.UnknownStaffException;
import org.example.exceptions.UnknownStoreException;
import org.example.model.Store;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreDao storeDao;

    @Override
    public Collection<Store> getAllStore() {
        return storeDao.readAll();
    }

    @Override
    public void recordStore(Store store) throws UnknownStaffException {
        storeDao.createStore(store);
    }

    @Override
    public void deleteStore(int id) throws UnknownStoreException {
        storeDao.deleteStore(id);
    }
}
