package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.dto.FilmDto;
import org.example.controller.dto.InventoryDto;
import org.example.controller.dto.InventoryRecordOrDeleteRequestDto;
import org.example.exceptions.UnknownFilmException;
import org.example.exceptions.UnknownInventoryException;
import org.example.exceptions.UnknownLanguageException;
import org.example.exceptions.UnknownStoreException;
import org.example.model.Film;
import org.example.model.Inventory;
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
    public Collection<FilmDto> listInventories() {
        return service.getAllFilm()
                .stream()
                .map(model -> FilmDto.builder()
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
        } catch (UnknownLanguageException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/film")
    private void deleteFirstMatch(@RequestBody FilmDto filmDto) {
        try {
            service.deleteFilm(new Film(
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
        } catch (UnknownFilmException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
