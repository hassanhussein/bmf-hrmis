package tz.or.mkapafoundation.hrmis.web.rest;

import tz.or.mkapafoundation.hrmis.HrmisApp;
import tz.or.mkapafoundation.hrmis.config.TestSecurityConfiguration;
import tz.or.mkapafoundation.hrmis.domain.GeographicLevel;
import tz.or.mkapafoundation.hrmis.repository.GeographicLevelRepository;
import tz.or.mkapafoundation.hrmis.service.GeographicLevelService;
import tz.or.mkapafoundation.hrmis.service.dto.GeographicLevelDTO;
import tz.or.mkapafoundation.hrmis.service.mapper.GeographicLevelMapper;
import tz.or.mkapafoundation.hrmis.service.dto.GeographicLevelCriteria;
import tz.or.mkapafoundation.hrmis.service.GeographicLevelQueryService;

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
 * Integration tests for the {@link GeographicLevelResource} REST controller.
 */
@SpringBootTest(classes = { HrmisApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class GeographicLevelResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_LEVEL_NUMBER = 1;
    private static final Integer UPDATED_LEVEL_NUMBER = 2;
    private static final Integer SMALLER_LEVEL_NUMBER = 1 - 1;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private GeographicLevelRepository geographicLevelRepository;

    @Autowired
    private GeographicLevelMapper geographicLevelMapper;

    @Autowired
    private GeographicLevelService geographicLevelService;

    @Autowired
    private GeographicLevelQueryService geographicLevelQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGeographicLevelMockMvc;

    private GeographicLevel geographicLevel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GeographicLevel createEntity(EntityManager em) {
        GeographicLevel geographicLevel = new GeographicLevel()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .levelNumber(DEFAULT_LEVEL_NUMBER)
            .active(DEFAULT_ACTIVE);
        return geographicLevel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GeographicLevel createUpdatedEntity(EntityManager em) {
        GeographicLevel geographicLevel = new GeographicLevel()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .levelNumber(UPDATED_LEVEL_NUMBER)
            .active(UPDATED_ACTIVE);
        return geographicLevel;
    }

    @BeforeEach
    public void initTest() {
        geographicLevel = createEntity(em);
    }

    @Test
    @Transactional
    public void createGeographicLevel() throws Exception {
        int databaseSizeBeforeCreate = geographicLevelRepository.findAll().size();
        // Create the GeographicLevel
        GeographicLevelDTO geographicLevelDTO = geographicLevelMapper.toDto(geographicLevel);
        restGeographicLevelMockMvc.perform(post("/api/geographic-levels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geographicLevelDTO)))
            .andExpect(status().isCreated());

        // Validate the GeographicLevel in the database
        List<GeographicLevel> geographicLevelList = geographicLevelRepository.findAll();
        assertThat(geographicLevelList).hasSize(databaseSizeBeforeCreate + 1);
        GeographicLevel testGeographicLevel = geographicLevelList.get(geographicLevelList.size() - 1);
        assertThat(testGeographicLevel.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testGeographicLevel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGeographicLevel.getLevelNumber()).isEqualTo(DEFAULT_LEVEL_NUMBER);
        assertThat(testGeographicLevel.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createGeographicLevelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = geographicLevelRepository.findAll().size();

        // Create the GeographicLevel with an existing ID
        geographicLevel.setId(1L);
        GeographicLevelDTO geographicLevelDTO = geographicLevelMapper.toDto(geographicLevel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeographicLevelMockMvc.perform(post("/api/geographic-levels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geographicLevelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GeographicLevel in the database
        List<GeographicLevel> geographicLevelList = geographicLevelRepository.findAll();
        assertThat(geographicLevelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = geographicLevelRepository.findAll().size();
        // set the field null
        geographicLevel.setName(null);

        // Create the GeographicLevel, which fails.
        GeographicLevelDTO geographicLevelDTO = geographicLevelMapper.toDto(geographicLevel);


        restGeographicLevelMockMvc.perform(post("/api/geographic-levels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geographicLevelDTO)))
            .andExpect(status().isBadRequest());

        List<GeographicLevel> geographicLevelList = geographicLevelRepository.findAll();
        assertThat(geographicLevelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGeographicLevels() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        // Get all the geographicLevelList
        restGeographicLevelMockMvc.perform(get("/api/geographic-levels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geographicLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].levelNumber").value(hasItem(DEFAULT_LEVEL_NUMBER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getGeographicLevel() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        // Get the geographicLevel
        restGeographicLevelMockMvc.perform(get("/api/geographic-levels/{id}", geographicLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(geographicLevel.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.levelNumber").value(DEFAULT_LEVEL_NUMBER))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getGeographicLevelsByIdFiltering() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        Long id = geographicLevel.getId();

        defaultGeographicLevelShouldBeFound("id.equals=" + id);
        defaultGeographicLevelShouldNotBeFound("id.notEquals=" + id);

        defaultGeographicLevelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGeographicLevelShouldNotBeFound("id.greaterThan=" + id);

        defaultGeographicLevelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGeographicLevelShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllGeographicLevelsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        // Get all the geographicLevelList where code equals to DEFAULT_CODE
        defaultGeographicLevelShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the geographicLevelList where code equals to UPDATED_CODE
        defaultGeographicLevelShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllGeographicLevelsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        // Get all the geographicLevelList where code not equals to DEFAULT_CODE
        defaultGeographicLevelShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the geographicLevelList where code not equals to UPDATED_CODE
        defaultGeographicLevelShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllGeographicLevelsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        // Get all the geographicLevelList where code in DEFAULT_CODE or UPDATED_CODE
        defaultGeographicLevelShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the geographicLevelList where code equals to UPDATED_CODE
        defaultGeographicLevelShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllGeographicLevelsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        // Get all the geographicLevelList where code is not null
        defaultGeographicLevelShouldBeFound("code.specified=true");

        // Get all the geographicLevelList where code is null
        defaultGeographicLevelShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllGeographicLevelsByCodeContainsSomething() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        // Get all the geographicLevelList where code contains DEFAULT_CODE
        defaultGeographicLevelShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the geographicLevelList where code contains UPDATED_CODE
        defaultGeographicLevelShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllGeographicLevelsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        // Get all the geographicLevelList where code does not contain DEFAULT_CODE
        defaultGeographicLevelShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the geographicLevelList where code does not contain UPDATED_CODE
        defaultGeographicLevelShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllGeographicLevelsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        // Get all the geographicLevelList where name equals to DEFAULT_NAME
        defaultGeographicLevelShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the geographicLevelList where name equals to UPDATED_NAME
        defaultGeographicLevelShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGeographicLevelsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        // Get all the geographicLevelList where name not equals to DEFAULT_NAME
        defaultGeographicLevelShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the geographicLevelList where name not equals to UPDATED_NAME
        defaultGeographicLevelShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGeographicLevelsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        // Get all the geographicLevelList where name in DEFAULT_NAME or UPDATED_NAME
        defaultGeographicLevelShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the geographicLevelList where name equals to UPDATED_NAME
        defaultGeographicLevelShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGeographicLevelsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        // Get all the geographicLevelList where name is not null
        defaultGeographicLevelShouldBeFound("name.specified=true");

        // Get all the geographicLevelList where name is null
        defaultGeographicLevelShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllGeographicLevelsByNameContainsSomething() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        // Get all the geographicLevelList where name contains DEFAULT_NAME
        defaultGeographicLevelShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the geographicLevelList where name contains UPDATED_NAME
        defaultGeographicLevelShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGeographicLevelsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        // Get all the geographicLevelList where name does not contain DEFAULT_NAME
        defaultGeographicLevelShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the geographicLevelList where name does not contain UPDATED_NAME
        defaultGeographicLevelShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllGeographicLevelsByLevelNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        // Get all the geographicLevelList where levelNumber equals to DEFAULT_LEVEL_NUMBER
        defaultGeographicLevelShouldBeFound("levelNumber.equals=" + DEFAULT_LEVEL_NUMBER);

        // Get all the geographicLevelList where levelNumber equals to UPDATED_LEVEL_NUMBER
        defaultGeographicLevelShouldNotBeFound("levelNumber.equals=" + UPDATED_LEVEL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllGeographicLevelsByLevelNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        // Get all the geographicLevelList where levelNumber not equals to DEFAULT_LEVEL_NUMBER
        defaultGeographicLevelShouldNotBeFound("levelNumber.notEquals=" + DEFAULT_LEVEL_NUMBER);

        // Get all the geographicLevelList where levelNumber not equals to UPDATED_LEVEL_NUMBER
        defaultGeographicLevelShouldBeFound("levelNumber.notEquals=" + UPDATED_LEVEL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllGeographicLevelsByLevelNumberIsInShouldWork() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        // Get all the geographicLevelList where levelNumber in DEFAULT_LEVEL_NUMBER or UPDATED_LEVEL_NUMBER
        defaultGeographicLevelShouldBeFound("levelNumber.in=" + DEFAULT_LEVEL_NUMBER + "," + UPDATED_LEVEL_NUMBER);

        // Get all the geographicLevelList where levelNumber equals to UPDATED_LEVEL_NUMBER
        defaultGeographicLevelShouldNotBeFound("levelNumber.in=" + UPDATED_LEVEL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllGeographicLevelsByLevelNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        // Get all the geographicLevelList where levelNumber is not null
        defaultGeographicLevelShouldBeFound("levelNumber.specified=true");

        // Get all the geographicLevelList where levelNumber is null
        defaultGeographicLevelShouldNotBeFound("levelNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllGeographicLevelsByLevelNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        // Get all the geographicLevelList where levelNumber is greater than or equal to DEFAULT_LEVEL_NUMBER
        defaultGeographicLevelShouldBeFound("levelNumber.greaterThanOrEqual=" + DEFAULT_LEVEL_NUMBER);

        // Get all the geographicLevelList where levelNumber is greater than or equal to UPDATED_LEVEL_NUMBER
        defaultGeographicLevelShouldNotBeFound("levelNumber.greaterThanOrEqual=" + UPDATED_LEVEL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllGeographicLevelsByLevelNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        // Get all the geographicLevelList where levelNumber is less than or equal to DEFAULT_LEVEL_NUMBER
        defaultGeographicLevelShouldBeFound("levelNumber.lessThanOrEqual=" + DEFAULT_LEVEL_NUMBER);

        // Get all the geographicLevelList where levelNumber is less than or equal to SMALLER_LEVEL_NUMBER
        defaultGeographicLevelShouldNotBeFound("levelNumber.lessThanOrEqual=" + SMALLER_LEVEL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllGeographicLevelsByLevelNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        // Get all the geographicLevelList where levelNumber is less than DEFAULT_LEVEL_NUMBER
        defaultGeographicLevelShouldNotBeFound("levelNumber.lessThan=" + DEFAULT_LEVEL_NUMBER);

        // Get all the geographicLevelList where levelNumber is less than UPDATED_LEVEL_NUMBER
        defaultGeographicLevelShouldBeFound("levelNumber.lessThan=" + UPDATED_LEVEL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllGeographicLevelsByLevelNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        // Get all the geographicLevelList where levelNumber is greater than DEFAULT_LEVEL_NUMBER
        defaultGeographicLevelShouldNotBeFound("levelNumber.greaterThan=" + DEFAULT_LEVEL_NUMBER);

        // Get all the geographicLevelList where levelNumber is greater than SMALLER_LEVEL_NUMBER
        defaultGeographicLevelShouldBeFound("levelNumber.greaterThan=" + SMALLER_LEVEL_NUMBER);
    }


    @Test
    @Transactional
    public void getAllGeographicLevelsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        // Get all the geographicLevelList where active equals to DEFAULT_ACTIVE
        defaultGeographicLevelShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the geographicLevelList where active equals to UPDATED_ACTIVE
        defaultGeographicLevelShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllGeographicLevelsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        // Get all the geographicLevelList where active not equals to DEFAULT_ACTIVE
        defaultGeographicLevelShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the geographicLevelList where active not equals to UPDATED_ACTIVE
        defaultGeographicLevelShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllGeographicLevelsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        // Get all the geographicLevelList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultGeographicLevelShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the geographicLevelList where active equals to UPDATED_ACTIVE
        defaultGeographicLevelShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllGeographicLevelsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        // Get all the geographicLevelList where active is not null
        defaultGeographicLevelShouldBeFound("active.specified=true");

        // Get all the geographicLevelList where active is null
        defaultGeographicLevelShouldNotBeFound("active.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGeographicLevelShouldBeFound(String filter) throws Exception {
        restGeographicLevelMockMvc.perform(get("/api/geographic-levels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geographicLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].levelNumber").value(hasItem(DEFAULT_LEVEL_NUMBER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restGeographicLevelMockMvc.perform(get("/api/geographic-levels/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGeographicLevelShouldNotBeFound(String filter) throws Exception {
        restGeographicLevelMockMvc.perform(get("/api/geographic-levels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGeographicLevelMockMvc.perform(get("/api/geographic-levels/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingGeographicLevel() throws Exception {
        // Get the geographicLevel
        restGeographicLevelMockMvc.perform(get("/api/geographic-levels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGeographicLevel() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        int databaseSizeBeforeUpdate = geographicLevelRepository.findAll().size();

        // Update the geographicLevel
        GeographicLevel updatedGeographicLevel = geographicLevelRepository.findById(geographicLevel.getId()).get();
        // Disconnect from session so that the updates on updatedGeographicLevel are not directly saved in db
        em.detach(updatedGeographicLevel);
        updatedGeographicLevel
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .levelNumber(UPDATED_LEVEL_NUMBER)
            .active(UPDATED_ACTIVE);
        GeographicLevelDTO geographicLevelDTO = geographicLevelMapper.toDto(updatedGeographicLevel);

        restGeographicLevelMockMvc.perform(put("/api/geographic-levels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geographicLevelDTO)))
            .andExpect(status().isOk());

        // Validate the GeographicLevel in the database
        List<GeographicLevel> geographicLevelList = geographicLevelRepository.findAll();
        assertThat(geographicLevelList).hasSize(databaseSizeBeforeUpdate);
        GeographicLevel testGeographicLevel = geographicLevelList.get(geographicLevelList.size() - 1);
        assertThat(testGeographicLevel.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testGeographicLevel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGeographicLevel.getLevelNumber()).isEqualTo(UPDATED_LEVEL_NUMBER);
        assertThat(testGeographicLevel.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingGeographicLevel() throws Exception {
        int databaseSizeBeforeUpdate = geographicLevelRepository.findAll().size();

        // Create the GeographicLevel
        GeographicLevelDTO geographicLevelDTO = geographicLevelMapper.toDto(geographicLevel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeographicLevelMockMvc.perform(put("/api/geographic-levels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geographicLevelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GeographicLevel in the database
        List<GeographicLevel> geographicLevelList = geographicLevelRepository.findAll();
        assertThat(geographicLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGeographicLevel() throws Exception {
        // Initialize the database
        geographicLevelRepository.saveAndFlush(geographicLevel);

        int databaseSizeBeforeDelete = geographicLevelRepository.findAll().size();

        // Delete the geographicLevel
        restGeographicLevelMockMvc.perform(delete("/api/geographic-levels/{id}", geographicLevel.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GeographicLevel> geographicLevelList = geographicLevelRepository.findAll();
        assertThat(geographicLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
