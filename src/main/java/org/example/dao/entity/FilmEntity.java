package org.example.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "film", schema = "sakila")
public class FilmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private int id;

    @Column
    private String title;

    @Column
    private String description;

    @Column(name = "release_year")
    private int releaseYear;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private LanguageEntity language;

    @ManyToOne
    @JoinColumn(name = "language_id", insertable = false, updatable = false)
    private LanguageEntity originalLanguage;

    @Column(name = "rental_duration")
    private int rentalDuration;

    @Column(name = "rental_rate")
    private BigDecimal rentalRate;

    @Column
    private int length;

    @Column(name = "replacement_cost")
    private BigDecimal replacementCost;

    @Column
    private String rating;

    @Column(name = "special_features")
    private String specialFeatures;

    @Column(name = "last_update")
    private Timestamp lastUpdate;

}
