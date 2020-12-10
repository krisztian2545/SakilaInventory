package org.example.dao;

import org.example.dao.entity.AddressEntity;
import org.example.dao.entity.CityEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface AddressRepository extends CrudRepository<AddressEntity, Integer> {
    Collection<AddressEntity> findByAddressAndDistrict(String address, String district);
}