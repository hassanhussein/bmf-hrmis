package tz.or.mkapafoundation.hrmis.web.rest;

import tz.or.mkapafoundation.hrmis.HrmisApp;
import tz.or.mkapafoundation.hrmis.config.TestSecurityConfiguration;
import tz.or.mkapafoundation.hrmis.domain.EmployeeConfirmation;
import tz.or.mkapafoundation.hrmis.repository.EmployeeConfirmationRepository;
import tz.or.mkapafoundation.hrmis.service.EmployeeConfirmationService;
import tz.or.mkapafoundation.hrmis.service.dto.EmployeeConfirmationDTO;
import tz.or.mkapafoundation.hrmis.service.mapper.EmployeeConfirmationMapper;

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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EmployeeConfirmationResource} REST controller.
 */
@SpringBootTest(classes = { HrmisApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class EmployeeConfirmationResourceIT {

    private static final Boolean DEFAULT_IS_CONFIRMED = false;
    private static final Boolean UPDATED_IS_CONFIRMED = true;

    private static final String DEFAULT_CONFIRMATION_LETTER = "AAAAAAAAAA";
    private static final String UPDATED_CONFIRMATION_LETTER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private EmployeeConfirmationRepository employeeConfirmationRepository;

    @Autowired
    private EmployeeConfirmationMapper employeeConfirmationMapper;

    @Autowired
    private EmployeeConfirmationService employeeConfirmationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeConfirmationMockMvc;

    private EmployeeConfirmation employeeConfirmation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeConfirmation createEntity(EntityManager em) {
        EmployeeConfirmation employeeConfirmation = new EmployeeConfirmation()
            .isConfirmed(DEFAULT_IS_CONFIRMED)
            .confirmationLetter(DEFAULT_CONFIRMATION_LETTER)
            .date(DEFAULT_DATE);
        return employeeConfirmation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeConfirmation createUpdatedEntity(EntityManager em) {
        EmployeeConfirmation employeeConfirmation = new EmployeeConfirmation()
            .isConfirmed(UPDATED_IS_CONFIRMED)
            .confirmationLetter(UPDATED_CONFIRMATION_LETTER)
            .date(UPDATED_DATE);
        return employeeConfirmation;
    }

    @BeforeEach
    public void initTest() {
        employeeConfirmation = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeConfirmation() throws Exception {
        int databaseSizeBeforeCreate = employeeConfirmationRepository.findAll().size();
        // Create the EmployeeConfirmation
        EmployeeConfirmationDTO employeeConfirmationDTO = employeeConfirmationMapper.toDto(employeeConfirmation);
        restEmployeeConfirmationMockMvc.perform(post("/api/employee-confirmations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeConfirmationDTO)))
            .andExpect(status().isCreated());

        // Validate the EmployeeConfirmation in the database
        List<EmployeeConfirmation> employeeConfirmationList = employeeConfirmationRepository.findAll();
        assertThat(employeeConfirmationList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeConfirmation testEmployeeConfirmation = employeeConfirmationList.get(employeeConfirmationList.size() - 1);
        assertThat(testEmployeeConfirmation.isIsConfirmed()).isEqualTo(DEFAULT_IS_CONFIRMED);
        assertThat(testEmployeeConfirmation.getConfirmationLetter()).isEqualTo(DEFAULT_CONFIRMATION_LETTER);
        assertThat(testEmployeeConfirmation.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createEmployeeConfirmationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeConfirmationRepository.findAll().size();

        // Create the EmployeeConfirmation with an existing ID
        employeeConfirmation.setId(1L);
        EmployeeConfirmationDTO employeeConfirmationDTO = employeeConfirmationMapper.toDto(employeeConfirmation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeConfirmationMockMvc.perform(post("/api/employee-confirmations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeConfirmationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeConfirmation in the database
        List<EmployeeConfirmation> employeeConfirmationList = employeeConfirmationRepository.findAll();
        assertThat(employeeConfirmationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmployeeConfirmations() throws Exception {
        // Initialize the database
        employeeConfirmationRepository.saveAndFlush(employeeConfirmation);

        // Get all the employeeConfirmationList
        restEmployeeConfirmationMockMvc.perform(get("/api/employee-confirmations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeConfirmation.getId().intValue())))
            .andExpect(jsonPath("$.[*].isConfirmed").value(hasItem(DEFAULT_IS_CONFIRMED.booleanValue())))
            .andExpect(jsonPath("$.[*].confirmationLetter").value(hasItem(DEFAULT_CONFIRMATION_LETTER)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getEmployeeConfirmation() throws Exception {
        // Initialize the database
        employeeConfirmationRepository.saveAndFlush(employeeConfirmation);

        // Get the employeeConfirmation
        restEmployeeConfirmationMockMvc.perform(get("/api/employee-confirmations/{id}", employeeConfirmation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeConfirmation.getId().intValue()))
            .andExpect(jsonPath("$.isConfirmed").value(DEFAULT_IS_CONFIRMED.booleanValue()))
            .andExpect(jsonPath("$.confirmationLetter").value(DEFAULT_CONFIRMATION_LETTER))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEmployeeConfirmation() throws Exception {
        // Get the employeeConfirmation
        restEmployeeConfirmationMockMvc.perform(get("/api/employee-confirmations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeConfirmation() throws Exception {
        // Initialize the database
        employeeConfirmationRepository.saveAndFlush(employeeConfirmation);

        int databaseSizeBeforeUpdate = employeeConfirmationRepository.findAll().size();

        // Update the employeeConfirmation
        EmployeeConfirmation updatedEmployeeConfirmation = employeeConfirmationRepository.findById(employeeConfirmation.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeConfirmation are not directly saved in db
        em.detach(updatedEmployeeConfirmation);
        updatedEmployeeConfirmation
            .isConfirmed(UPDATED_IS_CONFIRMED)
            .confirmationLetter(UPDATED_CONFIRMATION_LETTER)
            .date(UPDATED_DATE);
        EmployeeConfirmationDTO employeeConfirmationDTO = employeeConfirmationMapper.toDto(updatedEmployeeConfirmation);

        restEmployeeConfirmationMockMvc.perform(put("/api/employee-confirmations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeConfirmationDTO)))
            .andExpect(status().isOk());

        // Validate the EmployeeConfirmation in the database
        List<EmployeeConfirmation> employeeConfirmationList = employeeConfirmationRepository.findAll();
        assertThat(employeeConfirmationList).hasSize(databaseSizeBeforeUpdate);
        EmployeeConfirmation testEmployeeConfirmation = employeeConfirmationList.get(employeeConfirmationList.size() - 1);
        assertThat(testEmployeeConfirmation.isIsConfirmed()).isEqualTo(UPDATED_IS_CONFIRMED);
        assertThat(testEmployeeConfirmation.getConfirmationLetter()).isEqualTo(UPDATED_CONFIRMATION_LETTER);
        assertThat(testEmployeeConfirmation.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeConfirmation() throws Exception {
        int databaseSizeBeforeUpdate = employeeConfirmationRepository.findAll().size();

        // Create the EmployeeConfirmation
        EmployeeConfirmationDTO employeeConfirmationDTO = employeeConfirmationMapper.toDto(employeeConfirmation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeConfirmationMockMvc.perform(put("/api/employee-confirmations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeConfirmationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeConfirmation in the database
        List<EmployeeConfirmation> employeeConfirmationList = employeeConfirmationRepository.findAll();
        assertThat(employeeConfirmationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeConfirmation() throws Exception {
        // Initialize the database
        employeeConfirmationRepository.saveAndFlush(employeeConfirmation);

        int databaseSizeBeforeDelete = employeeConfirmationRepository.findAll().size();

        // Delete the employeeConfirmation
        restEmployeeConfirmationMockMvc.perform(delete("/api/employee-confirmations/{id}", employeeConfirmation.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeConfirmation> employeeConfirmationList = employeeConfirmationRepository.findAll();
        assertThat(employeeConfirmationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
