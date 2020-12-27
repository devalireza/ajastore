package com.aja.store.repository;

import com.aja.store.domain.ItemInstance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ItemInstance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemInstanceRepository extends JpaRepository<ItemInstance, Long>, JpaSpecificationExecutor<ItemInstance> {
}
