package tz.or.mkapafoundation.hrmis.web.rest;

import tz.or.mkapafoundation.hrmis.HrmisApp;
import tz.or.mkapafoundation.hrmis.config.TestSecurityConfiguration;
import tz.or.mkapafoundation.hrmis.domain.EmployeeRecord;
import tz.or.mkapafoundation.hrmis.domain.Attachment;
import tz.or.mkapafoundation.hrmis.domain.Department;
import tz.or.mkapafoundation.hrmis.domain.EmploymentCategory;
import tz.or.mkapafoundation.hrmis.domain.Carder;
import tz.or.mkapafoundation.hrmis.domain.Facility;
import tz.or.mkapafoundation.hrmis.domain.Project;
import tz.or.mkapafoundation.hrmis.repository.EmployeeRecordRepository;
import tz.or.mkapafoundation.hrmis.service.EmployeeRecordService;
import tz.or.mkapafoundation.hrmis.service.dto.EmployeeRecordDTO;
import tz.or.mkapafoundation.hrmis.service.mapper.EmployeeRecordMapper;
import tz.or.mkapafoundation.hrmis.service.dto.EmployeeRecordCriteria;
import tz.or.mkapafoundation.hrmis.service.EmployeeRecordQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@link EmployeeRecordResource} REST controller.
 */
