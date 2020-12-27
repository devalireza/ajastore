package com.aja.store.web.rest;

import com.aja.store.domain.ItemInstance;
import com.aja.store.service.ItemInstanceService;
import com.aja.store.web.rest.errors.BadRequestAlertException;
import com.aja.store.service.dto.ItemInstanceCriteria;
import com.aja.store.service.ItemInstanceQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.aja.store.domain.ItemInstance}.
 */
@RestController
@RequestMapping("/api")
public class ItemInstanceResource {

    private final Logger log = LoggerFactory.getLogger(ItemInstanceResource.class);

    private static final String ENTITY_NAME = "itemInstance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemInstanceService itemInstanceService;

    private final ItemInstanceQueryService itemInstanceQueryService;

    public ItemInstanceResource(ItemInstanceService itemInstanceService, ItemInstanceQueryService itemInstanceQueryService) {
        this.itemInstanceService = itemInstanceService;
        this.itemInstanceQueryService = itemInstanceQueryService;
    }

    /**
     * {@code POST  /item-instances} : Create a new itemInstance.
     *
     * @param itemInstance the itemInstance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemInstance, or with status {@code 400 (Bad Request)} if the itemInstance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-instances")
    public ResponseEntity<ItemInstance> createItemInstance(@Valid @RequestBody ItemInstance itemInstance) throws URISyntaxException {
        log.debug("REST request to save ItemInstance : {}", itemInstance);
        if (itemInstance.getId() != null) {
            throw new BadRequestAlertException("A new itemInstance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemInstance result = itemInstanceService.save(itemInstance);
        return ResponseEntity.created(new URI("/api/item-instances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-instances} : Updates an existing itemInstance.
     *
     * @param itemInstance the itemInstance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemInstance,
     * or with status {@code 400 (Bad Request)} if the itemInstance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemInstance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-instances")
    public ResponseEntity<ItemInstance> updateItemInstance(@Valid @RequestBody ItemInstance itemInstance) throws URISyntaxException {
        log.debug("REST request to update ItemInstance : {}", itemInstance);
        if (itemInstance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemInstance result = itemInstanceService.save(itemInstance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemInstance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /item-instances} : get all the itemInstances.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemInstances in body.
     */
    @GetMapping("/item-instances")
    public ResponseEntity<List<ItemInstance>> getAllItemInstances(ItemInstanceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ItemInstances by criteria: {}", criteria);
        Page<ItemInstance> page = itemInstanceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /item-instances/count} : count all the itemInstances.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/item-instances/count")
    public ResponseEntity<Long> countItemInstances(ItemInstanceCriteria criteria) {
        log.debug("REST request to count ItemInstances by criteria: {}", criteria);
        return ResponseEntity.ok().body(itemInstanceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /item-instances/:id} : get the "id" itemInstance.
     *
     * @param id the id of the itemInstance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemInstance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-instances/{id}")
    public ResponseEntity<ItemInstance> getItemInstance(@PathVariable Long id) {
        log.debug("REST request to get ItemInstance : {}", id);
        Optional<ItemInstance> itemInstance = itemInstanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemInstance);
    }

    /**
     * {@code DELETE  /item-instances/:id} : delete the "id" itemInstance.
     *
     * @param id the id of the itemInstance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-instances/{id}")
    public ResponseEntity<Void> deleteItemInstance(@PathVariable Long id) {
        log.debug("REST request to delete ItemInstance : {}", id);
        itemInstanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
