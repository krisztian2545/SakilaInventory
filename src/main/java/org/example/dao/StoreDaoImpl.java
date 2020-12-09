package org.example.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.entity.StoreEntity;
import org.example.exceptions.UnknownStaffException;
import org.example.exceptions.UnknownStoreException;
import org.example.model.Store;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreDaoImpl implements StoreDao {

    private final StoreRepository storeRepository;

    @Override
    public void createStore(Store store) throws UnknownStaffException {

    }

    @Override
    public Collection<Store> readAll() {
        log.info("Reading all rows of the store table");

        return StreamSupport.stream(storeRepository.findAll().spliterator(), false)
                .map(entity -> new Store(
                        entity.getId(),
                        entity.getManagerStaff().getFirstName(),
                        entity.getManagerStaff().getLastName(),
                        entity.getAddress().getAddress(),
                        entity.getAddress().getDistrict(),
                        entity.getAddress().getCity().getName(),
                        entity.getAddress().getCity().getCountry().getName()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteStore(int id) throws UnknownStoreException {
        StoreEntity storeEntity = queryStoreById(id);
        log.info("Deleting store {}", storeEntity);
        storeRepository.delete(storeEntity);
    }

    protected StoreEntity queryStoreById(int id) throws UnknownStoreException {
        Optional<StoreEntity> storeEntity = storeRepository.findById(id);

        if (!storeEntity.isPresent())
            throw new UnknownStoreException();

        return storeEntity.get();
    }
}