@SpringBootTest(classes = { HrmisApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class EmployeeRecordResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_BIRTH_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CELL_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_CELL_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_MARITAL_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_MARITAL_STATUS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PICTURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PICTURE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_EMPLOYEE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final LocalDate DEFAULT_DATE_JOINING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_JOINING = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_JOINING = LocalDate.ofEpochDay(-1L);

    private static final Double DEFAULT_SALARY = 1D;
    private static final Double UPDATED_SALARY = 2D;
    private static final Double SMALLER_SALARY = 1D - 1D;

    private static final LocalDate DEFAULT_CONTRACT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CONTRACT_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CONTRACT_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_CONTRACT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CONTRACT_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CONTRACT_END_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ACCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_INSURANCE_REGISTRATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_INSURANCE_REGISTRATION_NUMBER = "BBBBBBBBBB";

    @Autowired
    private EmployeeRecordRepository employeeRecordRepository;

    @Autowired
    private EmployeeRecordMapper employeeRecordMapper;

    @Autowired
    private EmployeeRecordService employeeRecordService;

    @Autowired
    private EmployeeRecordQueryService employeeRecordQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeRecordMockMvc;

    private EmployeeRecord employeeRecord;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeRecord createEntity(EntityManager em) {
        EmployeeRecord employeeRecord = new EmployeeRecord()
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .address(DEFAULT_ADDRESS)
            .gender(DEFAULT_GENDER)
            .birthDate(DEFAULT_BIRTH_DATE)
            .email(DEFAULT_EMAIL)
            .cellPhone(DEFAULT_CELL_PHONE)
            .telephone(DEFAULT_TELEPHONE)
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .picture(DEFAULT_PICTURE)
            .pictureContentType(DEFAULT_PICTURE_CONTENT_TYPE)
            .employeeNumber(DEFAULT_EMPLOYEE_NUMBER)
            .active(DEFAULT_ACTIVE)
            .dateJoining(DEFAULT_DATE_JOINING)
            .salary(DEFAULT_SALARY)
            .contractStartDate(DEFAULT_CONTRACT_START_DATE)
            .contractEndDate(DEFAULT_CONTRACT_END_DATE)
            .bankName(DEFAULT_BANK_NAME)
            .branchName(DEFAULT_BRANCH_NAME)
            .bankAccount(DEFAULT_BANK_ACCOUNT)
            .insuranceRegistrationNumber(DEFAULT_INSURANCE_REGISTRATION_NUMBER);
        // Add required entity
        Department department;
        if (TestUtil.findAll(em, Department.class).isEmpty()) {
            department = DepartmentResourceIT.createEntity(em);
            em.persist(department);
            em.flush();
        } else {
            department = TestUtil.findAll(em, Department.class).get(0);
        }
        employeeRecord.setDepartment(department);
        // Add required entity
        EmploymentCategory employmentCategory;
        if (TestUtil.findAll(em, EmploymentCategory.class).isEmpty()) {
            employmentCategory = EmploymentCategoryResourceIT.createEntity(em);
            em.persist(employmentCategory);
            em.flush();
        } else {
            employmentCategory = TestUtil.findAll(em, EmploymentCategory.class).get(0);
        }
        employeeRecord.setEmployeeType(employmentCategory);
        // Add required entity
        Carder carder;
        if (TestUtil.findAll(em, Carder.class).isEmpty()) {
            carder = CarderResourceIT.createEntity(em);
            em.persist(carder);
            em.flush();
        } else {
            carder = TestUtil.findAll(em, Carder.class).get(0);
        }
        employeeRecord.setDesignation(carder);
        // Add required entity
        Facility facility;
        if (TestUtil.findAll(em, Facility.class).isEmpty()) {
            facility = FacilityResourceIT.createEntity(em);
            em.persist(facility);
            em.flush();
        } else {
            facility = TestUtil.findAll(em, Facility.class).get(0);
        }
        employeeRecord.setFacility(facility);
        // Add required entity
        Project project;
        if (TestUtil.findAll(em, Project.class).isEmpty()) {
            project = ProjectResourceIT.createEntity(em);
            em.persist(project);
            em.flush();
        } else {
            project = TestUtil.findAll(em, Project.class).get(0);
        }
        employeeRecord.setProject(project);
        return employeeRecord;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeRecord createUpdatedEntity(EntityManager em) {
        EmployeeRecord employeeRecord = new EmployeeRecord()
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address(UPDATED_ADDRESS)
            .gender(UPDATED_GENDER)
            .birthDate(UPDATED_BIRTH_DATE)
            .email(UPDATED_EMAIL)
            .cellPhone(UPDATED_CELL_PHONE)
            .telephone(UPDATED_TELEPHONE)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE)
            .employeeNumber(UPDATED_EMPLOYEE_NUMBER)
            .active(UPDATED_ACTIVE)
            .dateJoining(UPDATED_DATE_JOINING)
            .salary(UPDATED_SALARY)
            .contractStartDate(UPDATED_CONTRACT_START_DATE)
            .contractEndDate(UPDATED_CONTRACT_END_DATE)
            .bankName(UPDATED_BANK_NAME)
            .branchName(UPDATED_BRANCH_NAME)
            .bankAccount(UPDATED_BANK_ACCOUNT)
            .insuranceRegistrationNumber(UPDATED_INSURANCE_REGISTRATION_NUMBER);
        // Add required entity
        Department department;
        if (TestUtil.findAll(em, Department.class).isEmpty()) {
            department = DepartmentResourceIT.createUpdatedEntity(em);
            em.persist(department);
            em.flush();
        } else {
            department = TestUtil.findAll(em, Department.class).get(0);
        }
        employeeRecord.setDepartment(department);
        // Add required entity
        EmploymentCategory employmentCategory;
        if (TestUtil.findAll(em, EmploymentCategory.class).isEmpty()) {
            employmentCategory = EmploymentCategoryResourceIT.createUpdatedEntity(em);
            em.persist(employmentCategory);
            em.flush();
        } else {
            employmentCategory = TestUtil.findAll(em, EmploymentCategory.class).get(0);
        }
        employeeRecord.setEmployeeType(employmentCategory);
        // Add required entity
        Carder carder;
        if (TestUtil.findAll(em, Carder.class).isEmpty()) {
            carder = CarderResourceIT.createUpdatedEntity(em);
            em.persist(carder);
            em.flush();
        } else {
            carder = TestUtil.findAll(em, Carder.class).get(0);
        }
        employeeRecord.setDesignation(carder);
        // Add required entity
        Facility facility;
        if (TestUtil.findAll(em, Facility.class).isEmpty()) {
            facility = FacilityResourceIT.createUpdatedEntity(em);
            em.persist(facility);
            em.flush();
        } else {
            facility = TestUtil.findAll(em, Facility.class).get(0);
        }
        employeeRecord.setFacility(facility);
        // Add required entity
        Project project;
        if (TestUtil.findAll(em, Project.class).isEmpty()) {
            project = ProjectResourceIT.createUpdatedEntity(em);
            em.persist(project);
            em.flush();
        } else {
            project = TestUtil.findAll(em, Project.class).get(0);
        }
        employeeRecord.setProject(project);
        return employeeRecord;
    }

    @BeforeEach
    public void initTest() {
        employeeRecord = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeRecord() throws Exception {
        int databaseSizeBeforeCreate = employeeRecordRepository.findAll().size();
        // Create the EmployeeRecord
        EmployeeRecordDTO employeeRecordDTO = employeeRecordMapper.toDto(employeeRecord);
        restEmployeeRecordMockMvc.perform(post("/api/employee-records").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeRecordDTO)))
            .andExpect(status().isCreated());

        // Validate the EmployeeRecord in the database
        List<EmployeeRecord> employeeRecordList = employeeRecordRepository.findAll();
        assertThat(employeeRecordList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeRecord testEmployeeRecord = employeeRecordList.get(employeeRecordList.size() - 1);
        assertThat(testEmployeeRecord.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testEmployeeRecord.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testEmployeeRecord.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testEmployeeRecord.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testEmployeeRecord.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testEmployeeRecord.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testEmployeeRecord.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmployeeRecord.getCellPhone()).isEqualTo(DEFAULT_CELL_PHONE);
        assertThat(testEmployeeRecord.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testEmployeeRecord.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testEmployeeRecord.getPicture()).isEqualTo(DEFAULT_PICTURE);
        assertThat(testEmployeeRecord.getPictureContentType()).isEqualTo(DEFAULT_PICTURE_CONTENT_TYPE);
        assertThat(testEmployeeRecord.getEmployeeNumber()).isEqualTo(DEFAULT_EMPLOYEE_NUMBER);
        assertThat(testEmployeeRecord.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testEmployeeRecord.getDateJoining()).isEqualTo(DEFAULT_DATE_JOINING);
        assertThat(testEmployeeRecord.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testEmployeeRecord.getContractStartDate()).isEqualTo(DEFAULT_CONTRACT_START_DATE);
        assertThat(testEmployeeRecord.getContractEndDate()).isEqualTo(DEFAULT_CONTRACT_END_DATE);
        assertThat(testEmployeeRecord.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testEmployeeRecord.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testEmployeeRecord.getBankAccount()).isEqualTo(DEFAULT_BANK_ACCOUNT);
        assertThat(testEmployeeRecord.getInsuranceRegistrationNumber()).isEqualTo(DEFAULT_INSURANCE_REGISTRATION_NUMBER);
    }

    @Test
    @Transactional
    public void createEmployeeRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeRecordRepository.findAll().size();

        // Create the EmployeeRecord with an existing ID
        employeeRecord.setId(1L);
        EmployeeRecordDTO employeeRecordDTO = employeeRecordMapper.toDto(employeeRecord);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeRecordMockMvc.perform(post("/api/employee-records").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeRecordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeRecord in the database
        List<EmployeeRecord> employeeRecordList = employeeRecordRepository.findAll();
        assertThat(employeeRecordList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRecordRepository.findAll().size();
        // set the field null
        employeeRecord.setFirstName(null);

        // Create the EmployeeRecord, which fails.
        EmployeeRecordDTO employeeRecordDTO = employeeRecordMapper.toDto(employeeRecord);


        restEmployeeRecordMockMvc.perform(post("/api/employee-records").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeRecordDTO)))
            .andExpect(status().isBadRequest());

        List<EmployeeRecord> employeeRecordList = employeeRecordRepository.findAll();
        assertThat(employeeRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRecordRepository.findAll().size();
        // set the field null
        employeeRecord.setLastName(null);

        // Create the EmployeeRecord, which fails.
        EmployeeRecordDTO employeeRecordDTO = employeeRecordMapper.toDto(employeeRecord);


        restEmployeeRecordMockMvc.perform(post("/api/employee-records").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeRecordDTO)))
            .andExpect(status().isBadRequest());

        List<EmployeeRecord> employeeRecordList = employeeRecordRepository.findAll();
        assertThat(employeeRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBirthDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRecordRepository.findAll().size();
        // set the field null
        employeeRecord.setBirthDate(null);

        // Create the EmployeeRecord, which fails.
        EmployeeRecordDTO employeeRecordDTO = employeeRecordMapper.toDto(employeeRecord);


        restEmployeeRecordMockMvc.perform(post("/api/employee-records").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeRecordDTO)))
            .andExpect(status().isBadRequest());

        List<EmployeeRecord> employeeRecordList = employeeRecordRepository.findAll();
        assertThat(employeeRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmployeeNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRecordRepository.findAll().size();
        // set the field null
        employeeRecord.setEmployeeNumber(null);

        // Create the EmployeeRecord, which fails.
        EmployeeRecordDTO employeeRecordDTO = employeeRecordMapper.toDto(employeeRecord);


        restEmployeeRecordMockMvc.perform(post("/api/employee-records").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeRecordDTO)))
            .andExpect(status().isBadRequest());

        List<EmployeeRecord> employeeRecordList = employeeRecordRepository.findAll();
        assertThat(employeeRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecords() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList
        restEmployeeRecordMockMvc.perform(get("/api/employee-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].cellPhone").value(hasItem(DEFAULT_CELL_PHONE)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS)))
            .andExpect(jsonPath("$.[*].pictureContentType").value(hasItem(DEFAULT_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(Base64Utils.encodeToString(DEFAULT_PICTURE))))
            .andExpect(jsonPath("$.[*].employeeNumber").value(hasItem(DEFAULT_EMPLOYEE_NUMBER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateJoining").value(hasItem(DEFAULT_DATE_JOINING.toString())))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.doubleValue())))
            .andExpect(jsonPath("$.[*].contractStartDate").value(hasItem(DEFAULT_CONTRACT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].contractEndDate").value(hasItem(DEFAULT_CONTRACT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].bankAccount").value(hasItem(DEFAULT_BANK_ACCOUNT)))
            .andExpect(jsonPath("$.[*].insuranceRegistrationNumber").value(hasItem(DEFAULT_INSURANCE_REGISTRATION_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getEmployeeRecord() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get the employeeRecord
        restEmployeeRecordMockMvc.perform(get("/api/employee-records/{id}", employeeRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeRecord.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.cellPhone").value(DEFAULT_CELL_PHONE))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS))
            .andExpect(jsonPath("$.pictureContentType").value(DEFAULT_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.picture").value(Base64Utils.encodeToString(DEFAULT_PICTURE)))
            .andExpect(jsonPath("$.employeeNumber").value(DEFAULT_EMPLOYEE_NUMBER))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.dateJoining").value(DEFAULT_DATE_JOINING.toString()))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY.doubleValue()))
            .andExpect(jsonPath("$.contractStartDate").value(DEFAULT_CONTRACT_START_DATE.toString()))
            .andExpect(jsonPath("$.contractEndDate").value(DEFAULT_CONTRACT_END_DATE.toString()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME))
            .andExpect(jsonPath("$.bankAccount").value(DEFAULT_BANK_ACCOUNT))
            .andExpect(jsonPath("$.insuranceRegistrationNumber").value(DEFAULT_INSURANCE_REGISTRATION_NUMBER));
    }


    @Test
    @Transactional
    public void getEmployeeRecordsByIdFiltering() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        Long id = employeeRecord.getId();

        defaultEmployeeRecordShouldBeFound("id.equals=" + id);
        defaultEmployeeRecordShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeRecordShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeRecordShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeRecordShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeRecordShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmployeeRecordsByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where firstName equals to DEFAULT_FIRST_NAME
        defaultEmployeeRecordShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the employeeRecordList where firstName equals to UPDATED_FIRST_NAME
        defaultEmployeeRecordShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where firstName not equals to DEFAULT_FIRST_NAME
        defaultEmployeeRecordShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the employeeRecordList where firstName not equals to UPDATED_FIRST_NAME
        defaultEmployeeRecordShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultEmployeeRecordShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the employeeRecordList where firstName equals to UPDATED_FIRST_NAME
        defaultEmployeeRecordShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where firstName is not null
        defaultEmployeeRecordShouldBeFound("firstName.specified=true");

        // Get all the employeeRecordList where firstName is null
        defaultEmployeeRecordShouldNotBeFound("firstName.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeRecordsByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where firstName contains DEFAULT_FIRST_NAME
        defaultEmployeeRecordShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the employeeRecordList where firstName contains UPDATED_FIRST_NAME
        defaultEmployeeRecordShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where firstName does not contain DEFAULT_FIRST_NAME
        defaultEmployeeRecordShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the employeeRecordList where firstName does not contain UPDATED_FIRST_NAME
        defaultEmployeeRecordShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }


    @Test
    @Transactional
    public void getAllEmployeeRecordsByMiddleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where middleName equals to DEFAULT_MIDDLE_NAME
        defaultEmployeeRecordShouldBeFound("middleName.equals=" + DEFAULT_MIDDLE_NAME);

        // Get all the employeeRecordList where middleName equals to UPDATED_MIDDLE_NAME
        defaultEmployeeRecordShouldNotBeFound("middleName.equals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByMiddleNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where middleName not equals to DEFAULT_MIDDLE_NAME
        defaultEmployeeRecordShouldNotBeFound("middleName.notEquals=" + DEFAULT_MIDDLE_NAME);

        // Get all the employeeRecordList where middleName not equals to UPDATED_MIDDLE_NAME
        defaultEmployeeRecordShouldBeFound("middleName.notEquals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByMiddleNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where middleName in DEFAULT_MIDDLE_NAME or UPDATED_MIDDLE_NAME
        defaultEmployeeRecordShouldBeFound("middleName.in=" + DEFAULT_MIDDLE_NAME + "," + UPDATED_MIDDLE_NAME);

        // Get all the employeeRecordList where middleName equals to UPDATED_MIDDLE_NAME
        defaultEmployeeRecordShouldNotBeFound("middleName.in=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByMiddleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where middleName is not null
        defaultEmployeeRecordShouldBeFound("middleName.specified=true");

        // Get all the employeeRecordList where middleName is null
        defaultEmployeeRecordShouldNotBeFound("middleName.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeRecordsByMiddleNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where middleName contains DEFAULT_MIDDLE_NAME
        defaultEmployeeRecordShouldBeFound("middleName.contains=" + DEFAULT_MIDDLE_NAME);

        // Get all the employeeRecordList where middleName contains UPDATED_MIDDLE_NAME
        defaultEmployeeRecordShouldNotBeFound("middleName.contains=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByMiddleNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where middleName does not contain DEFAULT_MIDDLE_NAME
        defaultEmployeeRecordShouldNotBeFound("middleName.doesNotContain=" + DEFAULT_MIDDLE_NAME);

        // Get all the employeeRecordList where middleName does not contain UPDATED_MIDDLE_NAME
        defaultEmployeeRecordShouldBeFound("middleName.doesNotContain=" + UPDATED_MIDDLE_NAME);
    }


    @Test
    @Transactional
    public void getAllEmployeeRecordsByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where lastName equals to DEFAULT_LAST_NAME
        defaultEmployeeRecordShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the employeeRecordList where lastName equals to UPDATED_LAST_NAME
        defaultEmployeeRecordShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where lastName not equals to DEFAULT_LAST_NAME
        defaultEmployeeRecordShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the employeeRecordList where lastName not equals to UPDATED_LAST_NAME
        defaultEmployeeRecordShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultEmployeeRecordShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the employeeRecordList where lastName equals to UPDATED_LAST_NAME
        defaultEmployeeRecordShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where lastName is not null
        defaultEmployeeRecordShouldBeFound("lastName.specified=true");

        // Get all the employeeRecordList where lastName is null
        defaultEmployeeRecordShouldNotBeFound("lastName.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeRecordsByLastNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where lastName contains DEFAULT_LAST_NAME
        defaultEmployeeRecordShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the employeeRecordList where lastName contains UPDATED_LAST_NAME
        defaultEmployeeRecordShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where lastName does not contain DEFAULT_LAST_NAME
        defaultEmployeeRecordShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the employeeRecordList where lastName does not contain UPDATED_LAST_NAME
        defaultEmployeeRecordShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }


    @Test
    @Transactional
    public void getAllEmployeeRecordsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where address equals to DEFAULT_ADDRESS
        defaultEmployeeRecordShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the employeeRecordList where address equals to UPDATED_ADDRESS
        defaultEmployeeRecordShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where address not equals to DEFAULT_ADDRESS
        defaultEmployeeRecordShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the employeeRecordList where address not equals to UPDATED_ADDRESS
        defaultEmployeeRecordShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultEmployeeRecordShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the employeeRecordList where address equals to UPDATED_ADDRESS
        defaultEmployeeRecordShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where address is not null
        defaultEmployeeRecordShouldBeFound("address.specified=true");

        // Get all the employeeRecordList where address is null
        defaultEmployeeRecordShouldNotBeFound("address.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeRecordsByAddressContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where address contains DEFAULT_ADDRESS
        defaultEmployeeRecordShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the employeeRecordList where address contains UPDATED_ADDRESS
        defaultEmployeeRecordShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where address does not contain DEFAULT_ADDRESS
        defaultEmployeeRecordShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the employeeRecordList where address does not contain UPDATED_ADDRESS
        defaultEmployeeRecordShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllEmployeeRecordsByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where gender equals to DEFAULT_GENDER
        defaultEmployeeRecordShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the employeeRecordList where gender equals to UPDATED_GENDER
        defaultEmployeeRecordShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByGenderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where gender not equals to DEFAULT_GENDER
        defaultEmployeeRecordShouldNotBeFound("gender.notEquals=" + DEFAULT_GENDER);

        // Get all the employeeRecordList where gender not equals to UPDATED_GENDER
        defaultEmployeeRecordShouldBeFound("gender.notEquals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultEmployeeRecordShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the employeeRecordList where gender equals to UPDATED_GENDER
        defaultEmployeeRecordShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where gender is not null
        defaultEmployeeRecordShouldBeFound("gender.specified=true");

        // Get all the employeeRecordList where gender is null
        defaultEmployeeRecordShouldNotBeFound("gender.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeRecordsByGenderContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where gender contains DEFAULT_GENDER
        defaultEmployeeRecordShouldBeFound("gender.contains=" + DEFAULT_GENDER);

        // Get all the employeeRecordList where gender contains UPDATED_GENDER
        defaultEmployeeRecordShouldNotBeFound("gender.contains=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByGenderNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where gender does not contain DEFAULT_GENDER
        defaultEmployeeRecordShouldNotBeFound("gender.doesNotContain=" + DEFAULT_GENDER);

        // Get all the employeeRecordList where gender does not contain UPDATED_GENDER
        defaultEmployeeRecordShouldBeFound("gender.doesNotContain=" + UPDATED_GENDER);
    }


    @Test
    @Transactional
    public void getAllEmployeeRecordsByBirthDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where birthDate equals to DEFAULT_BIRTH_DATE
        defaultEmployeeRecordShouldBeFound("birthDate.equals=" + DEFAULT_BIRTH_DATE);

        // Get all the employeeRecordList where birthDate equals to UPDATED_BIRTH_DATE
        defaultEmployeeRecordShouldNotBeFound("birthDate.equals=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByBirthDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where birthDate not equals to DEFAULT_BIRTH_DATE
        defaultEmployeeRecordShouldNotBeFound("birthDate.notEquals=" + DEFAULT_BIRTH_DATE);

        // Get all the employeeRecordList where birthDate not equals to UPDATED_BIRTH_DATE
        defaultEmployeeRecordShouldBeFound("birthDate.notEquals=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByBirthDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where birthDate in DEFAULT_BIRTH_DATE or UPDATED_BIRTH_DATE
        defaultEmployeeRecordShouldBeFound("birthDate.in=" + DEFAULT_BIRTH_DATE + "," + UPDATED_BIRTH_DATE);

        // Get all the employeeRecordList where birthDate equals to UPDATED_BIRTH_DATE
        defaultEmployeeRecordShouldNotBeFound("birthDate.in=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByBirthDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where birthDate is not null
        defaultEmployeeRecordShouldBeFound("birthDate.specified=true");

        // Get all the employeeRecordList where birthDate is null
        defaultEmployeeRecordShouldNotBeFound("birthDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByBirthDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where birthDate is greater than or equal to DEFAULT_BIRTH_DATE
        defaultEmployeeRecordShouldBeFound("birthDate.greaterThanOrEqual=" + DEFAULT_BIRTH_DATE);

        // Get all the employeeRecordList where birthDate is greater than or equal to UPDATED_BIRTH_DATE
        defaultEmployeeRecordShouldNotBeFound("birthDate.greaterThanOrEqual=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByBirthDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where birthDate is less than or equal to DEFAULT_BIRTH_DATE
        defaultEmployeeRecordShouldBeFound("birthDate.lessThanOrEqual=" + DEFAULT_BIRTH_DATE);

        // Get all the employeeRecordList where birthDate is less than or equal to SMALLER_BIRTH_DATE
        defaultEmployeeRecordShouldNotBeFound("birthDate.lessThanOrEqual=" + SMALLER_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByBirthDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where birthDate is less than DEFAULT_BIRTH_DATE
        defaultEmployeeRecordShouldNotBeFound("birthDate.lessThan=" + DEFAULT_BIRTH_DATE);

        // Get all the employeeRecordList where birthDate is less than UPDATED_BIRTH_DATE
        defaultEmployeeRecordShouldBeFound("birthDate.lessThan=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByBirthDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where birthDate is greater than DEFAULT_BIRTH_DATE
        defaultEmployeeRecordShouldNotBeFound("birthDate.greaterThan=" + DEFAULT_BIRTH_DATE);

        // Get all the employeeRecordList where birthDate is greater than SMALLER_BIRTH_DATE
        defaultEmployeeRecordShouldBeFound("birthDate.greaterThan=" + SMALLER_BIRTH_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeeRecordsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where email equals to DEFAULT_EMAIL
        defaultEmployeeRecordShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the employeeRecordList where email equals to UPDATED_EMAIL
        defaultEmployeeRecordShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where email not equals to DEFAULT_EMAIL
        defaultEmployeeRecordShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the employeeRecordList where email not equals to UPDATED_EMAIL
        defaultEmployeeRecordShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultEmployeeRecordShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the employeeRecordList where email equals to UPDATED_EMAIL
        defaultEmployeeRecordShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where email is not null
        defaultEmployeeRecordShouldBeFound("email.specified=true");

        // Get all the employeeRecordList where email is null
        defaultEmployeeRecordShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeRecordsByEmailContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where email contains DEFAULT_EMAIL
        defaultEmployeeRecordShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the employeeRecordList where email contains UPDATED_EMAIL
        defaultEmployeeRecordShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where email does not contain DEFAULT_EMAIL
        defaultEmployeeRecordShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the employeeRecordList where email does not contain UPDATED_EMAIL
        defaultEmployeeRecordShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllEmployeeRecordsByCellPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where cellPhone equals to DEFAULT_CELL_PHONE
        defaultEmployeeRecordShouldBeFound("cellPhone.equals=" + DEFAULT_CELL_PHONE);

        // Get all the employeeRecordList where cellPhone equals to UPDATED_CELL_PHONE
        defaultEmployeeRecordShouldNotBeFound("cellPhone.equals=" + UPDATED_CELL_PHONE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByCellPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where cellPhone not equals to DEFAULT_CELL_PHONE
        defaultEmployeeRecordShouldNotBeFound("cellPhone.notEquals=" + DEFAULT_CELL_PHONE);

        // Get all the employeeRecordList where cellPhone not equals to UPDATED_CELL_PHONE
        defaultEmployeeRecordShouldBeFound("cellPhone.notEquals=" + UPDATED_CELL_PHONE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByCellPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where cellPhone in DEFAULT_CELL_PHONE or UPDATED_CELL_PHONE
        defaultEmployeeRecordShouldBeFound("cellPhone.in=" + DEFAULT_CELL_PHONE + "," + UPDATED_CELL_PHONE);

        // Get all the employeeRecordList where cellPhone equals to UPDATED_CELL_PHONE
        defaultEmployeeRecordShouldNotBeFound("cellPhone.in=" + UPDATED_CELL_PHONE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByCellPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where cellPhone is not null
        defaultEmployeeRecordShouldBeFound("cellPhone.specified=true");

        // Get all the employeeRecordList where cellPhone is null
        defaultEmployeeRecordShouldNotBeFound("cellPhone.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeRecordsByCellPhoneContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where cellPhone contains DEFAULT_CELL_PHONE
        defaultEmployeeRecordShouldBeFound("cellPhone.contains=" + DEFAULT_CELL_PHONE);

        // Get all the employeeRecordList where cellPhone contains UPDATED_CELL_PHONE
        defaultEmployeeRecordShouldNotBeFound("cellPhone.contains=" + UPDATED_CELL_PHONE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByCellPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where cellPhone does not contain DEFAULT_CELL_PHONE
        defaultEmployeeRecordShouldNotBeFound("cellPhone.doesNotContain=" + DEFAULT_CELL_PHONE);

        // Get all the employeeRecordList where cellPhone does not contain UPDATED_CELL_PHONE
        defaultEmployeeRecordShouldBeFound("cellPhone.doesNotContain=" + UPDATED_CELL_PHONE);
    }


    @Test
    @Transactional
    public void getAllEmployeeRecordsByTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where telephone equals to DEFAULT_TELEPHONE
        defaultEmployeeRecordShouldBeFound("telephone.equals=" + DEFAULT_TELEPHONE);

        // Get all the employeeRecordList where telephone equals to UPDATED_TELEPHONE
        defaultEmployeeRecordShouldNotBeFound("telephone.equals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where telephone not equals to DEFAULT_TELEPHONE
        defaultEmployeeRecordShouldNotBeFound("telephone.notEquals=" + DEFAULT_TELEPHONE);

        // Get all the employeeRecordList where telephone not equals to UPDATED_TELEPHONE
        defaultEmployeeRecordShouldBeFound("telephone.notEquals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where telephone in DEFAULT_TELEPHONE or UPDATED_TELEPHONE
        defaultEmployeeRecordShouldBeFound("telephone.in=" + DEFAULT_TELEPHONE + "," + UPDATED_TELEPHONE);

        // Get all the employeeRecordList where telephone equals to UPDATED_TELEPHONE
        defaultEmployeeRecordShouldNotBeFound("telephone.in=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where telephone is not null
        defaultEmployeeRecordShouldBeFound("telephone.specified=true");

        // Get all the employeeRecordList where telephone is null
        defaultEmployeeRecordShouldNotBeFound("telephone.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeRecordsByTelephoneContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where telephone contains DEFAULT_TELEPHONE
        defaultEmployeeRecordShouldBeFound("telephone.contains=" + DEFAULT_TELEPHONE);

        // Get all the employeeRecordList where telephone contains UPDATED_TELEPHONE
        defaultEmployeeRecordShouldNotBeFound("telephone.contains=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByTelephoneNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where telephone does not contain DEFAULT_TELEPHONE
        defaultEmployeeRecordShouldNotBeFound("telephone.doesNotContain=" + DEFAULT_TELEPHONE);

        // Get all the employeeRecordList where telephone does not contain UPDATED_TELEPHONE
        defaultEmployeeRecordShouldBeFound("telephone.doesNotContain=" + UPDATED_TELEPHONE);
    }


    @Test
    @Transactional
    public void getAllEmployeeRecordsByMaritalStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where maritalStatus equals to DEFAULT_MARITAL_STATUS
        defaultEmployeeRecordShouldBeFound("maritalStatus.equals=" + DEFAULT_MARITAL_STATUS);

        // Get all the employeeRecordList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultEmployeeRecordShouldNotBeFound("maritalStatus.equals=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByMaritalStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where maritalStatus not equals to DEFAULT_MARITAL_STATUS
        defaultEmployeeRecordShouldNotBeFound("maritalStatus.notEquals=" + DEFAULT_MARITAL_STATUS);

        // Get all the employeeRecordList where maritalStatus not equals to UPDATED_MARITAL_STATUS
        defaultEmployeeRecordShouldBeFound("maritalStatus.notEquals=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByMaritalStatusIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where maritalStatus in DEFAULT_MARITAL_STATUS or UPDATED_MARITAL_STATUS
        defaultEmployeeRecordShouldBeFound("maritalStatus.in=" + DEFAULT_MARITAL_STATUS + "," + UPDATED_MARITAL_STATUS);

        // Get all the employeeRecordList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultEmployeeRecordShouldNotBeFound("maritalStatus.in=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByMaritalStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where maritalStatus is not null
        defaultEmployeeRecordShouldBeFound("maritalStatus.specified=true");

        // Get all the employeeRecordList where maritalStatus is null
        defaultEmployeeRecordShouldNotBeFound("maritalStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeRecordsByMaritalStatusContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where maritalStatus contains DEFAULT_MARITAL_STATUS
        defaultEmployeeRecordShouldBeFound("maritalStatus.contains=" + DEFAULT_MARITAL_STATUS);

        // Get all the employeeRecordList where maritalStatus contains UPDATED_MARITAL_STATUS
        defaultEmployeeRecordShouldNotBeFound("maritalStatus.contains=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByMaritalStatusNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where maritalStatus does not contain DEFAULT_MARITAL_STATUS
        defaultEmployeeRecordShouldNotBeFound("maritalStatus.doesNotContain=" + DEFAULT_MARITAL_STATUS);

        // Get all the employeeRecordList where maritalStatus does not contain UPDATED_MARITAL_STATUS
        defaultEmployeeRecordShouldBeFound("maritalStatus.doesNotContain=" + UPDATED_MARITAL_STATUS);
    }


    @Test
    @Transactional
    public void getAllEmployeeRecordsByEmployeeNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where employeeNumber equals to DEFAULT_EMPLOYEE_NUMBER
        defaultEmployeeRecordShouldBeFound("employeeNumber.equals=" + DEFAULT_EMPLOYEE_NUMBER);

        // Get all the employeeRecordList where employeeNumber equals to UPDATED_EMPLOYEE_NUMBER
        defaultEmployeeRecordShouldNotBeFound("employeeNumber.equals=" + UPDATED_EMPLOYEE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByEmployeeNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where employeeNumber not equals to DEFAULT_EMPLOYEE_NUMBER
        defaultEmployeeRecordShouldNotBeFound("employeeNumber.notEquals=" + DEFAULT_EMPLOYEE_NUMBER);

        // Get all the employeeRecordList where employeeNumber not equals to UPDATED_EMPLOYEE_NUMBER
        defaultEmployeeRecordShouldBeFound("employeeNumber.notEquals=" + UPDATED_EMPLOYEE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByEmployeeNumberIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where employeeNumber in DEFAULT_EMPLOYEE_NUMBER or UPDATED_EMPLOYEE_NUMBER
        defaultEmployeeRecordShouldBeFound("employeeNumber.in=" + DEFAULT_EMPLOYEE_NUMBER + "," + UPDATED_EMPLOYEE_NUMBER);

        // Get all the employeeRecordList where employeeNumber equals to UPDATED_EMPLOYEE_NUMBER
        defaultEmployeeRecordShouldNotBeFound("employeeNumber.in=" + UPDATED_EMPLOYEE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByEmployeeNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where employeeNumber is not null
        defaultEmployeeRecordShouldBeFound("employeeNumber.specified=true");

        // Get all the employeeRecordList where employeeNumber is null
        defaultEmployeeRecordShouldNotBeFound("employeeNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeRecordsByEmployeeNumberContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where employeeNumber contains DEFAULT_EMPLOYEE_NUMBER
        defaultEmployeeRecordShouldBeFound("employeeNumber.contains=" + DEFAULT_EMPLOYEE_NUMBER);

        // Get all the employeeRecordList where employeeNumber contains UPDATED_EMPLOYEE_NUMBER
        defaultEmployeeRecordShouldNotBeFound("employeeNumber.contains=" + UPDATED_EMPLOYEE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByEmployeeNumberNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where employeeNumber does not contain DEFAULT_EMPLOYEE_NUMBER
        defaultEmployeeRecordShouldNotBeFound("employeeNumber.doesNotContain=" + DEFAULT_EMPLOYEE_NUMBER);

        // Get all the employeeRecordList where employeeNumber does not contain UPDATED_EMPLOYEE_NUMBER
        defaultEmployeeRecordShouldBeFound("employeeNumber.doesNotContain=" + UPDATED_EMPLOYEE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllEmployeeRecordsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where active equals to DEFAULT_ACTIVE
        defaultEmployeeRecordShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the employeeRecordList where active equals to UPDATED_ACTIVE
        defaultEmployeeRecordShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where active not equals to DEFAULT_ACTIVE
        defaultEmployeeRecordShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the employeeRecordList where active not equals to UPDATED_ACTIVE
        defaultEmployeeRecordShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultEmployeeRecordShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the employeeRecordList where active equals to UPDATED_ACTIVE
        defaultEmployeeRecordShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where active is not null
        defaultEmployeeRecordShouldBeFound("active.specified=true");

        // Get all the employeeRecordList where active is null
        defaultEmployeeRecordShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByDateJoiningIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where dateJoining equals to DEFAULT_DATE_JOINING
        defaultEmployeeRecordShouldBeFound("dateJoining.equals=" + DEFAULT_DATE_JOINING);

        // Get all the employeeRecordList where dateJoining equals to UPDATED_DATE_JOINING
        defaultEmployeeRecordShouldNotBeFound("dateJoining.equals=" + UPDATED_DATE_JOINING);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByDateJoiningIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where dateJoining not equals to DEFAULT_DATE_JOINING
        defaultEmployeeRecordShouldNotBeFound("dateJoining.notEquals=" + DEFAULT_DATE_JOINING);

        // Get all the employeeRecordList where dateJoining not equals to UPDATED_DATE_JOINING
        defaultEmployeeRecordShouldBeFound("dateJoining.notEquals=" + UPDATED_DATE_JOINING);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByDateJoiningIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where dateJoining in DEFAULT_DATE_JOINING or UPDATED_DATE_JOINING
        defaultEmployeeRecordShouldBeFound("dateJoining.in=" + DEFAULT_DATE_JOINING + "," + UPDATED_DATE_JOINING);

        // Get all the employeeRecordList where dateJoining equals to UPDATED_DATE_JOINING
        defaultEmployeeRecordShouldNotBeFound("dateJoining.in=" + UPDATED_DATE_JOINING);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByDateJoiningIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where dateJoining is not null
        defaultEmployeeRecordShouldBeFound("dateJoining.specified=true");

        // Get all the employeeRecordList where dateJoining is null
        defaultEmployeeRecordShouldNotBeFound("dateJoining.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByDateJoiningIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where dateJoining is greater than or equal to DEFAULT_DATE_JOINING
        defaultEmployeeRecordShouldBeFound("dateJoining.greaterThanOrEqual=" + DEFAULT_DATE_JOINING);

        // Get all the employeeRecordList where dateJoining is greater than or equal to UPDATED_DATE_JOINING
        defaultEmployeeRecordShouldNotBeFound("dateJoining.greaterThanOrEqual=" + UPDATED_DATE_JOINING);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByDateJoiningIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where dateJoining is less than or equal to DEFAULT_DATE_JOINING
        defaultEmployeeRecordShouldBeFound("dateJoining.lessThanOrEqual=" + DEFAULT_DATE_JOINING);

        // Get all the employeeRecordList where dateJoining is less than or equal to SMALLER_DATE_JOINING
        defaultEmployeeRecordShouldNotBeFound("dateJoining.lessThanOrEqual=" + SMALLER_DATE_JOINING);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByDateJoiningIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where dateJoining is less than DEFAULT_DATE_JOINING
        defaultEmployeeRecordShouldNotBeFound("dateJoining.lessThan=" + DEFAULT_DATE_JOINING);

        // Get all the employeeRecordList where dateJoining is less than UPDATED_DATE_JOINING
        defaultEmployeeRecordShouldBeFound("dateJoining.lessThan=" + UPDATED_DATE_JOINING);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByDateJoiningIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where dateJoining is greater than DEFAULT_DATE_JOINING
        defaultEmployeeRecordShouldNotBeFound("dateJoining.greaterThan=" + DEFAULT_DATE_JOINING);

        // Get all the employeeRecordList where dateJoining is greater than SMALLER_DATE_JOINING
        defaultEmployeeRecordShouldBeFound("dateJoining.greaterThan=" + SMALLER_DATE_JOINING);
    }


    @Test
    @Transactional
    public void getAllEmployeeRecordsBySalaryIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where salary equals to DEFAULT_SALARY
        defaultEmployeeRecordShouldBeFound("salary.equals=" + DEFAULT_SALARY);

        // Get all the employeeRecordList where salary equals to UPDATED_SALARY
        defaultEmployeeRecordShouldNotBeFound("salary.equals=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsBySalaryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where salary not equals to DEFAULT_SALARY
        defaultEmployeeRecordShouldNotBeFound("salary.notEquals=" + DEFAULT_SALARY);

        // Get all the employeeRecordList where salary not equals to UPDATED_SALARY
        defaultEmployeeRecordShouldBeFound("salary.notEquals=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsBySalaryIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where salary in DEFAULT_SALARY or UPDATED_SALARY
        defaultEmployeeRecordShouldBeFound("salary.in=" + DEFAULT_SALARY + "," + UPDATED_SALARY);

        // Get all the employeeRecordList where salary equals to UPDATED_SALARY
        defaultEmployeeRecordShouldNotBeFound("salary.in=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsBySalaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where salary is not null
        defaultEmployeeRecordShouldBeFound("salary.specified=true");

        // Get all the employeeRecordList where salary is null
        defaultEmployeeRecordShouldNotBeFound("salary.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsBySalaryIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where salary is greater than or equal to DEFAULT_SALARY
        defaultEmployeeRecordShouldBeFound("salary.greaterThanOrEqual=" + DEFAULT_SALARY);

        // Get all the employeeRecordList where salary is greater than or equal to UPDATED_SALARY
        defaultEmployeeRecordShouldNotBeFound("salary.greaterThanOrEqual=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsBySalaryIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where salary is less than or equal to DEFAULT_SALARY
        defaultEmployeeRecordShouldBeFound("salary.lessThanOrEqual=" + DEFAULT_SALARY);

        // Get all the employeeRecordList where salary is less than or equal to SMALLER_SALARY
        defaultEmployeeRecordShouldNotBeFound("salary.lessThanOrEqual=" + SMALLER_SALARY);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsBySalaryIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where salary is less than DEFAULT_SALARY
        defaultEmployeeRecordShouldNotBeFound("salary.lessThan=" + DEFAULT_SALARY);

        // Get all the employeeRecordList where salary is less than UPDATED_SALARY
        defaultEmployeeRecordShouldBeFound("salary.lessThan=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsBySalaryIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where salary is greater than DEFAULT_SALARY
        defaultEmployeeRecordShouldNotBeFound("salary.greaterThan=" + DEFAULT_SALARY);

        // Get all the employeeRecordList where salary is greater than SMALLER_SALARY
        defaultEmployeeRecordShouldBeFound("salary.greaterThan=" + SMALLER_SALARY);
    }


    @Test
    @Transactional
    public void getAllEmployeeRecordsByContractStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where contractStartDate equals to DEFAULT_CONTRACT_START_DATE
        defaultEmployeeRecordShouldBeFound("contractStartDate.equals=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the employeeRecordList where contractStartDate equals to UPDATED_CONTRACT_START_DATE
        defaultEmployeeRecordShouldNotBeFound("contractStartDate.equals=" + UPDATED_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByContractStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where contractStartDate not equals to DEFAULT_CONTRACT_START_DATE
        defaultEmployeeRecordShouldNotBeFound("contractStartDate.notEquals=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the employeeRecordList where contractStartDate not equals to UPDATED_CONTRACT_START_DATE
        defaultEmployeeRecordShouldBeFound("contractStartDate.notEquals=" + UPDATED_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByContractStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where contractStartDate in DEFAULT_CONTRACT_START_DATE or UPDATED_CONTRACT_START_DATE
        defaultEmployeeRecordShouldBeFound("contractStartDate.in=" + DEFAULT_CONTRACT_START_DATE + "," + UPDATED_CONTRACT_START_DATE);

        // Get all the employeeRecordList where contractStartDate equals to UPDATED_CONTRACT_START_DATE
        defaultEmployeeRecordShouldNotBeFound("contractStartDate.in=" + UPDATED_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByContractStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where contractStartDate is not null
        defaultEmployeeRecordShouldBeFound("contractStartDate.specified=true");

        // Get all the employeeRecordList where contractStartDate is null
        defaultEmployeeRecordShouldNotBeFound("contractStartDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByContractStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where contractStartDate is greater than or equal to DEFAULT_CONTRACT_START_DATE
        defaultEmployeeRecordShouldBeFound("contractStartDate.greaterThanOrEqual=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the employeeRecordList where contractStartDate is greater than or equal to UPDATED_CONTRACT_START_DATE
        defaultEmployeeRecordShouldNotBeFound("contractStartDate.greaterThanOrEqual=" + UPDATED_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByContractStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where contractStartDate is less than or equal to DEFAULT_CONTRACT_START_DATE
        defaultEmployeeRecordShouldBeFound("contractStartDate.lessThanOrEqual=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the employeeRecordList where contractStartDate is less than or equal to SMALLER_CONTRACT_START_DATE
        defaultEmployeeRecordShouldNotBeFound("contractStartDate.lessThanOrEqual=" + SMALLER_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByContractStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where contractStartDate is less than DEFAULT_CONTRACT_START_DATE
        defaultEmployeeRecordShouldNotBeFound("contractStartDate.lessThan=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the employeeRecordList where contractStartDate is less than UPDATED_CONTRACT_START_DATE
        defaultEmployeeRecordShouldBeFound("contractStartDate.lessThan=" + UPDATED_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByContractStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where contractStartDate is greater than DEFAULT_CONTRACT_START_DATE
        defaultEmployeeRecordShouldNotBeFound("contractStartDate.greaterThan=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the employeeRecordList where contractStartDate is greater than SMALLER_CONTRACT_START_DATE
        defaultEmployeeRecordShouldBeFound("contractStartDate.greaterThan=" + SMALLER_CONTRACT_START_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeeRecordsByContractEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where contractEndDate equals to DEFAULT_CONTRACT_END_DATE
        defaultEmployeeRecordShouldBeFound("contractEndDate.equals=" + DEFAULT_CONTRACT_END_DATE);

        // Get all the employeeRecordList where contractEndDate equals to UPDATED_CONTRACT_END_DATE
        defaultEmployeeRecordShouldNotBeFound("contractEndDate.equals=" + UPDATED_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByContractEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where contractEndDate not equals to DEFAULT_CONTRACT_END_DATE
        defaultEmployeeRecordShouldNotBeFound("contractEndDate.notEquals=" + DEFAULT_CONTRACT_END_DATE);

        // Get all the employeeRecordList where contractEndDate not equals to UPDATED_CONTRACT_END_DATE
        defaultEmployeeRecordShouldBeFound("contractEndDate.notEquals=" + UPDATED_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByContractEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where contractEndDate in DEFAULT_CONTRACT_END_DATE or UPDATED_CONTRACT_END_DATE
        defaultEmployeeRecordShouldBeFound("contractEndDate.in=" + DEFAULT_CONTRACT_END_DATE + "," + UPDATED_CONTRACT_END_DATE);

        // Get all the employeeRecordList where contractEndDate equals to UPDATED_CONTRACT_END_DATE
        defaultEmployeeRecordShouldNotBeFound("contractEndDate.in=" + UPDATED_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByContractEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where contractEndDate is not null
        defaultEmployeeRecordShouldBeFound("contractEndDate.specified=true");

        // Get all the employeeRecordList where contractEndDate is null
        defaultEmployeeRecordShouldNotBeFound("contractEndDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByContractEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where contractEndDate is greater than or equal to DEFAULT_CONTRACT_END_DATE
        defaultEmployeeRecordShouldBeFound("contractEndDate.greaterThanOrEqual=" + DEFAULT_CONTRACT_END_DATE);

        // Get all the employeeRecordList where contractEndDate is greater than or equal to UPDATED_CONTRACT_END_DATE
        defaultEmployeeRecordShouldNotBeFound("contractEndDate.greaterThanOrEqual=" + UPDATED_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByContractEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where contractEndDate is less than or equal to DEFAULT_CONTRACT_END_DATE
        defaultEmployeeRecordShouldBeFound("contractEndDate.lessThanOrEqual=" + DEFAULT_CONTRACT_END_DATE);

        // Get all the employeeRecordList where contractEndDate is less than or equal to SMALLER_CONTRACT_END_DATE
        defaultEmployeeRecordShouldNotBeFound("contractEndDate.lessThanOrEqual=" + SMALLER_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByContractEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where contractEndDate is less than DEFAULT_CONTRACT_END_DATE
        defaultEmployeeRecordShouldNotBeFound("contractEndDate.lessThan=" + DEFAULT_CONTRACT_END_DATE);

        // Get all the employeeRecordList where contractEndDate is less than UPDATED_CONTRACT_END_DATE
        defaultEmployeeRecordShouldBeFound("contractEndDate.lessThan=" + UPDATED_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByContractEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where contractEndDate is greater than DEFAULT_CONTRACT_END_DATE
        defaultEmployeeRecordShouldNotBeFound("contractEndDate.greaterThan=" + DEFAULT_CONTRACT_END_DATE);

        // Get all the employeeRecordList where contractEndDate is greater than SMALLER_CONTRACT_END_DATE
        defaultEmployeeRecordShouldBeFound("contractEndDate.greaterThan=" + SMALLER_CONTRACT_END_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeeRecordsByBankNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where bankName equals to DEFAULT_BANK_NAME
        defaultEmployeeRecordShouldBeFound("bankName.equals=" + DEFAULT_BANK_NAME);

        // Get all the employeeRecordList where bankName equals to UPDATED_BANK_NAME
        defaultEmployeeRecordShouldNotBeFound("bankName.equals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByBankNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where bankName not equals to DEFAULT_BANK_NAME
        defaultEmployeeRecordShouldNotBeFound("bankName.notEquals=" + DEFAULT_BANK_NAME);

        // Get all the employeeRecordList where bankName not equals to UPDATED_BANK_NAME
        defaultEmployeeRecordShouldBeFound("bankName.notEquals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByBankNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where bankName in DEFAULT_BANK_NAME or UPDATED_BANK_NAME
        defaultEmployeeRecordShouldBeFound("bankName.in=" + DEFAULT_BANK_NAME + "," + UPDATED_BANK_NAME);

        // Get all the employeeRecordList where bankName equals to UPDATED_BANK_NAME
        defaultEmployeeRecordShouldNotBeFound("bankName.in=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByBankNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where bankName is not null
        defaultEmployeeRecordShouldBeFound("bankName.specified=true");

        // Get all the employeeRecordList where bankName is null
        defaultEmployeeRecordShouldNotBeFound("bankName.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeRecordsByBankNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where bankName contains DEFAULT_BANK_NAME
        defaultEmployeeRecordShouldBeFound("bankName.contains=" + DEFAULT_BANK_NAME);

        // Get all the employeeRecordList where bankName contains UPDATED_BANK_NAME
        defaultEmployeeRecordShouldNotBeFound("bankName.contains=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByBankNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where bankName does not contain DEFAULT_BANK_NAME
        defaultEmployeeRecordShouldNotBeFound("bankName.doesNotContain=" + DEFAULT_BANK_NAME);

        // Get all the employeeRecordList where bankName does not contain UPDATED_BANK_NAME
        defaultEmployeeRecordShouldBeFound("bankName.doesNotContain=" + UPDATED_BANK_NAME);
    }


    @Test
    @Transactional
    public void getAllEmployeeRecordsByBranchNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where branchName equals to DEFAULT_BRANCH_NAME
        defaultEmployeeRecordShouldBeFound("branchName.equals=" + DEFAULT_BRANCH_NAME);

        // Get all the employeeRecordList where branchName equals to UPDATED_BRANCH_NAME
        defaultEmployeeRecordShouldNotBeFound("branchName.equals=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByBranchNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where branchName not equals to DEFAULT_BRANCH_NAME
        defaultEmployeeRecordShouldNotBeFound("branchName.notEquals=" + DEFAULT_BRANCH_NAME);

        // Get all the employeeRecordList where branchName not equals to UPDATED_BRANCH_NAME
        defaultEmployeeRecordShouldBeFound("branchName.notEquals=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByBranchNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where branchName in DEFAULT_BRANCH_NAME or UPDATED_BRANCH_NAME
        defaultEmployeeRecordShouldBeFound("branchName.in=" + DEFAULT_BRANCH_NAME + "," + UPDATED_BRANCH_NAME);

        // Get all the employeeRecordList where branchName equals to UPDATED_BRANCH_NAME
        defaultEmployeeRecordShouldNotBeFound("branchName.in=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByBranchNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where branchName is not null
        defaultEmployeeRecordShouldBeFound("branchName.specified=true");

        // Get all the employeeRecordList where branchName is null
        defaultEmployeeRecordShouldNotBeFound("branchName.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeRecordsByBranchNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where branchName contains DEFAULT_BRANCH_NAME
        defaultEmployeeRecordShouldBeFound("branchName.contains=" + DEFAULT_BRANCH_NAME);

        // Get all the employeeRecordList where branchName contains UPDATED_BRANCH_NAME
        defaultEmployeeRecordShouldNotBeFound("branchName.contains=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByBranchNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where branchName does not contain DEFAULT_BRANCH_NAME
        defaultEmployeeRecordShouldNotBeFound("branchName.doesNotContain=" + DEFAULT_BRANCH_NAME);

        // Get all the employeeRecordList where branchName does not contain UPDATED_BRANCH_NAME
        defaultEmployeeRecordShouldBeFound("branchName.doesNotContain=" + UPDATED_BRANCH_NAME);
    }


    @Test
    @Transactional
    public void getAllEmployeeRecordsByBankAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where bankAccount equals to DEFAULT_BANK_ACCOUNT
        defaultEmployeeRecordShouldBeFound("bankAccount.equals=" + DEFAULT_BANK_ACCOUNT);

        // Get all the employeeRecordList where bankAccount equals to UPDATED_BANK_ACCOUNT
        defaultEmployeeRecordShouldNotBeFound("bankAccount.equals=" + UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByBankAccountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where bankAccount not equals to DEFAULT_BANK_ACCOUNT
        defaultEmployeeRecordShouldNotBeFound("bankAccount.notEquals=" + DEFAULT_BANK_ACCOUNT);

        // Get all the employeeRecordList where bankAccount not equals to UPDATED_BANK_ACCOUNT
        defaultEmployeeRecordShouldBeFound("bankAccount.notEquals=" + UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByBankAccountIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where bankAccount in DEFAULT_BANK_ACCOUNT or UPDATED_BANK_ACCOUNT
        defaultEmployeeRecordShouldBeFound("bankAccount.in=" + DEFAULT_BANK_ACCOUNT + "," + UPDATED_BANK_ACCOUNT);

        // Get all the employeeRecordList where bankAccount equals to UPDATED_BANK_ACCOUNT
        defaultEmployeeRecordShouldNotBeFound("bankAccount.in=" + UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByBankAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where bankAccount is not null
        defaultEmployeeRecordShouldBeFound("bankAccount.specified=true");

        // Get all the employeeRecordList where bankAccount is null
        defaultEmployeeRecordShouldNotBeFound("bankAccount.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeRecordsByBankAccountContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where bankAccount contains DEFAULT_BANK_ACCOUNT
        defaultEmployeeRecordShouldBeFound("bankAccount.contains=" + DEFAULT_BANK_ACCOUNT);

        // Get all the employeeRecordList where bankAccount contains UPDATED_BANK_ACCOUNT
        defaultEmployeeRecordShouldNotBeFound("bankAccount.contains=" + UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByBankAccountNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where bankAccount does not contain DEFAULT_BANK_ACCOUNT
        defaultEmployeeRecordShouldNotBeFound("bankAccount.doesNotContain=" + DEFAULT_BANK_ACCOUNT);

        // Get all the employeeRecordList where bankAccount does not contain UPDATED_BANK_ACCOUNT
        defaultEmployeeRecordShouldBeFound("bankAccount.doesNotContain=" + UPDATED_BANK_ACCOUNT);
    }


    @Test
    @Transactional
    public void getAllEmployeeRecordsByInsuranceRegistrationNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where insuranceRegistrationNumber equals to DEFAULT_INSURANCE_REGISTRATION_NUMBER
        defaultEmployeeRecordShouldBeFound("insuranceRegistrationNumber.equals=" + DEFAULT_INSURANCE_REGISTRATION_NUMBER);

        // Get all the employeeRecordList where insuranceRegistrationNumber equals to UPDATED_INSURANCE_REGISTRATION_NUMBER
        defaultEmployeeRecordShouldNotBeFound("insuranceRegistrationNumber.equals=" + UPDATED_INSURANCE_REGISTRATION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByInsuranceRegistrationNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where insuranceRegistrationNumber not equals to DEFAULT_INSURANCE_REGISTRATION_NUMBER
        defaultEmployeeRecordShouldNotBeFound("insuranceRegistrationNumber.notEquals=" + DEFAULT_INSURANCE_REGISTRATION_NUMBER);

        // Get all the employeeRecordList where insuranceRegistrationNumber not equals to UPDATED_INSURANCE_REGISTRATION_NUMBER
        defaultEmployeeRecordShouldBeFound("insuranceRegistrationNumber.notEquals=" + UPDATED_INSURANCE_REGISTRATION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByInsuranceRegistrationNumberIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where insuranceRegistrationNumber in DEFAULT_INSURANCE_REGISTRATION_NUMBER or UPDATED_INSURANCE_REGISTRATION_NUMBER
        defaultEmployeeRecordShouldBeFound("insuranceRegistrationNumber.in=" + DEFAULT_INSURANCE_REGISTRATION_NUMBER + "," + UPDATED_INSURANCE_REGISTRATION_NUMBER);

        // Get all the employeeRecordList where insuranceRegistrationNumber equals to UPDATED_INSURANCE_REGISTRATION_NUMBER
        defaultEmployeeRecordShouldNotBeFound("insuranceRegistrationNumber.in=" + UPDATED_INSURANCE_REGISTRATION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByInsuranceRegistrationNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where insuranceRegistrationNumber is not null
        defaultEmployeeRecordShouldBeFound("insuranceRegistrationNumber.specified=true");

        // Get all the employeeRecordList where insuranceRegistrationNumber is null
        defaultEmployeeRecordShouldNotBeFound("insuranceRegistrationNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeRecordsByInsuranceRegistrationNumberContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where insuranceRegistrationNumber contains DEFAULT_INSURANCE_REGISTRATION_NUMBER
        defaultEmployeeRecordShouldBeFound("insuranceRegistrationNumber.contains=" + DEFAULT_INSURANCE_REGISTRATION_NUMBER);

        // Get all the employeeRecordList where insuranceRegistrationNumber contains UPDATED_INSURANCE_REGISTRATION_NUMBER
        defaultEmployeeRecordShouldNotBeFound("insuranceRegistrationNumber.contains=" + UPDATED_INSURANCE_REGISTRATION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmployeeRecordsByInsuranceRegistrationNumberNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        // Get all the employeeRecordList where insuranceRegistrationNumber does not contain DEFAULT_INSURANCE_REGISTRATION_NUMBER
        defaultEmployeeRecordShouldNotBeFound("insuranceRegistrationNumber.doesNotContain=" + DEFAULT_INSURANCE_REGISTRATION_NUMBER);

        // Get all the employeeRecordList where insuranceRegistrationNumber does not contain UPDATED_INSURANCE_REGISTRATION_NUMBER
        defaultEmployeeRecordShouldBeFound("insuranceRegistrationNumber.doesNotContain=" + UPDATED_INSURANCE_REGISTRATION_NUMBER);
    }


    @Test
    @Transactional
    public void getAllEmployeeRecordsByAttachmentsIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);
        Attachment attachments = AttachmentResourceIT.createEntity(em);
        em.persist(attachments);
        em.flush();
        employeeRecord.addAttachments(attachments);
        employeeRecordRepository.saveAndFlush(employeeRecord);
        Long attachmentsId = attachments.getId();

        // Get all the employeeRecordList where attachments equals to attachmentsId
        defaultEmployeeRecordShouldBeFound("attachmentsId.equals=" + attachmentsId);

        // Get all the employeeRecordList where attachments equals to attachmentsId + 1
        defaultEmployeeRecordShouldNotBeFound("attachmentsId.equals=" + (attachmentsId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeeRecordsByDepartmentIsEqualToSomething() throws Exception {
        // Get already existing entity
        Department department = employeeRecord.getDepartment();
        employeeRecordRepository.saveAndFlush(employeeRecord);
        Long departmentId = department.getId();

        // Get all the employeeRecordList where department equals to departmentId
        defaultEmployeeRecordShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the employeeRecordList where department equals to departmentId + 1
        defaultEmployeeRecordShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeeRecordsByEmployeeTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        EmploymentCategory employeeType = employeeRecord.getEmployeeType();
        employeeRecordRepository.saveAndFlush(employeeRecord);
        Long employeeTypeId = employeeType.getId();

        // Get all the employeeRecordList where employeeType equals to employeeTypeId
        defaultEmployeeRecordShouldBeFound("employeeTypeId.equals=" + employeeTypeId);

        // Get all the employeeRecordList where employeeType equals to employeeTypeId + 1
        defaultEmployeeRecordShouldNotBeFound("employeeTypeId.equals=" + (employeeTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeeRecordsByDesignationIsEqualToSomething() throws Exception {
        // Get already existing entity
        Carder designation = employeeRecord.getDesignation();
        employeeRecordRepository.saveAndFlush(employeeRecord);
        Long designationId = designation.getId();

        // Get all the employeeRecordList where designation equals to designationId
        defaultEmployeeRecordShouldBeFound("designationId.equals=" + designationId);

        // Get all the employeeRecordList where designation equals to designationId + 1
        defaultEmployeeRecordShouldNotBeFound("designationId.equals=" + (designationId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeeRecordsByFacilityIsEqualToSomething() throws Exception {
        // Get already existing entity
        Facility facility = employeeRecord.getFacility();
        employeeRecordRepository.saveAndFlush(employeeRecord);
        Long facilityId = facility.getId();

        // Get all the employeeRecordList where facility equals to facilityId
        defaultEmployeeRecordShouldBeFound("facilityId.equals=" + facilityId);

        // Get all the employeeRecordList where facility equals to facilityId + 1
        defaultEmployeeRecordShouldNotBeFound("facilityId.equals=" + (facilityId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeeRecordsByProjectIsEqualToSomething() throws Exception {
        // Get already existing entity
        Project project = employeeRecord.getProject();
        employeeRecordRepository.saveAndFlush(employeeRecord);
        Long projectId = project.getId();

        // Get all the employeeRecordList where project equals to projectId
        defaultEmployeeRecordShouldBeFound("projectId.equals=" + projectId);

        // Get all the employeeRecordList where project equals to projectId + 1
        defaultEmployeeRecordShouldNotBeFound("projectId.equals=" + (projectId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeRecordShouldBeFound(String filter) throws Exception {
        restEmployeeRecordMockMvc.perform(get("/api/employee-records?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].cellPhone").value(hasItem(DEFAULT_CELL_PHONE)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS)))
            .andExpect(jsonPath("$.[*].pictureContentType").value(hasItem(DEFAULT_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(Base64Utils.encodeToString(DEFAULT_PICTURE))))
            .andExpect(jsonPath("$.[*].employeeNumber").value(hasItem(DEFAULT_EMPLOYEE_NUMBER)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateJoining").value(hasItem(DEFAULT_DATE_JOINING.toString())))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.doubleValue())))
            .andExpect(jsonPath("$.[*].contractStartDate").value(hasItem(DEFAULT_CONTRACT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].contractEndDate").value(hasItem(DEFAULT_CONTRACT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].bankAccount").value(hasItem(DEFAULT_BANK_ACCOUNT)))
            .andExpect(jsonPath("$.[*].insuranceRegistrationNumber").value(hasItem(DEFAULT_INSURANCE_REGISTRATION_NUMBER)));

        // Check, that the count call also returns 1
        restEmployeeRecordMockMvc.perform(get("/api/employee-records/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeRecordShouldNotBeFound(String filter) throws Exception {
        restEmployeeRecordMockMvc.perform(get("/api/employee-records?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeRecordMockMvc.perform(get("/api/employee-records/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeRecord() throws Exception {
        // Get the employeeRecord
        restEmployeeRecordMockMvc.perform(get("/api/employee-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeRecord() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        int databaseSizeBeforeUpdate = employeeRecordRepository.findAll().size();

        // Update the employeeRecord
        EmployeeRecord updatedEmployeeRecord = employeeRecordRepository.findById(employeeRecord.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeRecord are not directly saved in db
        em.detach(updatedEmployeeRecord);
        updatedEmployeeRecord
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address(UPDATED_ADDRESS)
            .gender(UPDATED_GENDER)
            .birthDate(UPDATED_BIRTH_DATE)
            .email(UPDATED_EMAIL)
            .cellPhone(UPDATED_CELL_PHONE)
            .telephone(UPDATED_TELEPHONE)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE)
            .employeeNumber(UPDATED_EMPLOYEE_NUMBER)
            .active(UPDATED_ACTIVE)
            .dateJoining(UPDATED_DATE_JOINING)
            .salary(UPDATED_SALARY)
            .contractStartDate(UPDATED_CONTRACT_START_DATE)
            .contractEndDate(UPDATED_CONTRACT_END_DATE)
            .bankName(UPDATED_BANK_NAME)
            .branchName(UPDATED_BRANCH_NAME)
            .bankAccount(UPDATED_BANK_ACCOUNT)
            .insuranceRegistrationNumber(UPDATED_INSURANCE_REGISTRATION_NUMBER);
        EmployeeRecordDTO employeeRecordDTO = employeeRecordMapper.toDto(updatedEmployeeRecord);

        restEmployeeRecordMockMvc.perform(put("/api/employee-records").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeRecordDTO)))
            .andExpect(status().isOk());

        // Validate the EmployeeRecord in the database
        List<EmployeeRecord> employeeRecordList = employeeRecordRepository.findAll();
        assertThat(employeeRecordList).hasSize(databaseSizeBeforeUpdate);
        EmployeeRecord testEmployeeRecord = employeeRecordList.get(employeeRecordList.size() - 1);
        assertThat(testEmployeeRecord.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testEmployeeRecord.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testEmployeeRecord.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testEmployeeRecord.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testEmployeeRecord.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testEmployeeRecord.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testEmployeeRecord.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployeeRecord.getCellPhone()).isEqualTo(UPDATED_CELL_PHONE);
        assertThat(testEmployeeRecord.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testEmployeeRecord.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testEmployeeRecord.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testEmployeeRecord.getPictureContentType()).isEqualTo(UPDATED_PICTURE_CONTENT_TYPE);
        assertThat(testEmployeeRecord.getEmployeeNumber()).isEqualTo(UPDATED_EMPLOYEE_NUMBER);
        assertThat(testEmployeeRecord.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testEmployeeRecord.getDateJoining()).isEqualTo(UPDATED_DATE_JOINING);
        assertThat(testEmployeeRecord.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testEmployeeRecord.getContractStartDate()).isEqualTo(UPDATED_CONTRACT_START_DATE);
        assertThat(testEmployeeRecord.getContractEndDate()).isEqualTo(UPDATED_CONTRACT_END_DATE);
        assertThat(testEmployeeRecord.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testEmployeeRecord.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testEmployeeRecord.getBankAccount()).isEqualTo(UPDATED_BANK_ACCOUNT);
        assertThat(testEmployeeRecord.getInsuranceRegistrationNumber()).isEqualTo(UPDATED_INSURANCE_REGISTRATION_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeRecord() throws Exception {
        int databaseSizeBeforeUpdate = employeeRecordRepository.findAll().size();

        // Create the EmployeeRecord
        EmployeeRecordDTO employeeRecordDTO = employeeRecordMapper.toDto(employeeRecord);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeRecordMockMvc.perform(put("/api/employee-records").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeRecordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeRecord in the database
        List<EmployeeRecord> employeeRecordList = employeeRecordRepository.findAll();
        assertThat(employeeRecordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeRecord() throws Exception {
        // Initialize the database
        employeeRecordRepository.saveAndFlush(employeeRecord);

        int databaseSizeBeforeDelete = employeeRecordRepository.findAll().size();

        // Delete the employeeRecord
        restEmployeeRecordMockMvc.perform(delete("/api/employee-records/{id}", employeeRecord.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeRecord> employeeRecordList = employeeRecordRepository.findAll();
        assertThat(employeeRecordList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
