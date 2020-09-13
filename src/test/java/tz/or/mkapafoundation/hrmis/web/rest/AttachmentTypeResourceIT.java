package tz.or.mkapafoundation.hrmis.web.rest;

import tz.or.mkapafoundation.hrmis.HrmisApp;
import tz.or.mkapafoundation.hrmis.config.TestSecurityConfiguration;
import tz.or.mkapafoundation.hrmis.domain.AttachmentType;
import tz.or.mkapafoundation.hrmis.repository.AttachmentTypeRepository;
import tz.or.mkapafoundation.hrmis.service.AttachmentTypeService;
import tz.or.mkapafoundation.hrmis.service.dto.AttachmentTypeDTO;
import tz.or.mkapafoundation.hrmis.service.mapper.AttachmentTypeMapper;
import tz.or.mkapafoundation.hrmis.service.dto.AttachmentTypeCriteria;
import tz.or.mkapafoundation.hrmis.service.AttachmentTypeQueryService;

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
 * Integration tests for the {@link AttachmentTypeResource} REST controller.
 */
@SpringBootTest(classes = { HrmisApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class AttachmentTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private AttachmentTypeRepository attachmentTypeRepository;

    @Autowired
    private AttachmentTypeMapper attachmentTypeMapper;

    @Autowired
    private AttachmentTypeService attachmentTypeService;

    @Autowired
    private AttachmentTypeQueryService attachmentTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttachmentTypeMockMvc;

    private AttachmentType attachmentType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttachmentType createEntity(EntityManager em) {
        AttachmentType attachmentType = new AttachmentType()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME);
        return attachmentType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttachmentType createUpdatedEntity(EntityManager em) {
        AttachmentType attachmentType = new AttachmentType()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);
        return attachmentType;
    }

    @BeforeEach
    public void initTest() {
        attachmentType = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttachmentType() throws Exception {
        int databaseSizeBeforeCreate = attachmentTypeRepository.findAll().size();
        // Create the AttachmentType
        AttachmentTypeDTO attachmentTypeDTO = attachmentTypeMapper.toDto(attachmentType);
        restAttachmentTypeMockMvc.perform(post("/api/attachment-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the AttachmentType in the database
        List<AttachmentType> attachmentTypeList = attachmentTypeRepository.findAll();
        assertThat(attachmentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AttachmentType testAttachmentType = attachmentTypeList.get(attachmentTypeList.size() - 1);
        assertThat(testAttachmentType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAttachmentType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createAttachmentTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attachmentTypeRepository.findAll().size();

        // Create the AttachmentType with an existing ID
        attachmentType.setId(1L);
        AttachmentTypeDTO attachmentTypeDTO = attachmentTypeMapper.toDto(attachmentType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttachmentTypeMockMvc.perform(post("/api/attachment-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AttachmentType in the database
        List<AttachmentType> attachmentTypeList = attachmentTypeRepository.findAll();
        assertThat(attachmentTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = attachmentTypeRepository.findAll().size();
        // set the field null
        attachmentType.setName(null);

        // Create the AttachmentType, which fails.
        AttachmentTypeDTO attachmentTypeDTO = attachmentTypeMapper.toDto(attachmentType);


        restAttachmentTypeMockMvc.perform(post("/api/attachment-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentTypeDTO)))
            .andExpect(status().isBadRequest());

        List<AttachmentType> attachmentTypeList = attachmentTypeRepository.findAll();
        assertThat(attachmentTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAttachmentTypes() throws Exception {
        // Initialize the database
        attachmentTypeRepository.saveAndFlush(attachmentType);

        // Get all the attachmentTypeList
        restAttachmentTypeMockMvc.perform(get("/api/attachment-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attachmentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getAttachmentType() throws Exception {
        // Initialize the database
        attachmentTypeRepository.saveAndFlush(attachmentType);

        // Get the attachmentType
        restAttachmentTypeMockMvc.perform(get("/api/attachment-types/{id}", attachmentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attachmentType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getAttachmentTypesByIdFiltering() throws Exception {
        // Initialize the database
        attachmentTypeRepository.saveAndFlush(attachmentType);

        Long id = attachmentType.getId();

        defaultAttachmentTypeShouldBeFound("id.equals=" + id);
        defaultAttachmentTypeShouldNotBeFound("id.notEquals=" + id);

        defaultAttachmentTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAttachmentTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultAttachmentTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAttachmentTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAttachmentTypesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        attachmentTypeRepository.saveAndFlush(attachmentType);

        // Get all the attachmentTypeList where code equals to DEFAULT_CODE
        defaultAttachmentTypeShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the attachmentTypeList where code equals to UPDATED_CODE
        defaultAttachmentTypeShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllAttachmentTypesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attachmentTypeRepository.saveAndFlush(attachmentType);

        // Get all the attachmentTypeList where code not equals to DEFAULT_CODE
        defaultAttachmentTypeShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the attachmentTypeList where code not equals to UPDATED_CODE
        defaultAttachmentTypeShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllAttachmentTypesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        attachmentTypeRepository.saveAndFlush(attachmentType);

        // Get all the attachmentTypeList where code in DEFAULT_CODE or UPDATED_CODE
        defaultAttachmentTypeShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the attachmentTypeList where code equals to UPDATED_CODE
        defaultAttachmentTypeShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllAttachmentTypesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        attachmentTypeRepository.saveAndFlush(attachmentType);

        // Get all the attachmentTypeList where code is not null
        defaultAttachmentTypeShouldBeFound("code.specified=true");

        // Get all the attachmentTypeList where code is null
        defaultAttachmentTypeShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllAttachmentTypesByCodeContainsSomething() throws Exception {
        // Initialize the database
        attachmentTypeRepository.saveAndFlush(attachmentType);

        // Get all the attachmentTypeList where code contains DEFAULT_CODE
        defaultAttachmentTypeShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the attachmentTypeList where code contains UPDATED_CODE
        defaultAttachmentTypeShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllAttachmentTypesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        attachmentTypeRepository.saveAndFlush(attachmentType);

        // Get all the attachmentTypeList where code does not contain DEFAULT_CODE
        defaultAttachmentTypeShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the attachmentTypeList where code does not contain UPDATED_CODE
        defaultAttachmentTypeShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllAttachmentTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        attachmentTypeRepository.saveAndFlush(attachmentType);

        // Get all the attachmentTypeList where name equals to DEFAULT_NAME
        defaultAttachmentTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the attachmentTypeList where name equals to UPDATED_NAME
        defaultAttachmentTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAttachmentTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attachmentTypeRepository.saveAndFlush(attachmentType);

        // Get all the attachmentTypeList where name not equals to DEFAULT_NAME
        defaultAttachmentTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the attachmentTypeList where name not equals to UPDATED_NAME
        defaultAttachmentTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAttachmentTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        attachmentTypeRepository.saveAndFlush(attachmentType);

        // Get all the attachmentTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAttachmentTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the attachmentTypeList where name equals to UPDATED_NAME
        defaultAttachmentTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAttachmentTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        attachmentTypeRepository.saveAndFlush(attachmentType);

        // Get all the attachmentTypeList where name is not null
        defaultAttachmentTypeShouldBeFound("name.specified=true");

        // Get all the attachmentTypeList where name is null
        defaultAttachmentTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAttachmentTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        attachmentTypeRepository.saveAndFlush(attachmentType);

        // Get all the attachmentTypeList where name contains DEFAULT_NAME
        defaultAttachmentTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the attachmentTypeList where name contains UPDATED_NAME
        defaultAttachmentTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAttachmentTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        attachmentTypeRepository.saveAndFlush(attachmentType);

        // Get all the attachmentTypeList where name does not contain DEFAULT_NAME
        defaultAttachmentTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the attachmentTypeList where name does not contain UPDATED_NAME
        defaultAttachmentTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAttachmentTypeShouldBeFound(String filter) throws Exception {
        restAttachmentTypeMockMvc.perform(get("/api/attachment-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attachmentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restAttachmentTypeMockMvc.perform(get("/api/attachment-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAttachmentTypeShouldNotBeFound(String filter) throws Exception {
        restAttachmentTypeMockMvc.perform(get("/api/attachment-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAttachmentTypeMockMvc.perform(get("/api/attachment-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAttachmentType() throws Exception {
        // Get the attachmentType
        restAttachmentTypeMockMvc.perform(get("/api/attachment-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttachmentType() throws Exception {
        // Initialize the database
        attachmentTypeRepository.saveAndFlush(attachmentType);

        int databaseSizeBeforeUpdate = attachmentTypeRepository.findAll().size();

        // Update the attachmentType
        AttachmentType updatedAttachmentType = attachmentTypeRepository.findById(attachmentType.getId()).get();
        // Disconnect from session so that the updates on updatedAttachmentType are not directly saved in db
        em.detach(updatedAttachmentType);
        updatedAttachmentType
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);
        AttachmentTypeDTO attachmentTypeDTO = attachmentTypeMapper.toDto(updatedAttachmentType);

        restAttachmentTypeMockMvc.perform(put("/api/attachment-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentTypeDTO)))
            .andExpect(status().isOk());

        // Validate the AttachmentType in the database
        List<AttachmentType> attachmentTypeList = attachmentTypeRepository.findAll();
        assertThat(attachmentTypeList).hasSize(databaseSizeBeforeUpdate);
        AttachmentType testAttachmentType = attachmentTypeList.get(attachmentTypeList.size() - 1);
        assertThat(testAttachmentType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAttachmentType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingAttachmentType() throws Exception {
        int databaseSizeBeforeUpdate = attachmentTypeRepository.findAll().size();

        // Create the AttachmentType
        AttachmentTypeDTO attachmentTypeDTO = attachmentTypeMapper.toDto(attachmentType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttachmentTypeMockMvc.perform(put("/api/attachment-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AttachmentType in the database
        List<AttachmentType> attachmentTypeList = attachmentTypeRepository.findAll();
        assertThat(attachmentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAttachmentType() throws Exception {
        // Initialize the database
        attachmentTypeRepository.saveAndFlush(attachmentType);

        int databaseSizeBeforeDelete = attachmentTypeRepository.findAll().size();

        // Delete the attachmentType
        restAttachmentTypeMockMvc.perform(delete("/api/attachment-types/{id}", attachmentType.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttachmentType> attachmentTypeList = attachmentTypeRepository.findAll();
        assertThat(attachmentTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
