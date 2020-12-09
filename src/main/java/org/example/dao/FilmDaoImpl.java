package org.example.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.UnknownFilmException;
import org.example.exceptions.UnknownLanguageException;
import org.example.model.Film;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmDaoImpl implements FilmDao {

    private final FilmRepository filmRepository;
    private final LanguageRepository languageRepository;

    @Override
    public void createFilm(Film film) throws UnknownLanguageException {

    }

    @Override
    public Collection<Film> readAll() {
        log.info("Reading all rows of the film table");

        return StreamSupport.stream(filmRepository.findAll().spliterator(), false)
                .map(entity -> new Film(
                        entity.getTitle(),
                        entity.getDescription(),
                        entity.getReleaseYear(),
                        entity.getLanguage().getName(),
                        entity.getOriginalLanguage().getName(),
                        entity.getRentalDuration(),
                        entity.getRentalRate(),
                        entity.getLength(),
                        entity.getReplacementCost(),
                        entity.getRating(),
                        entity.getSpecialFeatures()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFilm(Film film) throws UnknownFilmException {

    }
}
