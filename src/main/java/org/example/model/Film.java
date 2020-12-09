package org.example.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@ToString
@Getter
@EqualsAndHashCode
public class Film {
    private int id;
    private String title;
    private String description;
    private int releaseYear;
    private String language;
    private String originalLanguage;
    private int rentalDuration;
    private BigDecimal rentalRate;
    private int length;
    private BigDecimal replacementCost;
    private String rating;
    private String specialFeatures;

    public Film(String title, String description, int releaseYear, String language, String originalLanguage, int rentalDuration, BigDecimal rentalRate, int length, BigDecimal replacementCost, String rating, String specialFeatures) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.language = language;
        this.originalLanguage = originalLanguage;
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
        this.length = length;
        this.replacementCost = replacementCost;
        this.rating = rating;
        this.specialFeatures = specialFeatures;
    }
}
