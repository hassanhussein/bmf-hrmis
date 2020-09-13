package tz.or.mkapafoundation.hrmis.web.rest;

import tz.or.mkapafoundation.hrmis.HrmisApp;
import tz.or.mkapafoundation.hrmis.config.TestSecurityConfiguration;
import tz.or.mkapafoundation.hrmis.domain.GeographicZone;
import tz.or.mkapafoundation.hrmis.domain.GeographicLevel;
import tz.or.mkapafoundation.hrmis.repository.GeographicZoneRepository;
import tz.or.mkapafoundation.hrmis.service.GeographicZoneService;
import tz.or.mkapafoundation.hrmis.service.dto.GeographicZoneDTO;
import tz.or.mkapafoundation.hrmis.service.mapper.GeographicZoneMapper;

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
 * Integration tests for the {@link GeographicZoneResource} REST controller.
 */
@SpringBootTest(classes = { HrmisApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class GeographicZoneResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Float DEFAULT_LATITUDE = 1F;
    private static final Float UPDATED_LATITUDE = 2F;

    private static final Float DEFAULT_LONGITUDE = 1F;
    private static final Float UPDATED_LONGITUDE = 2F;

    @Autowired
    private GeographicZoneRepository geographicZoneRepository;

    @Autowired
    private GeographicZoneMapper geographicZoneMapper;

    @Autowired
    private GeographicZoneService geographicZoneService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGeographicZoneMockMvc;

    private GeographicZone geographicZone;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GeographicZone createEntity(EntityManager em) {
        GeographicZone geographicZone = new GeographicZone()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE);
        // Add required entity
        GeographicLevel geographicLevel;
        if (TestUtil.findAll(em, GeographicLevel.class).isEmpty()) {
            geographicLevel = GeographicLevelResourceIT.createEntity(em);
            em.persist(geographicLevel);
            em.flush();
        } else {
            geographicLevel = TestUtil.findAll(em, GeographicLevel.class).get(0);
        }
        geographicZone.setLevel(geographicLevel);
        return geographicZone;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GeographicZone createUpdatedEntity(EntityManager em) {
        GeographicZone geographicZone = new GeographicZone()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
        // Add required entity
        GeographicLevel geographicLevel;
        if (TestUtil.findAll(em, GeographicLevel.class).isEmpty()) {
            geographicLevel = GeographicLevelResourceIT.createUpdatedEntity(em);
            em.persist(geographicLevel);
            em.flush();
        } else {
            geographicLevel = TestUtil.findAll(em, GeographicLevel.class).get(0);
        }
        geographicZone.setLevel(geographicLevel);
        return geographicZone;
    }

    @BeforeEach
    public void initTest() {
        geographicZone = createEntity(em);
    }

    @Test
    @Transactional
    public void createGeographicZone() throws Exception {
        int databaseSizeBeforeCreate = geographicZoneRepository.findAll().size();
        // Create the GeographicZone
        GeographicZoneDTO geographicZoneDTO = geographicZoneMapper.toDto(geographicZone);
        restGeographicZoneMockMvc.perform(post("/api/geographic-zones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geographicZoneDTO)))
            .andExpect(status().isCreated());

        // Validate the GeographicZone in the database
        List<GeographicZone> geographicZoneList = geographicZoneRepository.findAll();
        assertThat(geographicZoneList).hasSize(databaseSizeBeforeCreate + 1);
        GeographicZone testGeographicZone = geographicZoneList.get(geographicZoneList.size() - 1);
        assertThat(testGeographicZone.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testGeographicZone.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGeographicZone.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testGeographicZone.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
    }

    @Test
    @Transactional
    public void createGeographicZoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = geographicZoneRepository.findAll().size();

        // Create the GeographicZone with an existing ID
        geographicZone.setId(1L);
        GeographicZoneDTO geographicZoneDTO = geographicZoneMapper.toDto(geographicZone);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeographicZoneMockMvc.perform(post("/api/geographic-zones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geographicZoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GeographicZone in the database
        List<GeographicZone> geographicZoneList = geographicZoneRepository.findAll();
        assertThat(geographicZoneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = geographicZoneRepository.findAll().size();
        // set the field null
        geographicZone.setName(null);

        // Create the GeographicZone, which fails.
        GeographicZoneDTO geographicZoneDTO = geographicZoneMapper.toDto(geographicZone);


        restGeographicZoneMockMvc.perform(post("/api/geographic-zones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geographicZoneDTO)))
            .andExpect(status().isBadRequest());

        List<GeographicZone> geographicZoneList = geographicZoneRepository.findAll();
        assertThat(geographicZoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGeographicZones() throws Exception {
        // Initialize the database
        geographicZoneRepository.saveAndFlush(geographicZone);

        // Get all the geographicZoneList
        restGeographicZoneMockMvc.perform(get("/api/geographic-zones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geographicZone.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getGeographicZone() throws Exception {
        // Initialize the database
        geographicZoneRepository.saveAndFlush(geographicZone);

        // Get the geographicZone
        restGeographicZoneMockMvc.perform(get("/api/geographic-zones/{id}", geographicZone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(geographicZone.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingGeographicZone() throws Exception {
        // Get the geographicZone
        restGeographicZoneMockMvc.perform(get("/api/geographic-zones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGeographicZone() throws Exception {
        // Initialize the database
        geographicZoneRepository.saveAndFlush(geographicZone);

        int databaseSizeBeforeUpdate = geographicZoneRepository.findAll().size();

        // Update the geographicZone
        GeographicZone updatedGeographicZone = geographicZoneRepository.findById(geographicZone.getId()).get();
        // Disconnect from session so that the updates on updatedGeographicZone are not directly saved in db
        em.detach(updatedGeographicZone);
        updatedGeographicZone
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
        GeographicZoneDTO geographicZoneDTO = geographicZoneMapper.toDto(updatedGeographicZone);

        restGeographicZoneMockMvc.perform(put("/api/geographic-zones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geographicZoneDTO)))
            .andExpect(status().isOk());

        // Validate the GeographicZone in the database
        List<GeographicZone> geographicZoneList = geographicZoneRepository.findAll();
        assertThat(geographicZoneList).hasSize(databaseSizeBeforeUpdate);
        GeographicZone testGeographicZone = geographicZoneList.get(geographicZoneList.size() - 1);
        assertThat(testGeographicZone.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testGeographicZone.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGeographicZone.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testGeographicZone.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void updateNonExistingGeographicZone() throws Exception {
        int databaseSizeBeforeUpdate = geographicZoneRepository.findAll().size();

        // Create the GeographicZone
        GeographicZoneDTO geographicZoneDTO = geographicZoneMapper.toDto(geographicZone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeographicZoneMockMvc.perform(put("/api/geographic-zones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geographicZoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GeographicZone in the database
        List<GeographicZone> geographicZoneList = geographicZoneRepository.findAll();
        assertThat(geographicZoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGeographicZone() throws Exception {
        // Initialize the database
        geographicZoneRepository.saveAndFlush(geographicZone);

        int databaseSizeBeforeDelete = geographicZoneRepository.findAll().size();

        // Delete the geographicZone
        restGeographicZoneMockMvc.perform(delete("/api/geographic-zones/{id}", geographicZone.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GeographicZone> geographicZoneList = geographicZoneRepository.findAll();
        assertThat(geographicZoneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
