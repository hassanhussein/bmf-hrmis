package tz.or.mkapafoundation.hrmis.web.rest;

import tz.or.mkapafoundation.hrmis.HrmisApp;
import tz.or.mkapafoundation.hrmis.config.TestSecurityConfiguration;
import tz.or.mkapafoundation.hrmis.domain.EmploymentCategory;
import tz.or.mkapafoundation.hrmis.repository.EmploymentCategoryRepository;
import tz.or.mkapafoundation.hrmis.service.EmploymentCategoryService;
import tz.or.mkapafoundation.hrmis.service.dto.EmploymentCategoryDTO;
import tz.or.mkapafoundation.hrmis.service.mapper.EmploymentCategoryMapper;

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
 * Integration tests for the {@link EmploymentCategoryResource} REST controller.
 */
@SpringBootTest(classes = { HrmisApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class EmploymentCategoryResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private EmploymentCategoryRepository employmentCategoryRepository;

    @Autowired
    private EmploymentCategoryMapper employmentCategoryMapper;

    @Autowired
    private EmploymentCategoryService employmentCategoryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmploymentCategoryMockMvc;

    private EmploymentCategory employmentCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmploymentCategory createEntity(EntityManager em) {
        EmploymentCategory employmentCategory = new EmploymentCategory()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME);
        return employmentCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmploymentCategory createUpdatedEntity(EntityManager em) {
        EmploymentCategory employmentCategory = new EmploymentCategory()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);
        return employmentCategory;
    }

    @BeforeEach
    public void initTest() {
        employmentCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmploymentCategory() throws Exception {
        int databaseSizeBeforeCreate = employmentCategoryRepository.findAll().size();
        // Create the EmploymentCategory
        EmploymentCategoryDTO employmentCategoryDTO = employmentCategoryMapper.toDto(employmentCategory);
        restEmploymentCategoryMockMvc.perform(post("/api/employment-categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employmentCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the EmploymentCategory in the database
        List<EmploymentCategory> employmentCategoryList = employmentCategoryRepository.findAll();
        assertThat(employmentCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        EmploymentCategory testEmploymentCategory = employmentCategoryList.get(employmentCategoryList.size() - 1);
        assertThat(testEmploymentCategory.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEmploymentCategory.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createEmploymentCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employmentCategoryRepository.findAll().size();

        // Create the EmploymentCategory with an existing ID
        employmentCategory.setId(1L);
        EmploymentCategoryDTO employmentCategoryDTO = employmentCategoryMapper.toDto(employmentCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmploymentCategoryMockMvc.perform(post("/api/employment-categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employmentCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmploymentCategory in the database
        List<EmploymentCategory> employmentCategoryList = employmentCategoryRepository.findAll();
        assertThat(employmentCategoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employmentCategoryRepository.findAll().size();
        // set the field null
        employmentCategory.setName(null);

        // Create the EmploymentCategory, which fails.
        EmploymentCategoryDTO employmentCategoryDTO = employmentCategoryMapper.toDto(employmentCategory);


        restEmploymentCategoryMockMvc.perform(post("/api/employment-categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employmentCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<EmploymentCategory> employmentCategoryList = employmentCategoryRepository.findAll();
        assertThat(employmentCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmploymentCategories() throws Exception {
        // Initialize the database
        employmentCategoryRepository.saveAndFlush(employmentCategory);

        // Get all the employmentCategoryList
        restEmploymentCategoryMockMvc.perform(get("/api/employment-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employmentCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getEmploymentCategory() throws Exception {
        // Initialize the database
        employmentCategoryRepository.saveAndFlush(employmentCategory);

        // Get the employmentCategory
        restEmploymentCategoryMockMvc.perform(get("/api/employment-categories/{id}", employmentCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employmentCategory.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingEmploymentCategory() throws Exception {
        // Get the employmentCategory
        restEmploymentCategoryMockMvc.perform(get("/api/employment-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmploymentCategory() throws Exception {
        // Initialize the database
        employmentCategoryRepository.saveAndFlush(employmentCategory);

        int databaseSizeBeforeUpdate = employmentCategoryRepository.findAll().size();

        // Update the employmentCategory
        EmploymentCategory updatedEmploymentCategory = employmentCategoryRepository.findById(employmentCategory.getId()).get();
        // Disconnect from session so that the updates on updatedEmploymentCategory are not directly saved in db
        em.detach(updatedEmploymentCategory);
        updatedEmploymentCategory
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);
        EmploymentCategoryDTO employmentCategoryDTO = employmentCategoryMapper.toDto(updatedEmploymentCategory);

        restEmploymentCategoryMockMvc.perform(put("/api/employment-categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employmentCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the EmploymentCategory in the database
        List<EmploymentCategory> employmentCategoryList = employmentCategoryRepository.findAll();
        assertThat(employmentCategoryList).hasSize(databaseSizeBeforeUpdate);
        EmploymentCategory testEmploymentCategory = employmentCategoryList.get(employmentCategoryList.size() - 1);
        assertThat(testEmploymentCategory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEmploymentCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingEmploymentCategory() throws Exception {
        int databaseSizeBeforeUpdate = employmentCategoryRepository.findAll().size();

        // Create the EmploymentCategory
        EmploymentCategoryDTO employmentCategoryDTO = employmentCategoryMapper.toDto(employmentCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmploymentCategoryMockMvc.perform(put("/api/employment-categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employmentCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmploymentCategory in the database
        List<EmploymentCategory> employmentCategoryList = employmentCategoryRepository.findAll();
        assertThat(employmentCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmploymentCategory() throws Exception {
        // Initialize the database
        employmentCategoryRepository.saveAndFlush(employmentCategory);

        int databaseSizeBeforeDelete = employmentCategoryRepository.findAll().size();

        // Delete the employmentCategory
        restEmploymentCategoryMockMvc.perform(delete("/api/employment-categories/{id}", employmentCategory.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmploymentCategory> employmentCategoryList = employmentCategoryRepository.findAll();
        assertThat(employmentCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
