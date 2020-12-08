package org.example.dao;

import org.example.dao.entity.LanguageEntity;
import org.example.exceptions.UnknownLanguageException;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LanguageRepository extends CrudRepository<LanguageEntity, Integer> {
    Optional<LanguageEntity> findByName(String name) throws UnknownLanguageException;
}
