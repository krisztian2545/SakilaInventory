package org.example.dao;

import org.example.exceptions.UnknownFilmException;
import org.example.exceptions.UnknownLanguageException;
import org.example.model.Film;

import java.util.Collection;

public interface FilmDao {
    void createFilm(Film film) throws UnknownLanguageException;
    Collection<Film> readAll();
    void deleteFilm(Film film) throws UnknownFilmException;
}
