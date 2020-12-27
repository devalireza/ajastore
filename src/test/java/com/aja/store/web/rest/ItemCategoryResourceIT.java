package com.aja.store.web.rest;

import com.aja.store.Store13App;
import com.aja.store.domain.ItemCategory;
import com.aja.store.domain.ItemCategory;
import com.aja.store.repository.ItemCategoryRepository;
import com.aja.store.service.ItemCategoryService;
import com.aja.store.service.dto.ItemCategoryCriteria;
import com.aja.store.service.ItemCategoryQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ItemCategoryResource} REST controller.
 */
@SpringBootTest(classes = Store13App.class)
@AutoConfigureMockMvc
@WithMockUser
public class ItemCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ItemCategoryRepository itemCategoryRepository;

    @Autowired
    private ItemCategoryService itemCategoryService;

    @Autowired
    private ItemCategoryQueryService itemCategoryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restItemCategoryMockMvc;

    private ItemCategory itemCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemCategory createEntity(EntityManager em) {
        ItemCategory itemCategory = new ItemCategory()
            .name(DEFAULT_NAME);
        return itemCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemCategory createUpdatedEntity(EntityManager em) {
        ItemCategory itemCategory = new ItemCategory()
            .name(UPDATED_NAME);
        return itemCategory;
    }

    @BeforeEach
    public void initTest() {
        itemCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemCategory() throws Exception {
        int databaseSizeBeforeCreate = itemCategoryRepository.findAll().size();
        // Create the ItemCategory
        restItemCategoryMockMvc.perform(post("/api/item-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemCategory)))
            .andExpect(status().isCreated());

        // Validate the ItemCategory in the database
        List<ItemCategory> itemCategoryList = itemCategoryRepository.findAll();
        assertThat(itemCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        ItemCategory testItemCategory = itemCategoryList.get(itemCategoryList.size() - 1);
        assertThat(testItemCategory.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createItemCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemCategoryRepository.findAll().size();

        // Create the ItemCategory with an existing ID
        itemCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemCategoryMockMvc.perform(post("/api/item-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemCategory)))
            .andExpect(status().isBadRequest());

        // Validate the ItemCategory in the database
        List<ItemCategory> itemCategoryList = itemCategoryRepository.findAll();
        assertThat(itemCategoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllItemCategories() throws Exception {
        // Initialize the database
        itemCategoryRepository.saveAndFlush(itemCategory);

        // Get all the itemCategoryList
        restItemCategoryMockMvc.perform(get("/api/item-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getItemCategory() throws Exception {
        // Initialize the database
        itemCategoryRepository.saveAndFlush(itemCategory);

        // Get the itemCategory
        restItemCategoryMockMvc.perform(get("/api/item-categories/{id}", itemCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(itemCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getItemCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        itemCategoryRepository.saveAndFlush(itemCategory);

        Long id = itemCategory.getId();

        defaultItemCategoryShouldBeFound("id.equals=" + id);
        defaultItemCategoryShouldNotBeFound("id.notEquals=" + id);

        defaultItemCategoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultItemCategoryShouldNotBeFound("id.greaterThan=" + id);

        defaultItemCategoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultItemCategoryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllItemCategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        itemCategoryRepository.saveAndFlush(itemCategory);

        // Get all the itemCategoryList where name equals to DEFAULT_NAME
        defaultItemCategoryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the itemCategoryList where name equals to UPDATED_NAME
        defaultItemCategoryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllItemCategoriesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemCategoryRepository.saveAndFlush(itemCategory);

        // Get all the itemCategoryList where name not equals to DEFAULT_NAME
        defaultItemCategoryShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the itemCategoryList where name not equals to UPDATED_NAME
        defaultItemCategoryShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllItemCategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        itemCategoryRepository.saveAndFlush(itemCategory);

        // Get all the itemCategoryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultItemCategoryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the itemCategoryList where name equals to UPDATED_NAME
        defaultItemCategoryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllItemCategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemCategoryRepository.saveAndFlush(itemCategory);

        // Get all the itemCategoryList where name is not null
        defaultItemCategoryShouldBeFound("name.specified=true");

        // Get all the itemCategoryList where name is null
        defaultItemCategoryShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllItemCategoriesByNameContainsSomething() throws Exception {
        // Initialize the database
        itemCategoryRepository.saveAndFlush(itemCategory);

        // Get all the itemCategoryList where name contains DEFAULT_NAME
        defaultItemCategoryShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the itemCategoryList where name contains UPDATED_NAME
        defaultItemCategoryShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllItemCategoriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        itemCategoryRepository.saveAndFlush(itemCategory);

        // Get all the itemCategoryList where name does not contain DEFAULT_NAME
        defaultItemCategoryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the itemCategoryList where name does not contain UPDATED_NAME
        defaultItemCategoryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllItemCategoriesByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        itemCategoryRepository.saveAndFlush(itemCategory);
        ItemCategory parent = ItemCategoryResourceIT.createEntity(em);
        em.persist(parent);
        em.flush();
        itemCategory.setParent(parent);
        itemCategoryRepository.saveAndFlush(itemCategory);
        Long parentId = parent.getId();

        // Get all the itemCategoryList where parent equals to parentId
        defaultItemCategoryShouldBeFound("parentId.equals=" + parentId);

        // Get all the itemCategoryList where parent equals to parentId + 1
        defaultItemCategoryShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultItemCategoryShouldBeFound(String filter) throws Exception {
        restItemCategoryMockMvc.perform(get("/api/item-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restItemCategoryMockMvc.perform(get("/api/item-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultItemCategoryShouldNotBeFound(String filter) throws Exception {
        restItemCategoryMockMvc.perform(get("/api/item-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restItemCategoryMockMvc.perform(get("/api/item-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingItemCategory() throws Exception {
        // Get the itemCategory
        restItemCategoryMockMvc.perform(get("/api/item-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemCategory() throws Exception {
        // Initialize the database
        itemCategoryService.save(itemCategory);

        int databaseSizeBeforeUpdate = itemCategoryRepository.findAll().size();

        // Update the itemCategory
        ItemCategory updatedItemCategory = itemCategoryRepository.findById(itemCategory.getId()).get();
        // Disconnect from session so that the updates on updatedItemCategory are not directly saved in db
        em.detach(updatedItemCategory);
        updatedItemCategory
            .name(UPDATED_NAME);

        restItemCategoryMockMvc.perform(put("/api/item-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedItemCategory)))
            .andExpect(status().isOk());

        // Validate the ItemCategory in the database
        List<ItemCategory> itemCategoryList = itemCategoryRepository.findAll();
        assertThat(itemCategoryList).hasSize(databaseSizeBeforeUpdate);
        ItemCategory testItemCategory = itemCategoryList.get(itemCategoryList.size() - 1);
        assertThat(testItemCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingItemCategory() throws Exception {
        int databaseSizeBeforeUpdate = itemCategoryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemCategoryMockMvc.perform(put("/api/item-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemCategory)))
            .andExpect(status().isBadRequest());

        // Validate the ItemCategory in the database
        List<ItemCategory> itemCategoryList = itemCategoryRepository.findAll();
        assertThat(itemCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItemCategory() throws Exception {
        // Initialize the database
        itemCategoryService.save(itemCategory);

        int databaseSizeBeforeDelete = itemCategoryRepository.findAll().size();

        // Delete the itemCategory
        restItemCategoryMockMvc.perform(delete("/api/item-categories/{id}", itemCategory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemCategory> itemCategoryList = itemCategoryRepository.findAll();
        assertThat(itemCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
