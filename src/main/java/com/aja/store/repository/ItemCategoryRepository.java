package com.aja.store.repository;

import com.aja.store.domain.ItemCategory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ItemCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long>, JpaSpecificationExecutor<ItemCategory> {
}
