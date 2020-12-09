package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.FilmDao;
import org.example.exceptions.OutOfBoundsException;
import org.example.exceptions.UnknownFilmException;
import org.example.exceptions.UnknownLanguageException;
import org.example.model.Film;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmDao filmDao;

    @Override
    public Collection<Film> getAllFilm() {
        return filmDao.readAll();
    }

    @Override
    public void recordFilm(Film film) throws UnknownLanguageException, OutOfBoundsException {
        checkForValuesLessThanZero(film);
        filmDao.createFilm(film);
    }

    @Override
    public void deleteFilm(int id) throws UnknownFilmException {
        filmDao.deleteFilm(id);
    }

    private void checkForValuesLessThanZero(Film film) throws OutOfBoundsException {
        checkLessThanZero(new BigDecimal(film.getReleaseYear()), "Release year");
        checkLessThanZero(new BigDecimal(film.getRentalDuration()), "Rental duration");
        checkLessThanZero(film.getRentalRate(), "Rental rate");
        checkLessThanZero(new BigDecimal(film.getLength()), "Length");
        checkLessThanZero(film.getReplacementCost(), "Replacement cost");
    }

    private void checkLessThanZero(BigDecimal value, String valueName) throws OutOfBoundsException {
        if (value.compareTo(BigDecimal.ZERO) < 0)
            throw new OutOfBoundsException(valueName + " cannot be less than 0");
    }
}
