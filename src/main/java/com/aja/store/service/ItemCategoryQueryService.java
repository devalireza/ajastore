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

import com.aja.store.domain.ItemCategory;
import com.aja.store.domain.*; // for static metamodels
import com.aja.store.repository.ItemCategoryRepository;
import com.aja.store.service.dto.ItemCategoryCriteria;

/**
 * Service for executing complex queries for {@link ItemCategory} entities in the database.
 * The main input is a {@link ItemCategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ItemCategory} or a {@link Page} of {@link ItemCategory} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ItemCategoryQueryService extends QueryService<ItemCategory> {

    private final Logger log = LoggerFactory.getLogger(ItemCategoryQueryService.class);

    private final ItemCategoryRepository itemCategoryRepository;

    public ItemCategoryQueryService(ItemCategoryRepository itemCategoryRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
    }

    /**
     * Return a {@link List} of {@link ItemCategory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ItemCategory> findByCriteria(ItemCategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ItemCategory> specification = createSpecification(criteria);
        return itemCategoryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ItemCategory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemCategory> findByCriteria(ItemCategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ItemCategory> specification = createSpecification(criteria);
        return itemCategoryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ItemCategoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ItemCategory> specification = createSpecification(criteria);
        return itemCategoryRepository.count(specification);
    }

    /**
     * Function to convert {@link ItemCategoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ItemCategory> createSpecification(ItemCategoryCriteria criteria) {
        Specification<ItemCategory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ItemCategory_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ItemCategory_.name));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentId(),
                    root -> root.join(ItemCategory_.parent, JoinType.LEFT).get(ItemCategory_.id)));
            }
        }
        return specification;
    }
}
