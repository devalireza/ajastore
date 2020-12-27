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

import com.aja.store.domain.ItemInstanceTransact;
import com.aja.store.domain.*; // for static metamodels
import com.aja.store.repository.ItemInstanceTransactRepository;
import com.aja.store.service.dto.ItemInstanceTransactCriteria;

/**
 * Service for executing complex queries for {@link ItemInstanceTransact} entities in the database.
 * The main input is a {@link ItemInstanceTransactCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ItemInstanceTransact} or a {@link Page} of {@link ItemInstanceTransact} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ItemInstanceTransactQueryService extends QueryService<ItemInstanceTransact> {

    private final Logger log = LoggerFactory.getLogger(ItemInstanceTransactQueryService.class);

    private final ItemInstanceTransactRepository itemInstanceTransactRepository;

    public ItemInstanceTransactQueryService(ItemInstanceTransactRepository itemInstanceTransactRepository) {
        this.itemInstanceTransactRepository = itemInstanceTransactRepository;
    }

    /**
     * Return a {@link List} of {@link ItemInstanceTransact} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ItemInstanceTransact> findByCriteria(ItemInstanceTransactCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ItemInstanceTransact> specification = createSpecification(criteria);
        return itemInstanceTransactRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ItemInstanceTransact} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemInstanceTransact> findByCriteria(ItemInstanceTransactCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ItemInstanceTransact> specification = createSpecification(criteria);
        return itemInstanceTransactRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ItemInstanceTransactCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ItemInstanceTransact> specification = createSpecification(criteria);
        return itemInstanceTransactRepository.count(specification);
    }

    /**
     * Function to convert {@link ItemInstanceTransactCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ItemInstanceTransact> createSpecification(ItemInstanceTransactCriteria criteria) {
        Specification<ItemInstanceTransact> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ItemInstanceTransact_.id));
            }
            if (criteria.getDeliveryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeliveryDate(), ItemInstanceTransact_.deliveryDate));
            }
            if (criteria.getTransactionType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTransactionType(), ItemInstanceTransact_.transactionType));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(ItemInstanceTransact_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getItemInstanceIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemInstanceIdId(),
                    root -> root.join(ItemInstanceTransact_.itemInstanceId, JoinType.LEFT).get(ItemInstance_.id)));
            }
        }
        return specification;
    }
}
