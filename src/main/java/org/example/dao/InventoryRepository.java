package org.example.dao;

import org.example.dao.entity.InventoryEntity;
import org.springframework.data.repository.CrudRepository;

public interface InventoryRepository extends CrudRepository<InventoryEntity, Integer> {
}
