package com.aja.store.service;

import com.aja.store.domain.ItemCategory;
import com.aja.store.repository.ItemCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ItemCategory}.
 */
@Service
@Transactional
public class ItemCategoryService {

    private final Logger log = LoggerFactory.getLogger(ItemCategoryService.class);

    private final ItemCategoryRepository itemCategoryRepository;

    public ItemCategoryService(ItemCategoryRepository itemCategoryRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
    }

    /**
     * Save a itemCategory.
     *
     * @param itemCategory the entity to save.
     * @return the persisted entity.
     */
    public ItemCategory save(ItemCategory itemCategory) {
        log.debug("Request to save ItemCategory : {}", itemCategory);
        return itemCategoryRepository.save(itemCategory);
    }

    /**
     * Get all the itemCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemCategory> findAll(Pageable pageable) {
        log.debug("Request to get all ItemCategories");
        return itemCategoryRepository.findAll(pageable);
    }


    /**
     * Get one itemCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ItemCategory> findOne(Long id) {
        log.debug("Request to get ItemCategory : {}", id);
        return itemCategoryRepository.findById(id);
    }

    /**
     * Delete the itemCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ItemCategory : {}", id);
        itemCategoryRepository.deleteById(id);
    }
}
