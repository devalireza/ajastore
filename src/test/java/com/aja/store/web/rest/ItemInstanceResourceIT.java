package com.aja.store.web.rest;

import com.aja.store.Store13App;
import com.aja.store.domain.ItemInstance;
import com.aja.store.domain.Item;
import com.aja.store.domain.Store;
import com.aja.store.repository.ItemInstanceRepository;
import com.aja.store.service.ItemInstanceService;
import com.aja.store.service.dto.ItemInstanceCriteria;
import com.aja.store.service.ItemInstanceQueryService;

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
 * Integration tests for the {@link ItemInstanceResource} REST controller.
 */
@SpringBootTest(classes = Store13App.class)
@AutoConfigureMockMvc
@WithMockUser
public class ItemInstanceResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private ItemInstanceRepository itemInstanceRepository;

    @Autowired
    private ItemInstanceService itemInstanceService;

    @Autowired
    private ItemInstanceQueryService itemInstanceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restItemInstanceMockMvc;

    private ItemInstance itemInstance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemInstance createEntity(EntityManager em) {
        ItemInstance itemInstance = new ItemInstance()
            .code(DEFAULT_CODE);
        return itemInstance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemInstance createUpdatedEntity(EntityManager em) {
        ItemInstance itemInstance = new ItemInstance()
            .code(UPDATED_CODE);
        return itemInstance;
    }

    @BeforeEach
    public void initTest() {
        itemInstance = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemInstance() throws Exception {
        int databaseSizeBeforeCreate = itemInstanceRepository.findAll().size();
        // Create the ItemInstance
        restItemInstanceMockMvc.perform(post("/api/item-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemInstance)))
            .andExpect(status().isCreated());

        // Validate the ItemInstance in the database
        List<ItemInstance> itemInstanceList = itemInstanceRepository.findAll();
        assertThat(itemInstanceList).hasSize(databaseSizeBeforeCreate + 1);
        ItemInstance testItemInstance = itemInstanceList.get(itemInstanceList.size() - 1);
        assertThat(testItemInstance.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createItemInstanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemInstanceRepository.findAll().size();

        // Create the ItemInstance with an existing ID
        itemInstance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemInstanceMockMvc.perform(post("/api/item-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemInstance)))
            .andExpect(status().isBadRequest());

        // Validate the ItemInstance in the database
        List<ItemInstance> itemInstanceList = itemInstanceRepository.findAll();
        assertThat(itemInstanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemInstanceRepository.findAll().size();
        // set the field null
        itemInstance.setCode(null);

        // Create the ItemInstance, which fails.


        restItemInstanceMockMvc.perform(post("/api/item-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemInstance)))
            .andExpect(status().isBadRequest());

        List<ItemInstance> itemInstanceList = itemInstanceRepository.findAll();
        assertThat(itemInstanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItemInstances() throws Exception {
        // Initialize the database
        itemInstanceRepository.saveAndFlush(itemInstance);

        // Get all the itemInstanceList
        restItemInstanceMockMvc.perform(get("/api/item-instances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }
    
    @Test
    @Transactional
    public void getItemInstance() throws Exception {
        // Initialize the database
        itemInstanceRepository.saveAndFlush(itemInstance);

        // Get the itemInstance
        restItemInstanceMockMvc.perform(get("/api/item-instances/{id}", itemInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(itemInstance.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }


    @Test
    @Transactional
    public void getItemInstancesByIdFiltering() throws Exception {
        // Initialize the database
        itemInstanceRepository.saveAndFlush(itemInstance);

        Long id = itemInstance.getId();

        defaultItemInstanceShouldBeFound("id.equals=" + id);
        defaultItemInstanceShouldNotBeFound("id.notEquals=" + id);

        defaultItemInstanceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultItemInstanceShouldNotBeFound("id.greaterThan=" + id);

        defaultItemInstanceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultItemInstanceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllItemInstancesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        itemInstanceRepository.saveAndFlush(itemInstance);

        // Get all the itemInstanceList where code equals to DEFAULT_CODE
        defaultItemInstanceShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the itemInstanceList where code equals to UPDATED_CODE
        defaultItemInstanceShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllItemInstancesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemInstanceRepository.saveAndFlush(itemInstance);

        // Get all the itemInstanceList where code not equals to DEFAULT_CODE
        defaultItemInstanceShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the itemInstanceList where code not equals to UPDATED_CODE
        defaultItemInstanceShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllItemInstancesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        itemInstanceRepository.saveAndFlush(itemInstance);

        // Get all the itemInstanceList where code in DEFAULT_CODE or UPDATED_CODE
        defaultItemInstanceShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the itemInstanceList where code equals to UPDATED_CODE
        defaultItemInstanceShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllItemInstancesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemInstanceRepository.saveAndFlush(itemInstance);

        // Get all the itemInstanceList where code is not null
        defaultItemInstanceShouldBeFound("code.specified=true");

        // Get all the itemInstanceList where code is null
        defaultItemInstanceShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllItemInstancesByCodeContainsSomething() throws Exception {
        // Initialize the database
        itemInstanceRepository.saveAndFlush(itemInstance);

        // Get all the itemInstanceList where code contains DEFAULT_CODE
        defaultItemInstanceShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the itemInstanceList where code contains UPDATED_CODE
        defaultItemInstanceShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllItemInstancesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        itemInstanceRepository.saveAndFlush(itemInstance);

        // Get all the itemInstanceList where code does not contain DEFAULT_CODE
        defaultItemInstanceShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the itemInstanceList where code does not contain UPDATED_CODE
        defaultItemInstanceShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllItemInstancesByItemIsEqualToSomething() throws Exception {
        // Initialize the database
        itemInstanceRepository.saveAndFlush(itemInstance);
        Item item = ItemResourceIT.createEntity(em);
        em.persist(item);
        em.flush();
        itemInstance.setItem(item);
        itemInstanceRepository.saveAndFlush(itemInstance);
        Long itemId = item.getId();

        // Get all the itemInstanceList where item equals to itemId
        defaultItemInstanceShouldBeFound("itemId.equals=" + itemId);

        // Get all the itemInstanceList where item equals to itemId + 1
        defaultItemInstanceShouldNotBeFound("itemId.equals=" + (itemId + 1));
    }


    @Test
    @Transactional
    public void getAllItemInstancesByStoreIsEqualToSomething() throws Exception {
        // Initialize the database
        itemInstanceRepository.saveAndFlush(itemInstance);
        Store store = StoreResourceIT.createEntity(em);
        em.persist(store);
        em.flush();
        itemInstance.setStore(store);
        itemInstanceRepository.saveAndFlush(itemInstance);
        Long storeId = store.getId();

        // Get all the itemInstanceList where store equals to storeId
        defaultItemInstanceShouldBeFound("storeId.equals=" + storeId);

        // Get all the itemInstanceList where store equals to storeId + 1
        defaultItemInstanceShouldNotBeFound("storeId.equals=" + (storeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultItemInstanceShouldBeFound(String filter) throws Exception {
        restItemInstanceMockMvc.perform(get("/api/item-instances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));

        // Check, that the count call also returns 1
        restItemInstanceMockMvc.perform(get("/api/item-instances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultItemInstanceShouldNotBeFound(String filter) throws Exception {
        restItemInstanceMockMvc.perform(get("/api/item-instances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restItemInstanceMockMvc.perform(get("/api/item-instances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingItemInstance() throws Exception {
        // Get the itemInstance
        restItemInstanceMockMvc.perform(get("/api/item-instances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemInstance() throws Exception {
        // Initialize the database
        itemInstanceService.save(itemInstance);

        int databaseSizeBeforeUpdate = itemInstanceRepository.findAll().size();

        // Update the itemInstance
        ItemInstance updatedItemInstance = itemInstanceRepository.findById(itemInstance.getId()).get();
        // Disconnect from session so that the updates on updatedItemInstance are not directly saved in db
        em.detach(updatedItemInstance);
        updatedItemInstance
            .code(UPDATED_CODE);

        restItemInstanceMockMvc.perform(put("/api/item-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedItemInstance)))
            .andExpect(status().isOk());

        // Validate the ItemInstance in the database
        List<ItemInstance> itemInstanceList = itemInstanceRepository.findAll();
        assertThat(itemInstanceList).hasSize(databaseSizeBeforeUpdate);
        ItemInstance testItemInstance = itemInstanceList.get(itemInstanceList.size() - 1);
        assertThat(testItemInstance.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingItemInstance() throws Exception {
        int databaseSizeBeforeUpdate = itemInstanceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemInstanceMockMvc.perform(put("/api/item-instances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemInstance)))
            .andExpect(status().isBadRequest());

        // Validate the ItemInstance in the database
        List<ItemInstance> itemInstanceList = itemInstanceRepository.findAll();
        assertThat(itemInstanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItemInstance() throws Exception {
        // Initialize the database
        itemInstanceService.save(itemInstance);

        int databaseSizeBeforeDelete = itemInstanceRepository.findAll().size();

        // Delete the itemInstance
        restItemInstanceMockMvc.perform(delete("/api/item-instances/{id}", itemInstance.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemInstance> itemInstanceList = itemInstanceRepository.findAll();
        assertThat(itemInstanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
