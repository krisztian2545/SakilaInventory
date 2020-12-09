package org.example.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.entity.FilmEntity;
import org.example.dao.entity.LanguageEntity;
import org.example.exceptions.UnknownFilmException;
import org.example.exceptions.UnknownLanguageException;
import org.example.model.Film;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
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
        FilmEntity filmEntity = FilmEntity.builder()
                .title(film.getTitle())
                .description(film.getDescription())
                .releaseYear(film.getReleaseYear())
                .language(queryLanguage(film.getLanguage()))
                .originalLanguage(queryLanguage(film.getOriginalLanguage()))
                .rentalDuration(film.getRentalDuration())
                .rentalRate(film.getRentalRate())
                .length(film.getLength())
                .replacementCost(film.getReplacementCost())
                .rating(film.getRating())
                .specialFeatures(film.getSpecialFeatures())
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();

        log.info("Creating FilmEntity {}", filmEntity);

        try {
            filmRepository.save(filmEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    protected LanguageEntity queryLanguage(String language) throws UnknownLanguageException {
        Optional<LanguageEntity> languageEntity = languageRepository.findByName(language);

        if (!languageEntity.isPresent()) {
            throw new UnknownLanguageException(language);
        }
        log.trace("Language entity: {}", languageEntity);

        return languageEntity.get();
    }

    @Override
    public Collection<Film> readAll() {
        log.info("Reading all rows of the film table");

        return StreamSupport.stream(filmRepository.findAll().spliterator(), false)
                .map(entity -> new Film(
                        entity.getId(),
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
    public void deleteFilm(int id) throws UnknownFilmException {
        FilmEntity filmEntity = queryFilm(id);
        log.info("Deleting inventory {}", filmEntity);
        filmRepository.delete(filmEntity);
    }

    protected FilmEntity queryFilm(int id) throws UnknownFilmException {
        Optional<FilmEntity> filmEntity = filmRepository.findById(id);

        if (!filmEntity.isPresent())
            throw new UnknownFilmException();

        return filmEntity.get();
    }
}
