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
}
