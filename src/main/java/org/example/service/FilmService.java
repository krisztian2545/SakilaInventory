package org.example.service;

import org.example.exceptions.OutOfBoundsException;
import org.example.exceptions.UnknownFilmException;
import org.example.exceptions.UnknownLanguageException;
import org.example.model.Film;

import java.util.Collection;

public interface FilmService {
    Collection<Film> getAllFilm();

    void recordFilm(Film film) throws UnknownLanguageException, OutOfBoundsException;
    void deleteFilm(int id) throws UnknownFilmException;
}
