package tz.or.mkapafoundation.hrmis.web.rest;

import tz.or.mkapafoundation.hrmis.HrmisApp;
import tz.or.mkapafoundation.hrmis.config.TestSecurityConfiguration;
import tz.or.mkapafoundation.hrmis.domain.Carder;
import tz.or.mkapafoundation.hrmis.repository.CarderRepository;
import tz.or.mkapafoundation.hrmis.service.CarderService;
import tz.or.mkapafoundation.hrmis.service.dto.CarderDTO;
import tz.or.mkapafoundation.hrmis.service.mapper.CarderMapper;

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
