package org.example.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.entity.*;
import org.example.exceptions.UnknownAddressException;
import org.example.exceptions.UnknownCountryException;
import org.example.exceptions.UnknownStaffException;
import org.example.exceptions.UnknownStoreException;
import org.example.model.Store;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreDaoImpl implements StoreDao {

    private final StoreRepository storeRepository;
    private final CreateStoreRepository createStoreRepository;
    private final AddressRepository addressRepository;
    private final StaffRepository staffRepository;
    private final CreateStaffFromStoreRepository createStaffFromStoreRepository;

    @Override
    public void createStore(Store store) throws UnknownAddressException {
        CreateStoreEntity storeEntity = CreateStoreEntity.builder()
                .address(queryAddress(store.getAddress(), store.getDistrict(), store.getCity(), store.getCountry()))
                .managerStaffId(queryStaff(store.getManagerFirstName(), store.getManagerLastName(), store.getManagerAddressId()).getId())
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();

        log.info("Creating StoreEntity {}", storeEntity);

        try {
            createStoreRepository.save(storeEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    protected AddressEntity queryAddress(String address, String district, String city, String country) throws UnknownAddressException {
        Optional<AddressEntity> addressEntity = addressRepository.findByAddressAndDistrict(address, district).stream()
                .filter(entity -> entity.getCity().getName().equals(city) && entity.getCity().getCountry().getName().equals(country))
                .findFirst();

        if (!addressEntity.isPresent())
            throw new UnknownAddressException();

        return addressEntity.get();
    }

    protected AddressEntity queryAddressById(int id) throws UnknownAddressException {
        Optional<AddressEntity> addressEntity = addressRepository.findById(id);

        if (!addressEntity.isPresent())
            throw new UnknownAddressException();

        return addressEntity.get();
    }

    protected CreateStaffEntityFromStore queryStaff(String firstName, String lastName, int managerAddressId) throws UnknownAddressException {
        Optional<CreateStaffEntityFromStore> staffEntity = createStaffFromStoreRepository.findByFirstNameAndLastName(firstName, lastName).stream()
                .filter(entity -> entity.getAddress().getId() == managerAddressId)
                .findFirst();

        if (!staffEntity.isPresent()) {
            staffEntity = Optional.ofNullable(CreateStaffEntityFromStore.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .address(queryAddressById(managerAddressId))
                    .storeId(getNewStoreId())
                    .active(1)
                    .username(firstName)
                    .lastUpdate(new Timestamp((new Date()).getTime()))
                    .build());

            log.info("Recorded new Staff: {}", staffEntity);
            createStaffFromStoreRepository.save(staffEntity.get());
        }

        return staffEntity.get();
    }

    private int getNewStoreId() {
        int biggestId = -1;
        List<StoreEntity> entities = StreamSupport.stream(storeRepository.findAll().spliterator(), false).collect(Collectors.toList());
        for (StoreEntity e : entities) {
            if (e.getId() > biggestId)
                biggestId = e.getId();
        }
        return biggestId+1;
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
