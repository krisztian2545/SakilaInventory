package org.example.dao;

import org.example.dao.entity.CountryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface CountryRepository extends CrudRepository<CountryEntity, Integer> {

    @Override
    Optional<CountryEntity> findById(Integer integer);

    Optional<CountryEntity> findByName(String name);
}