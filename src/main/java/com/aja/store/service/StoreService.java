package com.aja.store.service;

import com.aja.store.domain.Store;
import com.aja.store.repository.StoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Store}.
 */
@Service
@Transactional
public class StoreService {

    private final Logger log = LoggerFactory.getLogger(StoreService.class);

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    /**
     * Save a store.
     *
     * @param store the entity to save.
     * @return the persisted entity.
     */
    public Store save(Store store) {
        log.debug("Request to save Store : {}", store);
        return storeRepository.save(store);
    }

    /**
     * Get all the stores.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Store> findAll() {
        log.debug("Request to get all Stores");
        return storeRepository.findAll();
    }


    /**
     * Get one store by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Store> findOne(Long id) {
        log.debug("Request to get Store : {}", id);
        return storeRepository.findById(id);
    }

    /**
     * Delete the store by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Store : {}", id);
        storeRepository.deleteById(id);
    }
}
