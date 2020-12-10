package org.example.dao;

import org.example.dao.entity.CreateStaffEntityFromStore;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface CreateStaffFromStoreRepository extends CrudRepository<CreateStaffEntityFromStore, Integer> {
    Collection<CreateStaffEntityFromStore> findByFirstNameAndLastName(String firstName, String lastName);
}
