package org.example.dao;

import org.example.dao.entity.FilmEntity;
import org.example.exceptions.UnknownFilmException;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface FilmRepository extends CrudRepository<FilmEntity, Integer> {

    Collection<FilmEntity> findByTitle(String title) throws UnknownFilmException;

}
