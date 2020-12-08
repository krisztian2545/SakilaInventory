package org.example.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.entity.FilmEntity;
import org.example.dao.entity.InventoryEntity;
import org.example.dao.entity.LanguageEntity;
import org.example.exceptions.UnknownFilmException;
import org.example.exceptions.UnknownInventoryException;
import org.example.exceptions.UnknownLanguageException;
import org.example.exceptions.UnknownStoreException;
import org.example.model.Inventory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryDaoImpl implements InventoryDao {

    private final InventoryRepository inventoryRepository;
    private final FilmRepository filmRepository;
    private final LanguageRepository languageRepository;

    @Override
    public void createInventory(Inventory inventory)  throws UnknownFilmException, UnknownStoreException, UnknownLanguageException {
        InventoryEntity inventoryEntity;

        inventoryEntity = InventoryEntity.builder()
                .film(queryFilm(inventory.getFilm(), inventory.getLanguage()))
                .storeId(inventory.getStoreId())
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();

        log.info("Creating InventoryEntity {}", inventoryEntity);

        try {
            inventoryRepository.save(inventoryEntity);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    protected FilmEntity queryFilm(String title, String language) throws UnknownFilmException, UnknownLanguageException {
        Optional<FilmEntity> filmEntity = filmRepository.findByTitle(title).stream()
                .filter(entity -> entity.getLanguage().getName().equals(language))
                .findFirst();

        if (!filmEntity.isPresent()) {
            log.info("No such film");
            Optional<LanguageEntity> languageEntity = languageRepository.findByName(language);
            if (!languageEntity.isPresent()) {
                throw new UnknownLanguageException(language);
            }

            filmEntity = Optional.ofNullable(FilmEntity.builder()
                    .title(title)
                    .language(languageEntity.get())
                    .build());
            filmRepository.save(filmEntity.get());
            log.info("Recorded new Film: {}, {}", title, language);
        }
        log.trace("Film entity: {}", filmEntity);

        return filmEntity.get();
    }

    @Override
    public Collection<Inventory> readAll() {
        log.info("Reading all rows of the inventory");

        return StreamSupport.stream(inventoryRepository.findAll().spliterator(), false)
                .map(entity -> new Inventory(
                        entity.getFilm().getTitle(),
                        entity.getFilm().getLanguage().getName(),
                        entity.getStoreId()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteInventory(Inventory inventory) throws UnknownInventoryException {
        log.info("Deleting inventory {}", inventory);
        Optional<InventoryEntity> inventoryEntity = StreamSupport.stream(inventoryRepository.findAll().spliterator(), false).filter(
                entity -> {
                    return inventory.getFilm().equals(entity.getFilm().getTitle()) &&
                            inventory.getLanguage().equals(entity.getFilm().getLanguage().getName()) &&
                            inventory.getStoreId() == entity.getStoreId();
                }
        ).findAny();
        if (!inventoryEntity.isPresent()) {
            throw new UnknownInventoryException(String.format("Inventory not found %s", inventory), inventory);
        }
        inventoryRepository.delete(inventoryEntity.get());
    }
}
