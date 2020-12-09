package org.example.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.entity.FilmEntity;
import org.example.dao.entity.InventoryEntity;
import org.example.dao.entity.LanguageEntity;
import org.example.dao.entity.StoreEntity;
import org.example.exceptions.UnknownFilmException;
import org.example.exceptions.UnknownInventoryException;
import org.example.exceptions.UnknownLanguageException;
import org.example.exceptions.UnknownStoreException;
import org.example.model.Inventory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    private final StoreRepository storeRepository;

    //////////////////// CREATE //////////////////////
    @Override
    public void createInventory(Inventory inventory)  throws UnknownFilmException, UnknownStoreException, UnknownLanguageException {
        InventoryEntity inventoryEntity;

        inventoryEntity = InventoryEntity.builder()
                .film(queryOrCreateFilm(inventory.getFilm(), inventory.getLanguage()))
                .store(queryStore(inventory.getStoreId()))
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

    protected FilmEntity queryOrCreateFilm(String title, String language) throws UnknownFilmException, UnknownLanguageException {
        Optional<FilmEntity> filmEntity = filmRepository.findByTitle(title).stream()
                .filter(entity -> entity.getLanguage().getName().equals(language))
                .findFirst();

        if (!filmEntity.isPresent()) {
            log.info("Film not found");

            filmEntity = Optional.ofNullable(FilmEntity.builder()
                    .title(title)
                    .language(queryLanguage(language))
                    .rentalDuration(3)
                    .rentalRate(new BigDecimal("4.99"))
                    .replacementCost(new BigDecimal("19.99"))
                    .lastUpdate(new Timestamp((new Date()).getTime()))
                    .build());
            filmRepository.save(filmEntity.get());
            log.info("Recorded new Film: {}, {}", title, language);
        }
        log.trace("Film entity: {}", filmEntity);

        return filmEntity.get();
    }

    protected LanguageEntity queryLanguage(String language) throws UnknownLanguageException {
        Optional<LanguageEntity> languageEntity = languageRepository.findByName(language);

        if (!languageEntity.isPresent()) {
            throw new UnknownLanguageException(language);
        }
        log.trace("Language entity: {}", languageEntity);

        return languageEntity.get();
    }

    protected FilmEntity queryFilm(String title, String language) throws UnknownFilmException {
        Optional<FilmEntity> filmEntity = filmRepository.findByTitle(title).stream()
                .filter(entity -> entity.getLanguage().getName().equals(language))
                .findFirst();

        if (!filmEntity.isPresent()) {
            throw new UnknownFilmException();
        }
        log.trace("Film entity: {}", filmEntity);

        return filmEntity.get();
    }

    protected StoreEntity queryStore(int storeId) throws UnknownStoreException {
        Optional<StoreEntity> storeEntity = storeRepository.findById(storeId);

        if (!storeEntity.isPresent()) {
            throw new UnknownStoreException();
        }
        log.trace("Store entity: {}", storeEntity);

        return storeEntity.get();
    }

    //////////////////// READ //////////////////////
    @Override
    public Collection<Inventory> readAll() {
        log.info("Reading all rows of the inventory table");

        return StreamSupport.stream(inventoryRepository.findAll().spliterator(), false)
                .map(entity -> new Inventory(
                        entity.getId(),
                        entity.getFilm().getTitle(),
                        entity.getFilm().getLanguage().getName(),
                        entity.getStore().getId()
                ))
                .collect(Collectors.toList());
    }

//    @Override
//    public void updateInventory(Inventory inventory, Inventory update) throws UnknownInventoryException, UnknownFilmException {
//        log.info("Updating inventory {}", inventory);
//        Optional<InventoryEntity> inventoryEntity = findFirstInventory(inventory);
//        inventoryEntity.get().setLastUpdate(new Timestamp((new Date()).getTime()));
//        inventoryRepository.save(inventoryEntity.get());
//    }

    //////////////////// DELETE //////////////////////
    @Override
    public void deleteInventory(Inventory inventory) throws UnknownInventoryException {
        log.info("Deleting inventory {}", inventory);
        Optional<InventoryEntity> inventoryEntity = findFirstInventory(inventory);
        inventoryRepository.delete(inventoryEntity.get());
    }

    private Optional<InventoryEntity> findFirstInventory(Inventory inventory) throws UnknownInventoryException {
        Optional<InventoryEntity> inventoryEntity = StreamSupport.stream(inventoryRepository.findAll().spliterator(), false).filter(
                entity -> {
                    return inventory.getFilm().equals(entity.getFilm().getTitle()) &&
                            inventory.getLanguage().equals(entity.getFilm().getLanguage().getName()) &&
                            inventory.getStoreId() == entity.getStore().getId();
                }
        ).findFirst();
        if (!inventoryEntity.isPresent()) {
            throw new UnknownInventoryException(String.format("Inventory not found %s", inventory), inventory);
        }
        return inventoryEntity;
    }
}
