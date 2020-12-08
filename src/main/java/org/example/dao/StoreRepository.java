package org.example.dao;

import org.example.dao.entity.StoreEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StoreRepository extends CrudRepository<StoreEntity, Integer> {

    @Override
    Optional<StoreEntity> findById(Integer integer);

}
