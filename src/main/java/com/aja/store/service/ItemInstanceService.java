package com.aja.store.service;

import com.aja.store.domain.ItemInstance;
import com.aja.store.repository.ItemInstanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ItemInstance}.
 */
@Service
@Transactional
public class ItemInstanceService {

    private final Logger log = LoggerFactory.getLogger(ItemInstanceService.class);

    private final ItemInstanceRepository itemInstanceRepository;

    public ItemInstanceService(ItemInstanceRepository itemInstanceRepository) {
        this.itemInstanceRepository = itemInstanceRepository;
    }

    /**
     * Save a itemInstance.
     *
     * @param itemInstance the entity to save.
     * @return the persisted entity.
     */
    public ItemInstance save(ItemInstance itemInstance) {
        log.debug("Request to save ItemInstance : {}", itemInstance);
        return itemInstanceRepository.save(itemInstance);
    }

    /**
     * Get all the itemInstances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemInstance> findAll(Pageable pageable) {
        log.debug("Request to get all ItemInstances");
        return itemInstanceRepository.findAll(pageable);
    }


    /**
     * Get one itemInstance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ItemInstance> findOne(Long id) {
        log.debug("Request to get ItemInstance : {}", id);
        return itemInstanceRepository.findById(id);
    }

    /**
     * Delete the itemInstance by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ItemInstance : {}", id);
        itemInstanceRepository.deleteById(id);
    }
}
