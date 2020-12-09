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

import java.util.Collection;
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
            service.recordFilm(new Film(
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
                    filmDto.getSpecialFeatures()
            ));
        } catch (UnknownLanguageException | OutOfBoundsException e) {
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

}
