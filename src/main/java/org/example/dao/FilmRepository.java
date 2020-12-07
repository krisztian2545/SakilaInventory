package org.example.dao;

import org.example.dao.entity.FilmEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface FilmRepository extends CrudRepository<FilmEntity, Integer> {

    Optional<FilmEntity> findByName(String name);

}
