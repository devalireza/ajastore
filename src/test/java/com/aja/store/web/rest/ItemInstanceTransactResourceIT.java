package com.aja.store.web.rest;

import com.aja.store.Store13App;
import com.aja.store.domain.ItemInstanceTransact;
import com.aja.store.domain.User;
import com.aja.store.domain.ItemInstance;
import com.aja.store.repository.ItemInstanceTransactRepository;
import com.aja.store.service.ItemInstanceTransactService;
import com.aja.store.service.dto.ItemInstanceTransactCriteria;
import com.aja.store.service.ItemInstanceTransactQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ItemInstanceTransactResource} REST controller.
 */
@SpringBootTest(classes = Store13App.class)
@AutoConfigureMockMvc
@WithMockUser
public class ItemInstanceTransactResourceIT {

    private static final LocalDate DEFAULT_DELIVERY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELIVERY_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DELIVERY_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_TRANSACTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_TYPE = "BBBBBBBBBB";

    @Autowired
    private ItemInstanceTransactRepository itemInstanceTransactRepository;

    @Autowired
    private ItemInstanceTransactService itemInstanceTransactService;

    @Autowired
    private ItemInstanceTransactQueryService itemInstanceTransactQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restItemInstanceTransactMockMvc;

    private ItemInstanceTransact itemInstanceTransact;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemInstanceTransact createEntity(EntityManager em) {
        ItemInstanceTransact itemInstanceTransact = new ItemInstanceTransact()
            .deliveryDate(DEFAULT_DELIVERY_DATE)
            .transactionType(DEFAULT_TRANSACTION_TYPE);
        return itemInstanceTransact;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemInstanceTransact createUpdatedEntity(EntityManager em) {
        ItemInstanceTransact itemInstanceTransact = new ItemInstanceTransact()
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .transactionType(UPDATED_TRANSACTION_TYPE);
        return itemInstanceTransact;
    }

    @BeforeEach
    public void initTest() {
        itemInstanceTransact = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemInstanceTransact() throws Exception {
        int databaseSizeBeforeCreate = itemInstanceTransactRepository.findAll().size();
        // Create the ItemInstanceTransact
        restItemInstanceTransactMockMvc.perform(post("/api/item-instance-transacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemInstanceTransact)))
            .andExpect(status().isCreated());

        // Validate the ItemInstanceTransact in the database
        List<ItemInstanceTransact> itemInstanceTransactList = itemInstanceTransactRepository.findAll();
        assertThat(itemInstanceTransactList).hasSize(databaseSizeBeforeCreate + 1);
        ItemInstanceTransact testItemInstanceTransact = itemInstanceTransactList.get(itemInstanceTransactList.size() - 1);
        assertThat(testItemInstanceTransact.getDeliveryDate()).isEqualTo(DEFAULT_DELIVERY_DATE);
        assertThat(testItemInstanceTransact.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void createItemInstanceTransactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemInstanceTransactRepository.findAll().size();

        // Create the ItemInstanceTransact with an existing ID
        itemInstanceTransact.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemInstanceTransactMockMvc.perform(post("/api/item-instance-transacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemInstanceTransact)))
            .andExpect(status().isBadRequest());

        // Validate the ItemInstanceTransact in the database
        List<ItemInstanceTransact> itemInstanceTransactList = itemInstanceTransactRepository.findAll();
        assertThat(itemInstanceTransactList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDeliveryDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemInstanceTransactRepository.findAll().size();
        // set the field null
        itemInstanceTransact.setDeliveryDate(null);

        // Create the ItemInstanceTransact, which fails.


        restItemInstanceTransactMockMvc.perform(post("/api/item-instance-transacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemInstanceTransact)))
            .andExpect(status().isBadRequest());

        List<ItemInstanceTransact> itemInstanceTransactList = itemInstanceTransactRepository.findAll();
        assertThat(itemInstanceTransactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemInstanceTransactRepository.findAll().size();
        // set the field null
        itemInstanceTransact.setTransactionType(null);

        // Create the ItemInstanceTransact, which fails.


        restItemInstanceTransactMockMvc.perform(post("/api/item-instance-transacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemInstanceTransact)))
            .andExpect(status().isBadRequest());

        List<ItemInstanceTransact> itemInstanceTransactList = itemInstanceTransactRepository.findAll();
        assertThat(itemInstanceTransactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItemInstanceTransacts() throws Exception {
        // Initialize the database
        itemInstanceTransactRepository.saveAndFlush(itemInstanceTransact);

        // Get all the itemInstanceTransactList
        restItemInstanceTransactMockMvc.perform(get("/api/item-instance-transacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemInstanceTransact.getId().intValue())))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE)));
    }
    
    @Test
    @Transactional
    public void getItemInstanceTransact() throws Exception {
        // Initialize the database
        itemInstanceTransactRepository.saveAndFlush(itemInstanceTransact);

        // Get the itemInstanceTransact
        restItemInstanceTransactMockMvc.perform(get("/api/item-instance-transacts/{id}", itemInstanceTransact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(itemInstanceTransact.getId().intValue()))
            .andExpect(jsonPath("$.deliveryDate").value(DEFAULT_DELIVERY_DATE.toString()))
            .andExpect(jsonPath("$.transactionType").value(DEFAULT_TRANSACTION_TYPE));
    }


    @Test
    @Transactional
    public void getItemInstanceTransactsByIdFiltering() throws Exception {
        // Initialize the database
        itemInstanceTransactRepository.saveAndFlush(itemInstanceTransact);

        Long id = itemInstanceTransact.getId();

        defaultItemInstanceTransactShouldBeFound("id.equals=" + id);
        defaultItemInstanceTransactShouldNotBeFound("id.notEquals=" + id);

        defaultItemInstanceTransactShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultItemInstanceTransactShouldNotBeFound("id.greaterThan=" + id);

        defaultItemInstanceTransactShouldBeFound("id.lessThanOrEqual=" + id);
        defaultItemInstanceTransactShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllItemInstanceTransactsByDeliveryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        itemInstanceTransactRepository.saveAndFlush(itemInstanceTransact);

        // Get all the itemInstanceTransactList where deliveryDate equals to DEFAULT_DELIVERY_DATE
        defaultItemInstanceTransactShouldBeFound("deliveryDate.equals=" + DEFAULT_DELIVERY_DATE);

        // Get all the itemInstanceTransactList where deliveryDate equals to UPDATED_DELIVERY_DATE
        defaultItemInstanceTransactShouldNotBeFound("deliveryDate.equals=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllItemInstanceTransactsByDeliveryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemInstanceTransactRepository.saveAndFlush(itemInstanceTransact);

        // Get all the itemInstanceTransactList where deliveryDate not equals to DEFAULT_DELIVERY_DATE
        defaultItemInstanceTransactShouldNotBeFound("deliveryDate.notEquals=" + DEFAULT_DELIVERY_DATE);

        // Get all the itemInstanceTransactList where deliveryDate not equals to UPDATED_DELIVERY_DATE
        defaultItemInstanceTransactShouldBeFound("deliveryDate.notEquals=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllItemInstanceTransactsByDeliveryDateIsInShouldWork() throws Exception {
        // Initialize the database
        itemInstanceTransactRepository.saveAndFlush(itemInstanceTransact);

        // Get all the itemInstanceTransactList where deliveryDate in DEFAULT_DELIVERY_DATE or UPDATED_DELIVERY_DATE
        defaultItemInstanceTransactShouldBeFound("deliveryDate.in=" + DEFAULT_DELIVERY_DATE + "," + UPDATED_DELIVERY_DATE);

        // Get all the itemInstanceTransactList where deliveryDate equals to UPDATED_DELIVERY_DATE
        defaultItemInstanceTransactShouldNotBeFound("deliveryDate.in=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllItemInstanceTransactsByDeliveryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemInstanceTransactRepository.saveAndFlush(itemInstanceTransact);

        // Get all the itemInstanceTransactList where deliveryDate is not null
        defaultItemInstanceTransactShouldBeFound("deliveryDate.specified=true");

        // Get all the itemInstanceTransactList where deliveryDate is null
        defaultItemInstanceTransactShouldNotBeFound("deliveryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemInstanceTransactsByDeliveryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemInstanceTransactRepository.saveAndFlush(itemInstanceTransact);

        // Get all the itemInstanceTransactList where deliveryDate is greater than or equal to DEFAULT_DELIVERY_DATE
        defaultItemInstanceTransactShouldBeFound("deliveryDate.greaterThanOrEqual=" + DEFAULT_DELIVERY_DATE);

        // Get all the itemInstanceTransactList where deliveryDate is greater than or equal to UPDATED_DELIVERY_DATE
        defaultItemInstanceTransactShouldNotBeFound("deliveryDate.greaterThanOrEqual=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllItemInstanceTransactsByDeliveryDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemInstanceTransactRepository.saveAndFlush(itemInstanceTransact);

        // Get all the itemInstanceTransactList where deliveryDate is less than or equal to DEFAULT_DELIVERY_DATE
        defaultItemInstanceTransactShouldBeFound("deliveryDate.lessThanOrEqual=" + DEFAULT_DELIVERY_DATE);

        // Get all the itemInstanceTransactList where deliveryDate is less than or equal to SMALLER_DELIVERY_DATE
        defaultItemInstanceTransactShouldNotBeFound("deliveryDate.lessThanOrEqual=" + SMALLER_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllItemInstanceTransactsByDeliveryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        itemInstanceTransactRepository.saveAndFlush(itemInstanceTransact);

        // Get all the itemInstanceTransactList where deliveryDate is less than DEFAULT_DELIVERY_DATE
        defaultItemInstanceTransactShouldNotBeFound("deliveryDate.lessThan=" + DEFAULT_DELIVERY_DATE);

        // Get all the itemInstanceTransactList where deliveryDate is less than UPDATED_DELIVERY_DATE
        defaultItemInstanceTransactShouldBeFound("deliveryDate.lessThan=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllItemInstanceTransactsByDeliveryDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemInstanceTransactRepository.saveAndFlush(itemInstanceTransact);

        // Get all the itemInstanceTransactList where deliveryDate is greater than DEFAULT_DELIVERY_DATE
        defaultItemInstanceTransactShouldNotBeFound("deliveryDate.greaterThan=" + DEFAULT_DELIVERY_DATE);

        // Get all the itemInstanceTransactList where deliveryDate is greater than SMALLER_DELIVERY_DATE
        defaultItemInstanceTransactShouldBeFound("deliveryDate.greaterThan=" + SMALLER_DELIVERY_DATE);
    }


    @Test
    @Transactional
    public void getAllItemInstanceTransactsByTransactionTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        itemInstanceTransactRepository.saveAndFlush(itemInstanceTransact);

        // Get all the itemInstanceTransactList where transactionType equals to DEFAULT_TRANSACTION_TYPE
        defaultItemInstanceTransactShouldBeFound("transactionType.equals=" + DEFAULT_TRANSACTION_TYPE);

        // Get all the itemInstanceTransactList where transactionType equals to UPDATED_TRANSACTION_TYPE
        defaultItemInstanceTransactShouldNotBeFound("transactionType.equals=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllItemInstanceTransactsByTransactionTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemInstanceTransactRepository.saveAndFlush(itemInstanceTransact);

        // Get all the itemInstanceTransactList where transactionType not equals to DEFAULT_TRANSACTION_TYPE
        defaultItemInstanceTransactShouldNotBeFound("transactionType.notEquals=" + DEFAULT_TRANSACTION_TYPE);

        // Get all the itemInstanceTransactList where transactionType not equals to UPDATED_TRANSACTION_TYPE
        defaultItemInstanceTransactShouldBeFound("transactionType.notEquals=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllItemInstanceTransactsByTransactionTypeIsInShouldWork() throws Exception {
        // Initialize the database
        itemInstanceTransactRepository.saveAndFlush(itemInstanceTransact);

        // Get all the itemInstanceTransactList where transactionType in DEFAULT_TRANSACTION_TYPE or UPDATED_TRANSACTION_TYPE
        defaultItemInstanceTransactShouldBeFound("transactionType.in=" + DEFAULT_TRANSACTION_TYPE + "," + UPDATED_TRANSACTION_TYPE);

        // Get all the itemInstanceTransactList where transactionType equals to UPDATED_TRANSACTION_TYPE
        defaultItemInstanceTransactShouldNotBeFound("transactionType.in=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllItemInstanceTransactsByTransactionTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemInstanceTransactRepository.saveAndFlush(itemInstanceTransact);

        // Get all the itemInstanceTransactList where transactionType is not null
        defaultItemInstanceTransactShouldBeFound("transactionType.specified=true");

        // Get all the itemInstanceTransactList where transactionType is null
        defaultItemInstanceTransactShouldNotBeFound("transactionType.specified=false");
    }
                @Test
    @Transactional
    public void getAllItemInstanceTransactsByTransactionTypeContainsSomething() throws Exception {
        // Initialize the database
        itemInstanceTransactRepository.saveAndFlush(itemInstanceTransact);

        // Get all the itemInstanceTransactList where transactionType contains DEFAULT_TRANSACTION_TYPE
        defaultItemInstanceTransactShouldBeFound("transactionType.contains=" + DEFAULT_TRANSACTION_TYPE);

        // Get all the itemInstanceTransactList where transactionType contains UPDATED_TRANSACTION_TYPE
        defaultItemInstanceTransactShouldNotBeFound("transactionType.contains=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllItemInstanceTransactsByTransactionTypeNotContainsSomething() throws Exception {
        // Initialize the database
        itemInstanceTransactRepository.saveAndFlush(itemInstanceTransact);

        // Get all the itemInstanceTransactList where transactionType does not contain DEFAULT_TRANSACTION_TYPE
        defaultItemInstanceTransactShouldNotBeFound("transactionType.doesNotContain=" + DEFAULT_TRANSACTION_TYPE);

        // Get all the itemInstanceTransactList where transactionType does not contain UPDATED_TRANSACTION_TYPE
        defaultItemInstanceTransactShouldBeFound("transactionType.doesNotContain=" + UPDATED_TRANSACTION_TYPE);
    }


    @Test
    @Transactional
    public void getAllItemInstanceTransactsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        itemInstanceTransactRepository.saveAndFlush(itemInstanceTransact);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        itemInstanceTransact.setUser(user);
        itemInstanceTransactRepository.saveAndFlush(itemInstanceTransact);
        Long userId = user.getId();

        // Get all the itemInstanceTransactList where user equals to userId
        defaultItemInstanceTransactShouldBeFound("userId.equals=" + userId);

        // Get all the itemInstanceTransactList where user equals to userId + 1
        defaultItemInstanceTransactShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllItemInstanceTransactsByItemInstanceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        itemInstanceTransactRepository.saveAndFlush(itemInstanceTransact);
        ItemInstance itemInstanceId = ItemInstanceResourceIT.createEntity(em);
        em.persist(itemInstanceId);
        em.flush();
        itemInstanceTransact.setItemInstanceId(itemInstanceId);
        itemInstanceTransactRepository.saveAndFlush(itemInstanceTransact);
        Long itemInstanceIdId = itemInstanceId.getId();

        // Get all the itemInstanceTransactList where itemInstanceId equals to itemInstanceIdId
        defaultItemInstanceTransactShouldBeFound("itemInstanceIdId.equals=" + itemInstanceIdId);

        // Get all the itemInstanceTransactList where itemInstanceId equals to itemInstanceIdId + 1
        defaultItemInstanceTransactShouldNotBeFound("itemInstanceIdId.equals=" + (itemInstanceIdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultItemInstanceTransactShouldBeFound(String filter) throws Exception {
        restItemInstanceTransactMockMvc.perform(get("/api/item-instance-transacts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemInstanceTransact.getId().intValue())))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE)));

        // Check, that the count call also returns 1
        restItemInstanceTransactMockMvc.perform(get("/api/item-instance-transacts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultItemInstanceTransactShouldNotBeFound(String filter) throws Exception {
        restItemInstanceTransactMockMvc.perform(get("/api/item-instance-transacts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restItemInstanceTransactMockMvc.perform(get("/api/item-instance-transacts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingItemInstanceTransact() throws Exception {
        // Get the itemInstanceTransact
        restItemInstanceTransactMockMvc.perform(get("/api/item-instance-transacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemInstanceTransact() throws Exception {
        // Initialize the database
        itemInstanceTransactService.save(itemInstanceTransact);

        int databaseSizeBeforeUpdate = itemInstanceTransactRepository.findAll().size();

        // Update the itemInstanceTransact
        ItemInstanceTransact updatedItemInstanceTransact = itemInstanceTransactRepository.findById(itemInstanceTransact.getId()).get();
        // Disconnect from session so that the updates on updatedItemInstanceTransact are not directly saved in db
        em.detach(updatedItemInstanceTransact);
        updatedItemInstanceTransact
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .transactionType(UPDATED_TRANSACTION_TYPE);

        restItemInstanceTransactMockMvc.perform(put("/api/item-instance-transacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedItemInstanceTransact)))
            .andExpect(status().isOk());

        // Validate the ItemInstanceTransact in the database
        List<ItemInstanceTransact> itemInstanceTransactList = itemInstanceTransactRepository.findAll();
        assertThat(itemInstanceTransactList).hasSize(databaseSizeBeforeUpdate);
        ItemInstanceTransact testItemInstanceTransact = itemInstanceTransactList.get(itemInstanceTransactList.size() - 1);
        assertThat(testItemInstanceTransact.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testItemInstanceTransact.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingItemInstanceTransact() throws Exception {
        int databaseSizeBeforeUpdate = itemInstanceTransactRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemInstanceTransactMockMvc.perform(put("/api/item-instance-transacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemInstanceTransact)))
            .andExpect(status().isBadRequest());

        // Validate the ItemInstanceTransact in the database
        List<ItemInstanceTransact> itemInstanceTransactList = itemInstanceTransactRepository.findAll();
        assertThat(itemInstanceTransactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItemInstanceTransact() throws Exception {
        // Initialize the database
        itemInstanceTransactService.save(itemInstanceTransact);

        int databaseSizeBeforeDelete = itemInstanceTransactRepository.findAll().size();

        // Delete the itemInstanceTransact
        restItemInstanceTransactMockMvc.perform(delete("/api/item-instance-transacts/{id}", itemInstanceTransact.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemInstanceTransact> itemInstanceTransactList = itemInstanceTransactRepository.findAll();
        assertThat(itemInstanceTransactList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
