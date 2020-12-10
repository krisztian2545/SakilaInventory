package org.example.dao;

import org.example.dao.entity.StaffEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface StaffRepository extends CrudRepository<StaffEntity, Integer> {
    Collection<StaffEntity> findByFirstNameAndLastName(String firstName, String lastName);
}
