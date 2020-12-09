package org.example.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
@EqualsAndHashCode
public class Inventory {
    private int id;
    private String film;
    private String language;
    private int storeId;

    public Inventory(String film, String language, int storeId) {
        this.film = film;
        this.language = language;
        this.storeId = storeId;
    }
}
