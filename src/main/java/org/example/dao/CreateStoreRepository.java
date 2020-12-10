package org.example.dao;

import org.example.dao.entity.CreateStoreEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CreateStoreRepository extends CrudRepository<CreateStoreEntity, Integer> {

    @Override
    Optional<CreateStoreEntity> findById(Integer integer);

}
