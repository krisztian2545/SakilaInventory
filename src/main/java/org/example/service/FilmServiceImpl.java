package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.FilmDao;
import org.example.exceptions.UnknownFilmException;
import org.example.exceptions.UnknownLanguageException;
import org.example.model.Film;
import org.springframework.stereotype.Service;

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
    public void recordFilm(Film film) throws UnknownLanguageException {
        filmDao.createFilm(film);
    }

    @Override
    public void deleteFilm(Film film) throws UnknownFilmException {
        filmDao.deleteFilm(film);
    }
}
