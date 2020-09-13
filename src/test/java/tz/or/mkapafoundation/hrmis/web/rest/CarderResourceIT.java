package tz.or.mkapafoundation.hrmis.web.rest;

import tz.or.mkapafoundation.hrmis.HrmisApp;
import tz.or.mkapafoundation.hrmis.config.TestSecurityConfiguration;
import tz.or.mkapafoundation.hrmis.domain.Carder;
import tz.or.mkapafoundation.hrmis.repository.CarderRepository;
import tz.or.mkapafoundation.hrmis.service.CarderService;
import tz.or.mkapafoundation.hrmis.service.dto.CarderDTO;
import tz.or.mkapafoundation.hrmis.service.mapper.CarderMapper;
import tz.or.mkapafoundation.hrmis.service.dto.CarderCriteria;
import tz.or.mkapafoundation.hrmis.service.CarderQueryService;

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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CarderResource} REST controller.
 */
@SpringBootTest(classes = { HrmisApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CarderResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CarderRepository carderRepository;

    @Autowired
    private CarderMapper carderMapper;

    @Autowired
    private CarderService carderService;

    @Autowired
    private CarderQueryService carderQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCarderMockMvc;

    private Carder carder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Carder createEntity(EntityManager em) {
        Carder carder = new Carder()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME);
        return carder;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Carder createUpdatedEntity(EntityManager em) {
        Carder carder = new Carder()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);
        return carder;
    }

    @BeforeEach
    public void initTest() {
        carder = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarder() throws Exception {
        int databaseSizeBeforeCreate = carderRepository.findAll().size();
        // Create the Carder
        CarderDTO carderDTO = carderMapper.toDto(carder);
        restCarderMockMvc.perform(post("/api/carders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carderDTO)))
            .andExpect(status().isCreated());

        // Validate the Carder in the database
        List<Carder> carderList = carderRepository.findAll();
        assertThat(carderList).hasSize(databaseSizeBeforeCreate + 1);
        Carder testCarder = carderList.get(carderList.size() - 1);
        assertThat(testCarder.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCarder.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCarderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carderRepository.findAll().size();

        // Create the Carder with an existing ID
        carder.setId(1L);
        CarderDTO carderDTO = carderMapper.toDto(carder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarderMockMvc.perform(post("/api/carders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Carder in the database
        List<Carder> carderList = carderRepository.findAll();
        assertThat(carderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = carderRepository.findAll().size();
        // set the field null
        carder.setName(null);

        // Create the Carder, which fails.
        CarderDTO carderDTO = carderMapper.toDto(carder);


        restCarderMockMvc.perform(post("/api/carders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carderDTO)))
            .andExpect(status().isBadRequest());

        List<Carder> carderList = carderRepository.findAll();
        assertThat(carderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCarders() throws Exception {
        // Initialize the database
        carderRepository.saveAndFlush(carder);

        // Get all the carderList
        restCarderMockMvc.perform(get("/api/carders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carder.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getCarder() throws Exception {
        // Initialize the database
        carderRepository.saveAndFlush(carder);

        // Get the carder
        restCarderMockMvc.perform(get("/api/carders/{id}", carder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(carder.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getCardersByIdFiltering() throws Exception {
        // Initialize the database
        carderRepository.saveAndFlush(carder);

        Long id = carder.getId();

        defaultCarderShouldBeFound("id.equals=" + id);
        defaultCarderShouldNotBeFound("id.notEquals=" + id);

        defaultCarderShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCarderShouldNotBeFound("id.greaterThan=" + id);

        defaultCarderShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCarderShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCardersByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        carderRepository.saveAndFlush(carder);

        // Get all the carderList where code equals to DEFAULT_CODE
        defaultCarderShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the carderList where code equals to UPDATED_CODE
        defaultCarderShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCardersByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        carderRepository.saveAndFlush(carder);

        // Get all the carderList where code not equals to DEFAULT_CODE
        defaultCarderShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the carderList where code not equals to UPDATED_CODE
        defaultCarderShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCardersByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        carderRepository.saveAndFlush(carder);

        // Get all the carderList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCarderShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the carderList where code equals to UPDATED_CODE
        defaultCarderShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCardersByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        carderRepository.saveAndFlush(carder);

        // Get all the carderList where code is not null
        defaultCarderShouldBeFound("code.specified=true");

        // Get all the carderList where code is null
        defaultCarderShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllCardersByCodeContainsSomething() throws Exception {
        // Initialize the database
        carderRepository.saveAndFlush(carder);

        // Get all the carderList where code contains DEFAULT_CODE
        defaultCarderShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the carderList where code contains UPDATED_CODE
        defaultCarderShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCardersByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        carderRepository.saveAndFlush(carder);

        // Get all the carderList where code does not contain DEFAULT_CODE
        defaultCarderShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the carderList where code does not contain UPDATED_CODE
        defaultCarderShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllCardersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        carderRepository.saveAndFlush(carder);

        // Get all the carderList where name equals to DEFAULT_NAME
        defaultCarderShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the carderList where name equals to UPDATED_NAME
        defaultCarderShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCardersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        carderRepository.saveAndFlush(carder);

        // Get all the carderList where name not equals to DEFAULT_NAME
        defaultCarderShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the carderList where name not equals to UPDATED_NAME
        defaultCarderShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCardersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        carderRepository.saveAndFlush(carder);

        // Get all the carderList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCarderShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the carderList where name equals to UPDATED_NAME
        defaultCarderShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCardersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        carderRepository.saveAndFlush(carder);

        // Get all the carderList where name is not null
        defaultCarderShouldBeFound("name.specified=true");

        // Get all the carderList where name is null
        defaultCarderShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCardersByNameContainsSomething() throws Exception {
        // Initialize the database
        carderRepository.saveAndFlush(carder);

        // Get all the carderList where name contains DEFAULT_NAME
        defaultCarderShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the carderList where name contains UPDATED_NAME
        defaultCarderShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCardersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        carderRepository.saveAndFlush(carder);

        // Get all the carderList where name does not contain DEFAULT_NAME
        defaultCarderShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the carderList where name does not contain UPDATED_NAME
        defaultCarderShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCarderShouldBeFound(String filter) throws Exception {
        restCarderMockMvc.perform(get("/api/carders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carder.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restCarderMockMvc.perform(get("/api/carders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCarderShouldNotBeFound(String filter) throws Exception {
        restCarderMockMvc.perform(get("/api/carders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCarderMockMvc.perform(get("/api/carders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCarder() throws Exception {
        // Get the carder
        restCarderMockMvc.perform(get("/api/carders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarder() throws Exception {
        // Initialize the database
        carderRepository.saveAndFlush(carder);

        int databaseSizeBeforeUpdate = carderRepository.findAll().size();

        // Update the carder
        Carder updatedCarder = carderRepository.findById(carder.getId()).get();
        // Disconnect from session so that the updates on updatedCarder are not directly saved in db
        em.detach(updatedCarder);
        updatedCarder
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);
        CarderDTO carderDTO = carderMapper.toDto(updatedCarder);

        restCarderMockMvc.perform(put("/api/carders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carderDTO)))
            .andExpect(status().isOk());

        // Validate the Carder in the database
        List<Carder> carderList = carderRepository.findAll();
        assertThat(carderList).hasSize(databaseSizeBeforeUpdate);
        Carder testCarder = carderList.get(carderList.size() - 1);
        assertThat(testCarder.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCarder.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCarder() throws Exception {
        int databaseSizeBeforeUpdate = carderRepository.findAll().size();

        // Create the Carder
        CarderDTO carderDTO = carderMapper.toDto(carder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarderMockMvc.perform(put("/api/carders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Carder in the database
        List<Carder> carderList = carderRepository.findAll();
        assertThat(carderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCarder() throws Exception {
        // Initialize the database
        carderRepository.saveAndFlush(carder);

        int databaseSizeBeforeDelete = carderRepository.findAll().size();

        // Delete the carder
        restCarderMockMvc.perform(delete("/api/carders/{id}", carder.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Carder> carderList = carderRepository.findAll();
        assertThat(carderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
