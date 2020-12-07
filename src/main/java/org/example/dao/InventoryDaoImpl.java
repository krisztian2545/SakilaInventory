package org.example.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.entity.FilmEntity;
import org.example.dao.entity.InventoryEntity;
import org.example.exceptions.UnknownFilmException;
import org.example.exceptions.UnknownInventoryException;
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

    @Override
    public void createInventory(Inventory inventory)  throws UnknownFilmException, UnknownStoreException {
        InventoryEntity inventoryEntity;

        inventoryEntity = InventoryEntity.builder()
                .film(queryFilm(inventory.getFilm()))
                .storeId(inventory.getStoreId())
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();

        log.info("InventoryEntity {}", inventoryEntity);

        try {
            inventoryRepository.save(inventoryEntity);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    protected FilmEntity queryFilm(String title) throws UnknownFilmException {
        Optional<FilmEntity> filmEntity = filmRepository.findByName(title);

        if (!filmEntity.isPresent()) {
            throw new UnknownFilmException(title);
        }
        log.trace("Film entity: {}", filmEntity);

        return filmEntity.get();
    }

    @Override
    public Collection<Inventory> readAll() {
        return StreamSupport.stream(inventoryRepository.findAll().spliterator(), false)
                .map(entity -> new Inventory(
                        entity.getFilm().getTitle(),
                        entity.getStoreId()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteInventory(Inventory inventory) throws UnknownInventoryException {

    }
}
