package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.dto.FilmDto;
import org.example.controller.dto.GetFilmDto;
import org.example.exceptions.*;
import org.example.model.Film;
import org.example.service.FilmService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FilmController {

    private final FilmService service;

    @GetMapping("/film")
    public Collection<GetFilmDto> listInventories() {
        return service.getAllFilm()
                .stream()
                .map(model -> GetFilmDto.builder()
                        .id(model.getId())
                        .title(model.getTitle())
                        .description(model.getDescription())
                        .releaseYear(model.getReleaseYear())
                        .language(model.getLanguage())
                        .originalLanguage(model.getOriginalLanguage())
                        .rentalDuration(model.getRentalDuration())
                        .rentalRate(model.getRentalRate())
                        .length(model.getLength())
                        .replacementCost(model.getReplacementCost())
                        .rating(model.getRating())
                        .specialFeatures(model.getSpecialFeatures())
                        .build())
                .collect(Collectors.toList());
    }

    @PostMapping("/film")
    public void record(@RequestBody FilmDto filmDto) {
        try {
            Film film = new Film(
                    filmDto.getTitle(),
                    filmDto.getDescription(),
                    filmDto.getReleaseYear(),
                    filmDto.getLanguage(),
                    filmDto.getOriginalLanguage(),
                    filmDto.getRentalDuration(),
                    filmDto.getRentalRate(),
                    filmDto.getLength(),
                    filmDto.getReplacementCost(),
                    filmDto.getRating(),
                    checkInvalidSpecialFeatures(filmDto.getSpecialFeatures()));

            checkForValuesLessThanZero(film);
            checkInvalidRating(film.getRating());

            service.recordFilm(film);
        } catch (UnknownLanguageException | OutOfBoundsException | InvalidValueException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/film")
    private void deleteById(@RequestBody int id) {
        try {
            service.deleteFilm(id);
        } catch (UnknownFilmException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
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

//    private void checkInvalidValues(Film film) throws InvalidValueException{
//        checkInvalidRating(film.getRating());
//    }

    private void checkInvalidRating(String rating) throws InvalidValueException {
        String[] correctRatings = {"G", "PG", "PG-13", "R", "NC-17"};
        if (!Arrays.asList(correctRatings).contains(rating))
            throw new InvalidValueException("Rating has invalid value. Correct values: " + Arrays.toString(correctRatings));
    }

    private String checkInvalidSpecialFeatures(String features) throws InvalidValueException {
        if (features.equals(""))
            return "";

        String[] correctFeatures = {"Trailers", "Commentaries", "Deleted Scenes", "Behind the Scenes"};
        String[] splitted = features.split(",");
        Set<String> noDuplicates = new HashSet<>();

        for (int i = 0; i < splitted.length; i++) {
            if (Arrays.asList(correctFeatures).contains(splitted[i]))
                noDuplicates.add(splitted[i]);
            else
                throw new InvalidValueException("Special features has invalid value. Correct values: " + Arrays.toString(correctFeatures));
        }

        return noDuplicates.toString().substring(1, noDuplicates.toString().length()-1).replace(", ", ",");
    }

}
