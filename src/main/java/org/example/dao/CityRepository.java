package org.example.dao;

import org.example.dao.entity.CityEntity;
import org.example.dao.entity.CountryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface CityRepository extends CrudRepository<CityEntity, Integer> {

    Collection<CityEntity> findByName(String name);
    Collection<CityEntity> findCityEntityByCountry(CountryEntity country);
}