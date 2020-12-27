package com.aja.store.service;

import com.aja.store.domain.ItemInstanceTransact;
import com.aja.store.repository.ItemInstanceTransactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ItemInstanceTransact}.
 */
@Service
@Transactional
public class ItemInstanceTransactService {

    private final Logger log = LoggerFactory.getLogger(ItemInstanceTransactService.class);

    private final ItemInstanceTransactRepository itemInstanceTransactRepository;

    public ItemInstanceTransactService(ItemInstanceTransactRepository itemInstanceTransactRepository) {
        this.itemInstanceTransactRepository = itemInstanceTransactRepository;
    }

    /**
     * Save a itemInstanceTransact.
     *
     * @param itemInstanceTransact the entity to save.
     * @return the persisted entity.
     */
    public ItemInstanceTransact save(ItemInstanceTransact itemInstanceTransact) {
        log.debug("Request to save ItemInstanceTransact : {}", itemInstanceTransact);
        return itemInstanceTransactRepository.save(itemInstanceTransact);
    }

    /**
     * Get all the itemInstanceTransacts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemInstanceTransact> findAll(Pageable pageable) {
        log.debug("Request to get all ItemInstanceTransacts");
        return itemInstanceTransactRepository.findAll(pageable);
    }


    /**
     * Get one itemInstanceTransact by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ItemInstanceTransact> findOne(Long id) {
        log.debug("Request to get ItemInstanceTransact : {}", id);
        return itemInstanceTransactRepository.findById(id);
    }

    /**
     * Delete the itemInstanceTransact by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ItemInstanceTransact : {}", id);
        itemInstanceTransactRepository.deleteById(id);
    }
}
