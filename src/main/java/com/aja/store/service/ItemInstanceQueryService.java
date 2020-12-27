package com.aja.store.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.aja.store.domain.ItemInstance;
import com.aja.store.domain.*; // for static metamodels
import com.aja.store.repository.ItemInstanceRepository;
import com.aja.store.service.dto.ItemInstanceCriteria;

/**
 * Service for executing complex queries for {@link ItemInstance} entities in the database.
 * The main input is a {@link ItemInstanceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ItemInstance} or a {@link Page} of {@link ItemInstance} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ItemInstanceQueryService extends QueryService<ItemInstance> {

    private final Logger log = LoggerFactory.getLogger(ItemInstanceQueryService.class);

    private final ItemInstanceRepository itemInstanceRepository;

    public ItemInstanceQueryService(ItemInstanceRepository itemInstanceRepository) {
        this.itemInstanceRepository = itemInstanceRepository;
    }

    /**
     * Return a {@link List} of {@link ItemInstance} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ItemInstance> findByCriteria(ItemInstanceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ItemInstance> specification = createSpecification(criteria);
        return itemInstanceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ItemInstance} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemInstance> findByCriteria(ItemInstanceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ItemInstance> specification = createSpecification(criteria);
        return itemInstanceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ItemInstanceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ItemInstance> specification = createSpecification(criteria);
        return itemInstanceRepository.count(specification);
    }

    /**
     * Function to convert {@link ItemInstanceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ItemInstance> createSpecification(ItemInstanceCriteria criteria) {
        Specification<ItemInstance> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ItemInstance_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ItemInstance_.code));
            }
            if (criteria.getItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemId(),
                    root -> root.join(ItemInstance_.item, JoinType.LEFT).get(Item_.id)));
            }
            if (criteria.getStoreId() != null) {
                specification = specification.and(buildSpecification(criteria.getStoreId(),
                    root -> root.join(ItemInstance_.store, JoinType.LEFT).get(Store_.id)));
            }
        }
        return specification;
    }
}
