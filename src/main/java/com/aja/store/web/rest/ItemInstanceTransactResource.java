package com.aja.store.web.rest;

import com.aja.store.domain.ItemInstanceTransact;
import com.aja.store.service.ItemInstanceTransactService;
import com.aja.store.web.rest.errors.BadRequestAlertException;
import com.aja.store.service.dto.ItemInstanceTransactCriteria;
import com.aja.store.service.ItemInstanceTransactQueryService;

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
 * REST controller for managing {@link com.aja.store.domain.ItemInstanceTransact}.
 */
@RestController
@RequestMapping("/api")
public class ItemInstanceTransactResource {

    private final Logger log = LoggerFactory.getLogger(ItemInstanceTransactResource.class);

    private static final String ENTITY_NAME = "itemInstanceTransact";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemInstanceTransactService itemInstanceTransactService;

    private final ItemInstanceTransactQueryService itemInstanceTransactQueryService;

    public ItemInstanceTransactResource(ItemInstanceTransactService itemInstanceTransactService, ItemInstanceTransactQueryService itemInstanceTransactQueryService) {
        this.itemInstanceTransactService = itemInstanceTransactService;
        this.itemInstanceTransactQueryService = itemInstanceTransactQueryService;
    }

    /**
     * {@code POST  /item-instance-transacts} : Create a new itemInstanceTransact.
     *
     * @param itemInstanceTransact the itemInstanceTransact to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemInstanceTransact, or with status {@code 400 (Bad Request)} if the itemInstanceTransact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-instance-transacts")
    public ResponseEntity<ItemInstanceTransact> createItemInstanceTransact(@Valid @RequestBody ItemInstanceTransact itemInstanceTransact) throws URISyntaxException {
        log.debug("REST request to save ItemInstanceTransact : {}", itemInstanceTransact);
        if (itemInstanceTransact.getId() != null) {
            throw new BadRequestAlertException("A new itemInstanceTransact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemInstanceTransact result = itemInstanceTransactService.save(itemInstanceTransact);
        return ResponseEntity.created(new URI("/api/item-instance-transacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-instance-transacts} : Updates an existing itemInstanceTransact.
     *
     * @param itemInstanceTransact the itemInstanceTransact to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemInstanceTransact,
     * or with status {@code 400 (Bad Request)} if the itemInstanceTransact is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemInstanceTransact couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-instance-transacts")
    public ResponseEntity<ItemInstanceTransact> updateItemInstanceTransact(@Valid @RequestBody ItemInstanceTransact itemInstanceTransact) throws URISyntaxException {
        log.debug("REST request to update ItemInstanceTransact : {}", itemInstanceTransact);
        if (itemInstanceTransact.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemInstanceTransact result = itemInstanceTransactService.save(itemInstanceTransact);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemInstanceTransact.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /item-instance-transacts} : get all the itemInstanceTransacts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemInstanceTransacts in body.
     */
    @GetMapping("/item-instance-transacts")
    public ResponseEntity<List<ItemInstanceTransact>> getAllItemInstanceTransacts(ItemInstanceTransactCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ItemInstanceTransacts by criteria: {}", criteria);
        Page<ItemInstanceTransact> page = itemInstanceTransactQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /item-instance-transacts/count} : count all the itemInstanceTransacts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/item-instance-transacts/count")
    public ResponseEntity<Long> countItemInstanceTransacts(ItemInstanceTransactCriteria criteria) {
        log.debug("REST request to count ItemInstanceTransacts by criteria: {}", criteria);
        return ResponseEntity.ok().body(itemInstanceTransactQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /item-instance-transacts/:id} : get the "id" itemInstanceTransact.
     *
     * @param id the id of the itemInstanceTransact to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemInstanceTransact, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-instance-transacts/{id}")
    public ResponseEntity<ItemInstanceTransact> getItemInstanceTransact(@PathVariable Long id) {
        log.debug("REST request to get ItemInstanceTransact : {}", id);
        Optional<ItemInstanceTransact> itemInstanceTransact = itemInstanceTransactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemInstanceTransact);
    }

    /**
     * {@code DELETE  /item-instance-transacts/:id} : delete the "id" itemInstanceTransact.
     *
     * @param id the id of the itemInstanceTransact to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-instance-transacts/{id}")
    public ResponseEntity<Void> deleteItemInstanceTransact(@PathVariable Long id) {
        log.debug("REST request to delete ItemInstanceTransact : {}", id);
        itemInstanceTransactService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
