package tz.or.mkapafoundation.hrmis.web.rest;

import tz.or.mkapafoundation.hrmis.HrmisApp;
import tz.or.mkapafoundation.hrmis.config.TestSecurityConfiguration;
import tz.or.mkapafoundation.hrmis.domain.FacilityType;
import tz.or.mkapafoundation.hrmis.repository.FacilityTypeRepository;
import tz.or.mkapafoundation.hrmis.service.FacilityTypeService;
import tz.or.mkapafoundation.hrmis.service.dto.FacilityTypeDTO;
import tz.or.mkapafoundation.hrmis.service.mapper.FacilityTypeMapper;

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
 * Integration tests for the {@link FacilityTypeResource} REST controller.
 */
@SpringBootTest(classes = { HrmisApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class FacilityTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_DISPLAY_ORDER = 1;
    private static final Integer UPDATED_DISPLAY_ORDER = 2;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private FacilityTypeRepository facilityTypeRepository;

    @Autowired
    private FacilityTypeMapper facilityTypeMapper;

    @Autowired
    private FacilityTypeService facilityTypeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFacilityTypeMockMvc;

    private FacilityType facilityType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FacilityType createEntity(EntityManager em) {
        FacilityType facilityType = new FacilityType()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .displayOrder(DEFAULT_DISPLAY_ORDER)
            .active(DEFAULT_ACTIVE);
        return facilityType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FacilityType createUpdatedEntity(EntityManager em) {
        FacilityType facilityType = new FacilityType()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .displayOrder(UPDATED_DISPLAY_ORDER)
            .active(UPDATED_ACTIVE);
        return facilityType;
    }

    @BeforeEach
    public void initTest() {
        facilityType = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacilityType() throws Exception {
        int databaseSizeBeforeCreate = facilityTypeRepository.findAll().size();
        // Create the FacilityType
        FacilityTypeDTO facilityTypeDTO = facilityTypeMapper.toDto(facilityType);
        restFacilityTypeMockMvc.perform(post("/api/facility-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the FacilityType in the database
        List<FacilityType> facilityTypeList = facilityTypeRepository.findAll();
        assertThat(facilityTypeList).hasSize(databaseSizeBeforeCreate + 1);
        FacilityType testFacilityType = facilityTypeList.get(facilityTypeList.size() - 1);
        assertThat(testFacilityType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFacilityType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFacilityType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFacilityType.getDisplayOrder()).isEqualTo(DEFAULT_DISPLAY_ORDER);
        assertThat(testFacilityType.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createFacilityTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facilityTypeRepository.findAll().size();

        // Create the FacilityType with an existing ID
        facilityType.setId(1L);
        FacilityTypeDTO facilityTypeDTO = facilityTypeMapper.toDto(facilityType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacilityTypeMockMvc.perform(post("/api/facility-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FacilityType in the database
        List<FacilityType> facilityTypeList = facilityTypeRepository.findAll();
        assertThat(facilityTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = facilityTypeRepository.findAll().size();
        // set the field null
        facilityType.setName(null);

        // Create the FacilityType, which fails.
        FacilityTypeDTO facilityTypeDTO = facilityTypeMapper.toDto(facilityType);


        restFacilityTypeMockMvc.perform(post("/api/facility-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityTypeDTO)))
            .andExpect(status().isBadRequest());

        List<FacilityType> facilityTypeList = facilityTypeRepository.findAll();
        assertThat(facilityTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFacilityTypes() throws Exception {
        // Initialize the database
        facilityTypeRepository.saveAndFlush(facilityType);

        // Get all the facilityTypeList
        restFacilityTypeMockMvc.perform(get("/api/facility-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facilityType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].displayOrder").value(hasItem(DEFAULT_DISPLAY_ORDER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getFacilityType() throws Exception {
        // Initialize the database
        facilityTypeRepository.saveAndFlush(facilityType);

        // Get the facilityType
        restFacilityTypeMockMvc.perform(get("/api/facility-types/{id}", facilityType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(facilityType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.displayOrder").value(DEFAULT_DISPLAY_ORDER))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingFacilityType() throws Exception {
        // Get the facilityType
        restFacilityTypeMockMvc.perform(get("/api/facility-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacilityType() throws Exception {
        // Initialize the database
        facilityTypeRepository.saveAndFlush(facilityType);

        int databaseSizeBeforeUpdate = facilityTypeRepository.findAll().size();

        // Update the facilityType
        FacilityType updatedFacilityType = facilityTypeRepository.findById(facilityType.getId()).get();
        // Disconnect from session so that the updates on updatedFacilityType are not directly saved in db
        em.detach(updatedFacilityType);
        updatedFacilityType
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .displayOrder(UPDATED_DISPLAY_ORDER)
            .active(UPDATED_ACTIVE);
        FacilityTypeDTO facilityTypeDTO = facilityTypeMapper.toDto(updatedFacilityType);

        restFacilityTypeMockMvc.perform(put("/api/facility-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityTypeDTO)))
            .andExpect(status().isOk());

        // Validate the FacilityType in the database
        List<FacilityType> facilityTypeList = facilityTypeRepository.findAll();
        assertThat(facilityTypeList).hasSize(databaseSizeBeforeUpdate);
        FacilityType testFacilityType = facilityTypeList.get(facilityTypeList.size() - 1);
        assertThat(testFacilityType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFacilityType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFacilityType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFacilityType.getDisplayOrder()).isEqualTo(UPDATED_DISPLAY_ORDER);
        assertThat(testFacilityType.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingFacilityType() throws Exception {
        int databaseSizeBeforeUpdate = facilityTypeRepository.findAll().size();

        // Create the FacilityType
        FacilityTypeDTO facilityTypeDTO = facilityTypeMapper.toDto(facilityType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacilityTypeMockMvc.perform(put("/api/facility-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FacilityType in the database
        List<FacilityType> facilityTypeList = facilityTypeRepository.findAll();
        assertThat(facilityTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFacilityType() throws Exception {
        // Initialize the database
        facilityTypeRepository.saveAndFlush(facilityType);

        int databaseSizeBeforeDelete = facilityTypeRepository.findAll().size();

        // Delete the facilityType
        restFacilityTypeMockMvc.perform(delete("/api/facility-types/{id}", facilityType.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FacilityType> facilityTypeList = facilityTypeRepository.findAll();
        assertThat(facilityTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
