package tz.or.mkapafoundation.hrmis.web.rest;

import tz.or.mkapafoundation.hrmis.HrmisApp;
import tz.or.mkapafoundation.hrmis.config.TestSecurityConfiguration;
import tz.or.mkapafoundation.hrmis.domain.Employee;
import tz.or.mkapafoundation.hrmis.domain.Department;
import tz.or.mkapafoundation.hrmis.repository.EmployeeRepository;
import tz.or.mkapafoundation.hrmis.service.EmployeeService;
import tz.or.mkapafoundation.hrmis.service.dto.EmployeeDTO;
import tz.or.mkapafoundation.hrmis.service.mapper.EmployeeMapper;
import tz.or.mkapafoundation.hrmis.service.dto.EmployeeCriteria;
import tz.or.mkapafoundation.hrmis.service.EmployeeQueryService;

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
 * Integration tests for the {@link EmployeeResource} REST controller.
 */
@SpringBootTest(classes = { HrmisApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class EmployeeResourceIT {

    private static final String DEFAULT_EMPLOYEE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_BIRTH_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CELL_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_CELL_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_MARITAL_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_MARITAL_STATUS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

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

    private static final Long DEFAULT_DISTRICT_ID = 1L;
    private static final Long UPDATED_DISTRICT_ID = 2L;
    private static final Long SMALLER_DISTRICT_ID = 1L - 1L;

    private static final Long DEFAULT_FACILITY_ID = 1L;
    private static final Long UPDATED_FACILITY_ID = 2L;
    private static final Long SMALLER_FACILITY_ID = 1L - 1L;

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;
    private static final Long SMALLER_CATEGORY_ID = 1L - 1L;

    private static final Long DEFAULT_TRAINING_ID = 1L;
    private static final Long UPDATED_TRAINING_ID = 2L;
    private static final Long SMALLER_TRAINING_ID = 1L - 1L;

    private static final Long DEFAULT_CARDER_ID = 1L;
    private static final Long UPDATED_CARDER_ID = 2L;
    private static final Long SMALLER_CARDER_ID = 1L - 1L;

    private static final byte[] DEFAULT_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PICTURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PICTURE_CONTENT_TYPE = "image/png";

    private static final Long DEFAULT_DEPART_MENT_CODE = 1L;
    private static final Long UPDATED_DEPART_MENT_CODE = 2L;
    private static final Long SMALLER_DEPART_MENT_CODE = 1L - 1L;

    private static final Long DEFAULT_ATTACHMENT_ID = 1L;
    private static final Long UPDATED_ATTACHMENT_ID = 2L;
    private static final Long SMALLER_ATTACHMENT_ID = 1L - 1L;

    private static final Long DEFAULT_CONFIRMATION_ID = 1L;
    private static final Long UPDATED_CONFIRMATION_ID = 2L;
    private static final Long SMALLER_CONFIRMATION_ID = 1L - 1L;

    private static final Long DEFAULT_PROJECT_ID = 1L;
    private static final Long UPDATED_PROJECT_ID = 2L;
    private static final Long SMALLER_PROJECT_ID = 1L - 1L;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeQueryService employeeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createEntity(EntityManager em) {
        Employee employee = new Employee()
            .employeeNumber(DEFAULT_EMPLOYEE_NUMBER)
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .gender(DEFAULT_GENDER)
            .birthDate(DEFAULT_BIRTH_DATE)
            .email(DEFAULT_EMAIL)
            .cellPhone(DEFAULT_CELL_PHONE)
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .active(DEFAULT_ACTIVE)
            .contractStartDate(DEFAULT_CONTRACT_START_DATE)
            .contractEndDate(DEFAULT_CONTRACT_END_DATE)
            .bankName(DEFAULT_BANK_NAME)
            .branchName(DEFAULT_BRANCH_NAME)
            .bankAccount(DEFAULT_BANK_ACCOUNT)
            .insuranceRegistrationNumber(DEFAULT_INSURANCE_REGISTRATION_NUMBER)
            .districtId(DEFAULT_DISTRICT_ID)
            .facilityId(DEFAULT_FACILITY_ID)
            .categoryId(DEFAULT_CATEGORY_ID)
            .trainingId(DEFAULT_TRAINING_ID)
            .carderId(DEFAULT_CARDER_ID)
            .picture(DEFAULT_PICTURE)
            .pictureContentType(DEFAULT_PICTURE_CONTENT_TYPE)
            .departMentCode(DEFAULT_DEPART_MENT_CODE)
            .attachmentId(DEFAULT_ATTACHMENT_ID)
            .confirmationId(DEFAULT_CONFIRMATION_ID)
            .projectId(DEFAULT_PROJECT_ID);
        return employee;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createUpdatedEntity(EntityManager em) {
        Employee employee = new Employee()
            .employeeNumber(UPDATED_EMPLOYEE_NUMBER)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .gender(UPDATED_GENDER)
            .birthDate(UPDATED_BIRTH_DATE)
            .email(UPDATED_EMAIL)
            .cellPhone(UPDATED_CELL_PHONE)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .active(UPDATED_ACTIVE)
            .contractStartDate(UPDATED_CONTRACT_START_DATE)
            .contractEndDate(UPDATED_CONTRACT_END_DATE)
            .bankName(UPDATED_BANK_NAME)
            .branchName(UPDATED_BRANCH_NAME)
            .bankAccount(UPDATED_BANK_ACCOUNT)
            .insuranceRegistrationNumber(UPDATED_INSURANCE_REGISTRATION_NUMBER)
            .districtId(UPDATED_DISTRICT_ID)
            .facilityId(UPDATED_FACILITY_ID)
            .categoryId(UPDATED_CATEGORY_ID)
            .trainingId(UPDATED_TRAINING_ID)
            .carderId(UPDATED_CARDER_ID)
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE)
            .departMentCode(UPDATED_DEPART_MENT_CODE)
            .attachmentId(UPDATED_ATTACHMENT_ID)
            .confirmationId(UPDATED_CONFIRMATION_ID)
            .projectId(UPDATED_PROJECT_ID);
        return employee;
    }

    @BeforeEach
    public void initTest() {
        employee = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployee() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();
        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);
        restEmployeeMockMvc.perform(post("/api/employees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate + 1);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getEmployeeNumber()).isEqualTo(DEFAULT_EMPLOYEE_NUMBER);
        assertThat(testEmployee.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testEmployee.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testEmployee.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testEmployee.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testEmployee.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testEmployee.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmployee.getCellPhone()).isEqualTo(DEFAULT_CELL_PHONE);
        assertThat(testEmployee.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testEmployee.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testEmployee.getContractStartDate()).isEqualTo(DEFAULT_CONTRACT_START_DATE);
        assertThat(testEmployee.getContractEndDate()).isEqualTo(DEFAULT_CONTRACT_END_DATE);
        assertThat(testEmployee.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testEmployee.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testEmployee.getBankAccount()).isEqualTo(DEFAULT_BANK_ACCOUNT);
        assertThat(testEmployee.getInsuranceRegistrationNumber()).isEqualTo(DEFAULT_INSURANCE_REGISTRATION_NUMBER);
        assertThat(testEmployee.getDistrictId()).isEqualTo(DEFAULT_DISTRICT_ID);
        assertThat(testEmployee.getFacilityId()).isEqualTo(DEFAULT_FACILITY_ID);
        assertThat(testEmployee.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testEmployee.getTrainingId()).isEqualTo(DEFAULT_TRAINING_ID);
        assertThat(testEmployee.getCarderId()).isEqualTo(DEFAULT_CARDER_ID);
        assertThat(testEmployee.getPicture()).isEqualTo(DEFAULT_PICTURE);
        assertThat(testEmployee.getPictureContentType()).isEqualTo(DEFAULT_PICTURE_CONTENT_TYPE);
        assertThat(testEmployee.getDepartMentCode()).isEqualTo(DEFAULT_DEPART_MENT_CODE);
        assertThat(testEmployee.getAttachmentId()).isEqualTo(DEFAULT_ATTACHMENT_ID);
        assertThat(testEmployee.getConfirmationId()).isEqualTo(DEFAULT_CONFIRMATION_ID);
        assertThat(testEmployee.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
    }

    @Test
    @Transactional
    public void createEmployeeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // Create the Employee with an existing ID
        employee.setId(1L);
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeMockMvc.perform(post("/api/employees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEmployeeNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setEmployeeNumber(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);


        restEmployeeMockMvc.perform(post("/api/employees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployees() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeNumber").value(hasItem(DEFAULT_EMPLOYEE_NUMBER)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].cellPhone").value(hasItem(DEFAULT_CELL_PHONE)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].contractStartDate").value(hasItem(DEFAULT_CONTRACT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].contractEndDate").value(hasItem(DEFAULT_CONTRACT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].bankAccount").value(hasItem(DEFAULT_BANK_ACCOUNT)))
            .andExpect(jsonPath("$.[*].insuranceRegistrationNumber").value(hasItem(DEFAULT_INSURANCE_REGISTRATION_NUMBER)))
            .andExpect(jsonPath("$.[*].districtId").value(hasItem(DEFAULT_DISTRICT_ID.intValue())))
            .andExpect(jsonPath("$.[*].facilityId").value(hasItem(DEFAULT_FACILITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].trainingId").value(hasItem(DEFAULT_TRAINING_ID.intValue())))
            .andExpect(jsonPath("$.[*].carderId").value(hasItem(DEFAULT_CARDER_ID.intValue())))
            .andExpect(jsonPath("$.[*].pictureContentType").value(hasItem(DEFAULT_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(Base64Utils.encodeToString(DEFAULT_PICTURE))))
            .andExpect(jsonPath("$.[*].departMentCode").value(hasItem(DEFAULT_DEPART_MENT_CODE.intValue())))
            .andExpect(jsonPath("$.[*].attachmentId").value(hasItem(DEFAULT_ATTACHMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].confirmationId").value(hasItem(DEFAULT_CONFIRMATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
            .andExpect(jsonPath("$.employeeNumber").value(DEFAULT_EMPLOYEE_NUMBER))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.cellPhone").value(DEFAULT_CELL_PHONE))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.contractStartDate").value(DEFAULT_CONTRACT_START_DATE.toString()))
            .andExpect(jsonPath("$.contractEndDate").value(DEFAULT_CONTRACT_END_DATE.toString()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME))
            .andExpect(jsonPath("$.bankAccount").value(DEFAULT_BANK_ACCOUNT))
            .andExpect(jsonPath("$.insuranceRegistrationNumber").value(DEFAULT_INSURANCE_REGISTRATION_NUMBER))
            .andExpect(jsonPath("$.districtId").value(DEFAULT_DISTRICT_ID.intValue()))
            .andExpect(jsonPath("$.facilityId").value(DEFAULT_FACILITY_ID.intValue()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.trainingId").value(DEFAULT_TRAINING_ID.intValue()))
            .andExpect(jsonPath("$.carderId").value(DEFAULT_CARDER_ID.intValue()))
            .andExpect(jsonPath("$.pictureContentType").value(DEFAULT_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.picture").value(Base64Utils.encodeToString(DEFAULT_PICTURE)))
            .andExpect(jsonPath("$.departMentCode").value(DEFAULT_DEPART_MENT_CODE.intValue()))
            .andExpect(jsonPath("$.attachmentId").value(DEFAULT_ATTACHMENT_ID.intValue()))
            .andExpect(jsonPath("$.confirmationId").value(DEFAULT_CONFIRMATION_ID.intValue()))
            .andExpect(jsonPath("$.projectId").value(DEFAULT_PROJECT_ID.intValue()));
    }


    @Test
    @Transactional
    public void getEmployeesByIdFiltering() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        Long id = employee.getId();

        defaultEmployeeShouldBeFound("id.equals=" + id);
        defaultEmployeeShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmployeesByEmployeeNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeNumber equals to DEFAULT_EMPLOYEE_NUMBER
        defaultEmployeeShouldBeFound("employeeNumber.equals=" + DEFAULT_EMPLOYEE_NUMBER);

        // Get all the employeeList where employeeNumber equals to UPDATED_EMPLOYEE_NUMBER
        defaultEmployeeShouldNotBeFound("employeeNumber.equals=" + UPDATED_EMPLOYEE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeNumber not equals to DEFAULT_EMPLOYEE_NUMBER
        defaultEmployeeShouldNotBeFound("employeeNumber.notEquals=" + DEFAULT_EMPLOYEE_NUMBER);

        // Get all the employeeList where employeeNumber not equals to UPDATED_EMPLOYEE_NUMBER
        defaultEmployeeShouldBeFound("employeeNumber.notEquals=" + UPDATED_EMPLOYEE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeNumberIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeNumber in DEFAULT_EMPLOYEE_NUMBER or UPDATED_EMPLOYEE_NUMBER
        defaultEmployeeShouldBeFound("employeeNumber.in=" + DEFAULT_EMPLOYEE_NUMBER + "," + UPDATED_EMPLOYEE_NUMBER);

        // Get all the employeeList where employeeNumber equals to UPDATED_EMPLOYEE_NUMBER
        defaultEmployeeShouldNotBeFound("employeeNumber.in=" + UPDATED_EMPLOYEE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeNumber is not null
        defaultEmployeeShouldBeFound("employeeNumber.specified=true");

        // Get all the employeeList where employeeNumber is null
        defaultEmployeeShouldNotBeFound("employeeNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByEmployeeNumberContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeNumber contains DEFAULT_EMPLOYEE_NUMBER
        defaultEmployeeShouldBeFound("employeeNumber.contains=" + DEFAULT_EMPLOYEE_NUMBER);

        // Get all the employeeList where employeeNumber contains UPDATED_EMPLOYEE_NUMBER
        defaultEmployeeShouldNotBeFound("employeeNumber.contains=" + UPDATED_EMPLOYEE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeNumberNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeNumber does not contain DEFAULT_EMPLOYEE_NUMBER
        defaultEmployeeShouldNotBeFound("employeeNumber.doesNotContain=" + DEFAULT_EMPLOYEE_NUMBER);

        // Get all the employeeList where employeeNumber does not contain UPDATED_EMPLOYEE_NUMBER
        defaultEmployeeShouldBeFound("employeeNumber.doesNotContain=" + UPDATED_EMPLOYEE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllEmployeesByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName equals to DEFAULT_FIRST_NAME
        defaultEmployeeShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the employeeList where firstName equals to UPDATED_FIRST_NAME
        defaultEmployeeShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName not equals to DEFAULT_FIRST_NAME
        defaultEmployeeShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the employeeList where firstName not equals to UPDATED_FIRST_NAME
        defaultEmployeeShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultEmployeeShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the employeeList where firstName equals to UPDATED_FIRST_NAME
        defaultEmployeeShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName is not null
        defaultEmployeeShouldBeFound("firstName.specified=true");

        // Get all the employeeList where firstName is null
        defaultEmployeeShouldNotBeFound("firstName.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName contains DEFAULT_FIRST_NAME
        defaultEmployeeShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the employeeList where firstName contains UPDATED_FIRST_NAME
        defaultEmployeeShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName does not contain DEFAULT_FIRST_NAME
        defaultEmployeeShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the employeeList where firstName does not contain UPDATED_FIRST_NAME
        defaultEmployeeShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }


    @Test
    @Transactional
    public void getAllEmployeesByMiddleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where middleName equals to DEFAULT_MIDDLE_NAME
        defaultEmployeeShouldBeFound("middleName.equals=" + DEFAULT_MIDDLE_NAME);

        // Get all the employeeList where middleName equals to UPDATED_MIDDLE_NAME
        defaultEmployeeShouldNotBeFound("middleName.equals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByMiddleNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where middleName not equals to DEFAULT_MIDDLE_NAME
        defaultEmployeeShouldNotBeFound("middleName.notEquals=" + DEFAULT_MIDDLE_NAME);

        // Get all the employeeList where middleName not equals to UPDATED_MIDDLE_NAME
        defaultEmployeeShouldBeFound("middleName.notEquals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByMiddleNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where middleName in DEFAULT_MIDDLE_NAME or UPDATED_MIDDLE_NAME
        defaultEmployeeShouldBeFound("middleName.in=" + DEFAULT_MIDDLE_NAME + "," + UPDATED_MIDDLE_NAME);

        // Get all the employeeList where middleName equals to UPDATED_MIDDLE_NAME
        defaultEmployeeShouldNotBeFound("middleName.in=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByMiddleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where middleName is not null
        defaultEmployeeShouldBeFound("middleName.specified=true");

        // Get all the employeeList where middleName is null
        defaultEmployeeShouldNotBeFound("middleName.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByMiddleNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where middleName contains DEFAULT_MIDDLE_NAME
        defaultEmployeeShouldBeFound("middleName.contains=" + DEFAULT_MIDDLE_NAME);

        // Get all the employeeList where middleName contains UPDATED_MIDDLE_NAME
        defaultEmployeeShouldNotBeFound("middleName.contains=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByMiddleNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where middleName does not contain DEFAULT_MIDDLE_NAME
        defaultEmployeeShouldNotBeFound("middleName.doesNotContain=" + DEFAULT_MIDDLE_NAME);

        // Get all the employeeList where middleName does not contain UPDATED_MIDDLE_NAME
        defaultEmployeeShouldBeFound("middleName.doesNotContain=" + UPDATED_MIDDLE_NAME);
    }


    @Test
    @Transactional
    public void getAllEmployeesByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName equals to DEFAULT_LAST_NAME
        defaultEmployeeShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the employeeList where lastName equals to UPDATED_LAST_NAME
        defaultEmployeeShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName not equals to DEFAULT_LAST_NAME
        defaultEmployeeShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the employeeList where lastName not equals to UPDATED_LAST_NAME
        defaultEmployeeShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultEmployeeShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the employeeList where lastName equals to UPDATED_LAST_NAME
        defaultEmployeeShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName is not null
        defaultEmployeeShouldBeFound("lastName.specified=true");

        // Get all the employeeList where lastName is null
        defaultEmployeeShouldNotBeFound("lastName.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByLastNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName contains DEFAULT_LAST_NAME
        defaultEmployeeShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the employeeList where lastName contains UPDATED_LAST_NAME
        defaultEmployeeShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName does not contain DEFAULT_LAST_NAME
        defaultEmployeeShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the employeeList where lastName does not contain UPDATED_LAST_NAME
        defaultEmployeeShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }


    @Test
    @Transactional
    public void getAllEmployeesByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gender equals to DEFAULT_GENDER
        defaultEmployeeShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the employeeList where gender equals to UPDATED_GENDER
        defaultEmployeeShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllEmployeesByGenderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gender not equals to DEFAULT_GENDER
        defaultEmployeeShouldNotBeFound("gender.notEquals=" + DEFAULT_GENDER);

        // Get all the employeeList where gender not equals to UPDATED_GENDER
        defaultEmployeeShouldBeFound("gender.notEquals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllEmployeesByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultEmployeeShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the employeeList where gender equals to UPDATED_GENDER
        defaultEmployeeShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllEmployeesByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gender is not null
        defaultEmployeeShouldBeFound("gender.specified=true");

        // Get all the employeeList where gender is null
        defaultEmployeeShouldNotBeFound("gender.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByGenderContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gender contains DEFAULT_GENDER
        defaultEmployeeShouldBeFound("gender.contains=" + DEFAULT_GENDER);

        // Get all the employeeList where gender contains UPDATED_GENDER
        defaultEmployeeShouldNotBeFound("gender.contains=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllEmployeesByGenderNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gender does not contain DEFAULT_GENDER
        defaultEmployeeShouldNotBeFound("gender.doesNotContain=" + DEFAULT_GENDER);

        // Get all the employeeList where gender does not contain UPDATED_GENDER
        defaultEmployeeShouldBeFound("gender.doesNotContain=" + UPDATED_GENDER);
    }


    @Test
    @Transactional
    public void getAllEmployeesByBirthDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthDate equals to DEFAULT_BIRTH_DATE
        defaultEmployeeShouldBeFound("birthDate.equals=" + DEFAULT_BIRTH_DATE);

        // Get all the employeeList where birthDate equals to UPDATED_BIRTH_DATE
        defaultEmployeeShouldNotBeFound("birthDate.equals=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBirthDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthDate not equals to DEFAULT_BIRTH_DATE
        defaultEmployeeShouldNotBeFound("birthDate.notEquals=" + DEFAULT_BIRTH_DATE);

        // Get all the employeeList where birthDate not equals to UPDATED_BIRTH_DATE
        defaultEmployeeShouldBeFound("birthDate.notEquals=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBirthDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthDate in DEFAULT_BIRTH_DATE or UPDATED_BIRTH_DATE
        defaultEmployeeShouldBeFound("birthDate.in=" + DEFAULT_BIRTH_DATE + "," + UPDATED_BIRTH_DATE);

        // Get all the employeeList where birthDate equals to UPDATED_BIRTH_DATE
        defaultEmployeeShouldNotBeFound("birthDate.in=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBirthDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthDate is not null
        defaultEmployeeShouldBeFound("birthDate.specified=true");

        // Get all the employeeList where birthDate is null
        defaultEmployeeShouldNotBeFound("birthDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByBirthDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthDate is greater than or equal to DEFAULT_BIRTH_DATE
        defaultEmployeeShouldBeFound("birthDate.greaterThanOrEqual=" + DEFAULT_BIRTH_DATE);

        // Get all the employeeList where birthDate is greater than or equal to UPDATED_BIRTH_DATE
        defaultEmployeeShouldNotBeFound("birthDate.greaterThanOrEqual=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBirthDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthDate is less than or equal to DEFAULT_BIRTH_DATE
        defaultEmployeeShouldBeFound("birthDate.lessThanOrEqual=" + DEFAULT_BIRTH_DATE);

        // Get all the employeeList where birthDate is less than or equal to SMALLER_BIRTH_DATE
        defaultEmployeeShouldNotBeFound("birthDate.lessThanOrEqual=" + SMALLER_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBirthDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthDate is less than DEFAULT_BIRTH_DATE
        defaultEmployeeShouldNotBeFound("birthDate.lessThan=" + DEFAULT_BIRTH_DATE);

        // Get all the employeeList where birthDate is less than UPDATED_BIRTH_DATE
        defaultEmployeeShouldBeFound("birthDate.lessThan=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBirthDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthDate is greater than DEFAULT_BIRTH_DATE
        defaultEmployeeShouldNotBeFound("birthDate.greaterThan=" + DEFAULT_BIRTH_DATE);

        // Get all the employeeList where birthDate is greater than SMALLER_BIRTH_DATE
        defaultEmployeeShouldBeFound("birthDate.greaterThan=" + SMALLER_BIRTH_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email equals to DEFAULT_EMAIL
        defaultEmployeeShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the employeeList where email equals to UPDATED_EMAIL
        defaultEmployeeShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email not equals to DEFAULT_EMAIL
        defaultEmployeeShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the employeeList where email not equals to UPDATED_EMAIL
        defaultEmployeeShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultEmployeeShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the employeeList where email equals to UPDATED_EMAIL
        defaultEmployeeShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email is not null
        defaultEmployeeShouldBeFound("email.specified=true");

        // Get all the employeeList where email is null
        defaultEmployeeShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByEmailContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email contains DEFAULT_EMAIL
        defaultEmployeeShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the employeeList where email contains UPDATED_EMAIL
        defaultEmployeeShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email does not contain DEFAULT_EMAIL
        defaultEmployeeShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the employeeList where email does not contain UPDATED_EMAIL
        defaultEmployeeShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllEmployeesByCellPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where cellPhone equals to DEFAULT_CELL_PHONE
        defaultEmployeeShouldBeFound("cellPhone.equals=" + DEFAULT_CELL_PHONE);

        // Get all the employeeList where cellPhone equals to UPDATED_CELL_PHONE
        defaultEmployeeShouldNotBeFound("cellPhone.equals=" + UPDATED_CELL_PHONE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCellPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where cellPhone not equals to DEFAULT_CELL_PHONE
        defaultEmployeeShouldNotBeFound("cellPhone.notEquals=" + DEFAULT_CELL_PHONE);

        // Get all the employeeList where cellPhone not equals to UPDATED_CELL_PHONE
        defaultEmployeeShouldBeFound("cellPhone.notEquals=" + UPDATED_CELL_PHONE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCellPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where cellPhone in DEFAULT_CELL_PHONE or UPDATED_CELL_PHONE
        defaultEmployeeShouldBeFound("cellPhone.in=" + DEFAULT_CELL_PHONE + "," + UPDATED_CELL_PHONE);

        // Get all the employeeList where cellPhone equals to UPDATED_CELL_PHONE
        defaultEmployeeShouldNotBeFound("cellPhone.in=" + UPDATED_CELL_PHONE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCellPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where cellPhone is not null
        defaultEmployeeShouldBeFound("cellPhone.specified=true");

        // Get all the employeeList where cellPhone is null
        defaultEmployeeShouldNotBeFound("cellPhone.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByCellPhoneContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where cellPhone contains DEFAULT_CELL_PHONE
        defaultEmployeeShouldBeFound("cellPhone.contains=" + DEFAULT_CELL_PHONE);

        // Get all the employeeList where cellPhone contains UPDATED_CELL_PHONE
        defaultEmployeeShouldNotBeFound("cellPhone.contains=" + UPDATED_CELL_PHONE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCellPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where cellPhone does not contain DEFAULT_CELL_PHONE
        defaultEmployeeShouldNotBeFound("cellPhone.doesNotContain=" + DEFAULT_CELL_PHONE);

        // Get all the employeeList where cellPhone does not contain UPDATED_CELL_PHONE
        defaultEmployeeShouldBeFound("cellPhone.doesNotContain=" + UPDATED_CELL_PHONE);
    }


    @Test
    @Transactional
    public void getAllEmployeesByMaritalStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where maritalStatus equals to DEFAULT_MARITAL_STATUS
        defaultEmployeeShouldBeFound("maritalStatus.equals=" + DEFAULT_MARITAL_STATUS);

        // Get all the employeeList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultEmployeeShouldNotBeFound("maritalStatus.equals=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByMaritalStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where maritalStatus not equals to DEFAULT_MARITAL_STATUS
        defaultEmployeeShouldNotBeFound("maritalStatus.notEquals=" + DEFAULT_MARITAL_STATUS);

        // Get all the employeeList where maritalStatus not equals to UPDATED_MARITAL_STATUS
        defaultEmployeeShouldBeFound("maritalStatus.notEquals=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByMaritalStatusIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where maritalStatus in DEFAULT_MARITAL_STATUS or UPDATED_MARITAL_STATUS
        defaultEmployeeShouldBeFound("maritalStatus.in=" + DEFAULT_MARITAL_STATUS + "," + UPDATED_MARITAL_STATUS);

        // Get all the employeeList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultEmployeeShouldNotBeFound("maritalStatus.in=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByMaritalStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where maritalStatus is not null
        defaultEmployeeShouldBeFound("maritalStatus.specified=true");

        // Get all the employeeList where maritalStatus is null
        defaultEmployeeShouldNotBeFound("maritalStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByMaritalStatusContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where maritalStatus contains DEFAULT_MARITAL_STATUS
        defaultEmployeeShouldBeFound("maritalStatus.contains=" + DEFAULT_MARITAL_STATUS);

        // Get all the employeeList where maritalStatus contains UPDATED_MARITAL_STATUS
        defaultEmployeeShouldNotBeFound("maritalStatus.contains=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByMaritalStatusNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where maritalStatus does not contain DEFAULT_MARITAL_STATUS
        defaultEmployeeShouldNotBeFound("maritalStatus.doesNotContain=" + DEFAULT_MARITAL_STATUS);

        // Get all the employeeList where maritalStatus does not contain UPDATED_MARITAL_STATUS
        defaultEmployeeShouldBeFound("maritalStatus.doesNotContain=" + UPDATED_MARITAL_STATUS);
    }


    @Test
    @Transactional
    public void getAllEmployeesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where active equals to DEFAULT_ACTIVE
        defaultEmployeeShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the employeeList where active equals to UPDATED_ACTIVE
        defaultEmployeeShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where active not equals to DEFAULT_ACTIVE
        defaultEmployeeShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the employeeList where active not equals to UPDATED_ACTIVE
        defaultEmployeeShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultEmployeeShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the employeeList where active equals to UPDATED_ACTIVE
        defaultEmployeeShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where active is not null
        defaultEmployeeShouldBeFound("active.specified=true");

        // Get all the employeeList where active is null
        defaultEmployeeShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByContractStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where contractStartDate equals to DEFAULT_CONTRACT_START_DATE
        defaultEmployeeShouldBeFound("contractStartDate.equals=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the employeeList where contractStartDate equals to UPDATED_CONTRACT_START_DATE
        defaultEmployeeShouldNotBeFound("contractStartDate.equals=" + UPDATED_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByContractStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where contractStartDate not equals to DEFAULT_CONTRACT_START_DATE
        defaultEmployeeShouldNotBeFound("contractStartDate.notEquals=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the employeeList where contractStartDate not equals to UPDATED_CONTRACT_START_DATE
        defaultEmployeeShouldBeFound("contractStartDate.notEquals=" + UPDATED_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByContractStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where contractStartDate in DEFAULT_CONTRACT_START_DATE or UPDATED_CONTRACT_START_DATE
        defaultEmployeeShouldBeFound("contractStartDate.in=" + DEFAULT_CONTRACT_START_DATE + "," + UPDATED_CONTRACT_START_DATE);

        // Get all the employeeList where contractStartDate equals to UPDATED_CONTRACT_START_DATE
        defaultEmployeeShouldNotBeFound("contractStartDate.in=" + UPDATED_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByContractStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where contractStartDate is not null
        defaultEmployeeShouldBeFound("contractStartDate.specified=true");

        // Get all the employeeList where contractStartDate is null
        defaultEmployeeShouldNotBeFound("contractStartDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByContractStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where contractStartDate is greater than or equal to DEFAULT_CONTRACT_START_DATE
        defaultEmployeeShouldBeFound("contractStartDate.greaterThanOrEqual=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the employeeList where contractStartDate is greater than or equal to UPDATED_CONTRACT_START_DATE
        defaultEmployeeShouldNotBeFound("contractStartDate.greaterThanOrEqual=" + UPDATED_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByContractStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where contractStartDate is less than or equal to DEFAULT_CONTRACT_START_DATE
        defaultEmployeeShouldBeFound("contractStartDate.lessThanOrEqual=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the employeeList where contractStartDate is less than or equal to SMALLER_CONTRACT_START_DATE
        defaultEmployeeShouldNotBeFound("contractStartDate.lessThanOrEqual=" + SMALLER_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByContractStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where contractStartDate is less than DEFAULT_CONTRACT_START_DATE
        defaultEmployeeShouldNotBeFound("contractStartDate.lessThan=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the employeeList where contractStartDate is less than UPDATED_CONTRACT_START_DATE
        defaultEmployeeShouldBeFound("contractStartDate.lessThan=" + UPDATED_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByContractStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where contractStartDate is greater than DEFAULT_CONTRACT_START_DATE
        defaultEmployeeShouldNotBeFound("contractStartDate.greaterThan=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the employeeList where contractStartDate is greater than SMALLER_CONTRACT_START_DATE
        defaultEmployeeShouldBeFound("contractStartDate.greaterThan=" + SMALLER_CONTRACT_START_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeesByContractEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where contractEndDate equals to DEFAULT_CONTRACT_END_DATE
        defaultEmployeeShouldBeFound("contractEndDate.equals=" + DEFAULT_CONTRACT_END_DATE);

        // Get all the employeeList where contractEndDate equals to UPDATED_CONTRACT_END_DATE
        defaultEmployeeShouldNotBeFound("contractEndDate.equals=" + UPDATED_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByContractEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where contractEndDate not equals to DEFAULT_CONTRACT_END_DATE
        defaultEmployeeShouldNotBeFound("contractEndDate.notEquals=" + DEFAULT_CONTRACT_END_DATE);

        // Get all the employeeList where contractEndDate not equals to UPDATED_CONTRACT_END_DATE
        defaultEmployeeShouldBeFound("contractEndDate.notEquals=" + UPDATED_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByContractEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where contractEndDate in DEFAULT_CONTRACT_END_DATE or UPDATED_CONTRACT_END_DATE
        defaultEmployeeShouldBeFound("contractEndDate.in=" + DEFAULT_CONTRACT_END_DATE + "," + UPDATED_CONTRACT_END_DATE);

        // Get all the employeeList where contractEndDate equals to UPDATED_CONTRACT_END_DATE
        defaultEmployeeShouldNotBeFound("contractEndDate.in=" + UPDATED_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByContractEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where contractEndDate is not null
        defaultEmployeeShouldBeFound("contractEndDate.specified=true");

        // Get all the employeeList where contractEndDate is null
        defaultEmployeeShouldNotBeFound("contractEndDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByContractEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where contractEndDate is greater than or equal to DEFAULT_CONTRACT_END_DATE
        defaultEmployeeShouldBeFound("contractEndDate.greaterThanOrEqual=" + DEFAULT_CONTRACT_END_DATE);

        // Get all the employeeList where contractEndDate is greater than or equal to UPDATED_CONTRACT_END_DATE
        defaultEmployeeShouldNotBeFound("contractEndDate.greaterThanOrEqual=" + UPDATED_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByContractEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where contractEndDate is less than or equal to DEFAULT_CONTRACT_END_DATE
        defaultEmployeeShouldBeFound("contractEndDate.lessThanOrEqual=" + DEFAULT_CONTRACT_END_DATE);

        // Get all the employeeList where contractEndDate is less than or equal to SMALLER_CONTRACT_END_DATE
        defaultEmployeeShouldNotBeFound("contractEndDate.lessThanOrEqual=" + SMALLER_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByContractEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where contractEndDate is less than DEFAULT_CONTRACT_END_DATE
        defaultEmployeeShouldNotBeFound("contractEndDate.lessThan=" + DEFAULT_CONTRACT_END_DATE);

        // Get all the employeeList where contractEndDate is less than UPDATED_CONTRACT_END_DATE
        defaultEmployeeShouldBeFound("contractEndDate.lessThan=" + UPDATED_CONTRACT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByContractEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where contractEndDate is greater than DEFAULT_CONTRACT_END_DATE
        defaultEmployeeShouldNotBeFound("contractEndDate.greaterThan=" + DEFAULT_CONTRACT_END_DATE);

        // Get all the employeeList where contractEndDate is greater than SMALLER_CONTRACT_END_DATE
        defaultEmployeeShouldBeFound("contractEndDate.greaterThan=" + SMALLER_CONTRACT_END_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeesByBankNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankName equals to DEFAULT_BANK_NAME
        defaultEmployeeShouldBeFound("bankName.equals=" + DEFAULT_BANK_NAME);

        // Get all the employeeList where bankName equals to UPDATED_BANK_NAME
        defaultEmployeeShouldNotBeFound("bankName.equals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBankNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankName not equals to DEFAULT_BANK_NAME
        defaultEmployeeShouldNotBeFound("bankName.notEquals=" + DEFAULT_BANK_NAME);

        // Get all the employeeList where bankName not equals to UPDATED_BANK_NAME
        defaultEmployeeShouldBeFound("bankName.notEquals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBankNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankName in DEFAULT_BANK_NAME or UPDATED_BANK_NAME
        defaultEmployeeShouldBeFound("bankName.in=" + DEFAULT_BANK_NAME + "," + UPDATED_BANK_NAME);

        // Get all the employeeList where bankName equals to UPDATED_BANK_NAME
        defaultEmployeeShouldNotBeFound("bankName.in=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBankNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankName is not null
        defaultEmployeeShouldBeFound("bankName.specified=true");

        // Get all the employeeList where bankName is null
        defaultEmployeeShouldNotBeFound("bankName.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByBankNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankName contains DEFAULT_BANK_NAME
        defaultEmployeeShouldBeFound("bankName.contains=" + DEFAULT_BANK_NAME);

        // Get all the employeeList where bankName contains UPDATED_BANK_NAME
        defaultEmployeeShouldNotBeFound("bankName.contains=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBankNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankName does not contain DEFAULT_BANK_NAME
        defaultEmployeeShouldNotBeFound("bankName.doesNotContain=" + DEFAULT_BANK_NAME);

        // Get all the employeeList where bankName does not contain UPDATED_BANK_NAME
        defaultEmployeeShouldBeFound("bankName.doesNotContain=" + UPDATED_BANK_NAME);
    }


    @Test
    @Transactional
    public void getAllEmployeesByBranchNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where branchName equals to DEFAULT_BRANCH_NAME
        defaultEmployeeShouldBeFound("branchName.equals=" + DEFAULT_BRANCH_NAME);

        // Get all the employeeList where branchName equals to UPDATED_BRANCH_NAME
        defaultEmployeeShouldNotBeFound("branchName.equals=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBranchNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where branchName not equals to DEFAULT_BRANCH_NAME
        defaultEmployeeShouldNotBeFound("branchName.notEquals=" + DEFAULT_BRANCH_NAME);

        // Get all the employeeList where branchName not equals to UPDATED_BRANCH_NAME
        defaultEmployeeShouldBeFound("branchName.notEquals=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBranchNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where branchName in DEFAULT_BRANCH_NAME or UPDATED_BRANCH_NAME
        defaultEmployeeShouldBeFound("branchName.in=" + DEFAULT_BRANCH_NAME + "," + UPDATED_BRANCH_NAME);

        // Get all the employeeList where branchName equals to UPDATED_BRANCH_NAME
        defaultEmployeeShouldNotBeFound("branchName.in=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBranchNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where branchName is not null
        defaultEmployeeShouldBeFound("branchName.specified=true");

        // Get all the employeeList where branchName is null
        defaultEmployeeShouldNotBeFound("branchName.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByBranchNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where branchName contains DEFAULT_BRANCH_NAME
        defaultEmployeeShouldBeFound("branchName.contains=" + DEFAULT_BRANCH_NAME);

        // Get all the employeeList where branchName contains UPDATED_BRANCH_NAME
        defaultEmployeeShouldNotBeFound("branchName.contains=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBranchNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where branchName does not contain DEFAULT_BRANCH_NAME
        defaultEmployeeShouldNotBeFound("branchName.doesNotContain=" + DEFAULT_BRANCH_NAME);

        // Get all the employeeList where branchName does not contain UPDATED_BRANCH_NAME
        defaultEmployeeShouldBeFound("branchName.doesNotContain=" + UPDATED_BRANCH_NAME);
    }


    @Test
    @Transactional
    public void getAllEmployeesByBankAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankAccount equals to DEFAULT_BANK_ACCOUNT
        defaultEmployeeShouldBeFound("bankAccount.equals=" + DEFAULT_BANK_ACCOUNT);

        // Get all the employeeList where bankAccount equals to UPDATED_BANK_ACCOUNT
        defaultEmployeeShouldNotBeFound("bankAccount.equals=" + UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBankAccountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankAccount not equals to DEFAULT_BANK_ACCOUNT
        defaultEmployeeShouldNotBeFound("bankAccount.notEquals=" + DEFAULT_BANK_ACCOUNT);

        // Get all the employeeList where bankAccount not equals to UPDATED_BANK_ACCOUNT
        defaultEmployeeShouldBeFound("bankAccount.notEquals=" + UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBankAccountIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankAccount in DEFAULT_BANK_ACCOUNT or UPDATED_BANK_ACCOUNT
        defaultEmployeeShouldBeFound("bankAccount.in=" + DEFAULT_BANK_ACCOUNT + "," + UPDATED_BANK_ACCOUNT);

        // Get all the employeeList where bankAccount equals to UPDATED_BANK_ACCOUNT
        defaultEmployeeShouldNotBeFound("bankAccount.in=" + UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBankAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankAccount is not null
        defaultEmployeeShouldBeFound("bankAccount.specified=true");

        // Get all the employeeList where bankAccount is null
        defaultEmployeeShouldNotBeFound("bankAccount.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByBankAccountContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankAccount contains DEFAULT_BANK_ACCOUNT
        defaultEmployeeShouldBeFound("bankAccount.contains=" + DEFAULT_BANK_ACCOUNT);

        // Get all the employeeList where bankAccount contains UPDATED_BANK_ACCOUNT
        defaultEmployeeShouldNotBeFound("bankAccount.contains=" + UPDATED_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBankAccountNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankAccount does not contain DEFAULT_BANK_ACCOUNT
        defaultEmployeeShouldNotBeFound("bankAccount.doesNotContain=" + DEFAULT_BANK_ACCOUNT);

        // Get all the employeeList where bankAccount does not contain UPDATED_BANK_ACCOUNT
        defaultEmployeeShouldBeFound("bankAccount.doesNotContain=" + UPDATED_BANK_ACCOUNT);
    }


    @Test
    @Transactional
    public void getAllEmployeesByInsuranceRegistrationNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where insuranceRegistrationNumber equals to DEFAULT_INSURANCE_REGISTRATION_NUMBER
        defaultEmployeeShouldBeFound("insuranceRegistrationNumber.equals=" + DEFAULT_INSURANCE_REGISTRATION_NUMBER);

        // Get all the employeeList where insuranceRegistrationNumber equals to UPDATED_INSURANCE_REGISTRATION_NUMBER
        defaultEmployeeShouldNotBeFound("insuranceRegistrationNumber.equals=" + UPDATED_INSURANCE_REGISTRATION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmployeesByInsuranceRegistrationNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where insuranceRegistrationNumber not equals to DEFAULT_INSURANCE_REGISTRATION_NUMBER
        defaultEmployeeShouldNotBeFound("insuranceRegistrationNumber.notEquals=" + DEFAULT_INSURANCE_REGISTRATION_NUMBER);

        // Get all the employeeList where insuranceRegistrationNumber not equals to UPDATED_INSURANCE_REGISTRATION_NUMBER
        defaultEmployeeShouldBeFound("insuranceRegistrationNumber.notEquals=" + UPDATED_INSURANCE_REGISTRATION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmployeesByInsuranceRegistrationNumberIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where insuranceRegistrationNumber in DEFAULT_INSURANCE_REGISTRATION_NUMBER or UPDATED_INSURANCE_REGISTRATION_NUMBER
        defaultEmployeeShouldBeFound("insuranceRegistrationNumber.in=" + DEFAULT_INSURANCE_REGISTRATION_NUMBER + "," + UPDATED_INSURANCE_REGISTRATION_NUMBER);

        // Get all the employeeList where insuranceRegistrationNumber equals to UPDATED_INSURANCE_REGISTRATION_NUMBER
        defaultEmployeeShouldNotBeFound("insuranceRegistrationNumber.in=" + UPDATED_INSURANCE_REGISTRATION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmployeesByInsuranceRegistrationNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where insuranceRegistrationNumber is not null
        defaultEmployeeShouldBeFound("insuranceRegistrationNumber.specified=true");

        // Get all the employeeList where insuranceRegistrationNumber is null
        defaultEmployeeShouldNotBeFound("insuranceRegistrationNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByInsuranceRegistrationNumberContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where insuranceRegistrationNumber contains DEFAULT_INSURANCE_REGISTRATION_NUMBER
        defaultEmployeeShouldBeFound("insuranceRegistrationNumber.contains=" + DEFAULT_INSURANCE_REGISTRATION_NUMBER);

        // Get all the employeeList where insuranceRegistrationNumber contains UPDATED_INSURANCE_REGISTRATION_NUMBER
        defaultEmployeeShouldNotBeFound("insuranceRegistrationNumber.contains=" + UPDATED_INSURANCE_REGISTRATION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmployeesByInsuranceRegistrationNumberNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where insuranceRegistrationNumber does not contain DEFAULT_INSURANCE_REGISTRATION_NUMBER
        defaultEmployeeShouldNotBeFound("insuranceRegistrationNumber.doesNotContain=" + DEFAULT_INSURANCE_REGISTRATION_NUMBER);

        // Get all the employeeList where insuranceRegistrationNumber does not contain UPDATED_INSURANCE_REGISTRATION_NUMBER
        defaultEmployeeShouldBeFound("insuranceRegistrationNumber.doesNotContain=" + UPDATED_INSURANCE_REGISTRATION_NUMBER);
    }


    @Test
    @Transactional
    public void getAllEmployeesByDistrictIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where districtId equals to DEFAULT_DISTRICT_ID
        defaultEmployeeShouldBeFound("districtId.equals=" + DEFAULT_DISTRICT_ID);

        // Get all the employeeList where districtId equals to UPDATED_DISTRICT_ID
        defaultEmployeeShouldNotBeFound("districtId.equals=" + UPDATED_DISTRICT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDistrictIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where districtId not equals to DEFAULT_DISTRICT_ID
        defaultEmployeeShouldNotBeFound("districtId.notEquals=" + DEFAULT_DISTRICT_ID);

        // Get all the employeeList where districtId not equals to UPDATED_DISTRICT_ID
        defaultEmployeeShouldBeFound("districtId.notEquals=" + UPDATED_DISTRICT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDistrictIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where districtId in DEFAULT_DISTRICT_ID or UPDATED_DISTRICT_ID
        defaultEmployeeShouldBeFound("districtId.in=" + DEFAULT_DISTRICT_ID + "," + UPDATED_DISTRICT_ID);

        // Get all the employeeList where districtId equals to UPDATED_DISTRICT_ID
        defaultEmployeeShouldNotBeFound("districtId.in=" + UPDATED_DISTRICT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDistrictIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where districtId is not null
        defaultEmployeeShouldBeFound("districtId.specified=true");

        // Get all the employeeList where districtId is null
        defaultEmployeeShouldNotBeFound("districtId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByDistrictIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where districtId is greater than or equal to DEFAULT_DISTRICT_ID
        defaultEmployeeShouldBeFound("districtId.greaterThanOrEqual=" + DEFAULT_DISTRICT_ID);

        // Get all the employeeList where districtId is greater than or equal to UPDATED_DISTRICT_ID
        defaultEmployeeShouldNotBeFound("districtId.greaterThanOrEqual=" + UPDATED_DISTRICT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDistrictIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where districtId is less than or equal to DEFAULT_DISTRICT_ID
        defaultEmployeeShouldBeFound("districtId.lessThanOrEqual=" + DEFAULT_DISTRICT_ID);

        // Get all the employeeList where districtId is less than or equal to SMALLER_DISTRICT_ID
        defaultEmployeeShouldNotBeFound("districtId.lessThanOrEqual=" + SMALLER_DISTRICT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDistrictIdIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where districtId is less than DEFAULT_DISTRICT_ID
        defaultEmployeeShouldNotBeFound("districtId.lessThan=" + DEFAULT_DISTRICT_ID);

        // Get all the employeeList where districtId is less than UPDATED_DISTRICT_ID
        defaultEmployeeShouldBeFound("districtId.lessThan=" + UPDATED_DISTRICT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDistrictIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where districtId is greater than DEFAULT_DISTRICT_ID
        defaultEmployeeShouldNotBeFound("districtId.greaterThan=" + DEFAULT_DISTRICT_ID);

        // Get all the employeeList where districtId is greater than SMALLER_DISTRICT_ID
        defaultEmployeeShouldBeFound("districtId.greaterThan=" + SMALLER_DISTRICT_ID);
    }


    @Test
    @Transactional
    public void getAllEmployeesByFacilityIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where facilityId equals to DEFAULT_FACILITY_ID
        defaultEmployeeShouldBeFound("facilityId.equals=" + DEFAULT_FACILITY_ID);

        // Get all the employeeList where facilityId equals to UPDATED_FACILITY_ID
        defaultEmployeeShouldNotBeFound("facilityId.equals=" + UPDATED_FACILITY_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFacilityIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where facilityId not equals to DEFAULT_FACILITY_ID
        defaultEmployeeShouldNotBeFound("facilityId.notEquals=" + DEFAULT_FACILITY_ID);

        // Get all the employeeList where facilityId not equals to UPDATED_FACILITY_ID
        defaultEmployeeShouldBeFound("facilityId.notEquals=" + UPDATED_FACILITY_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFacilityIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where facilityId in DEFAULT_FACILITY_ID or UPDATED_FACILITY_ID
        defaultEmployeeShouldBeFound("facilityId.in=" + DEFAULT_FACILITY_ID + "," + UPDATED_FACILITY_ID);

        // Get all the employeeList where facilityId equals to UPDATED_FACILITY_ID
        defaultEmployeeShouldNotBeFound("facilityId.in=" + UPDATED_FACILITY_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFacilityIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where facilityId is not null
        defaultEmployeeShouldBeFound("facilityId.specified=true");

        // Get all the employeeList where facilityId is null
        defaultEmployeeShouldNotBeFound("facilityId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByFacilityIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where facilityId is greater than or equal to DEFAULT_FACILITY_ID
        defaultEmployeeShouldBeFound("facilityId.greaterThanOrEqual=" + DEFAULT_FACILITY_ID);

        // Get all the employeeList where facilityId is greater than or equal to UPDATED_FACILITY_ID
        defaultEmployeeShouldNotBeFound("facilityId.greaterThanOrEqual=" + UPDATED_FACILITY_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFacilityIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where facilityId is less than or equal to DEFAULT_FACILITY_ID
        defaultEmployeeShouldBeFound("facilityId.lessThanOrEqual=" + DEFAULT_FACILITY_ID);

        // Get all the employeeList where facilityId is less than or equal to SMALLER_FACILITY_ID
        defaultEmployeeShouldNotBeFound("facilityId.lessThanOrEqual=" + SMALLER_FACILITY_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFacilityIdIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where facilityId is less than DEFAULT_FACILITY_ID
        defaultEmployeeShouldNotBeFound("facilityId.lessThan=" + DEFAULT_FACILITY_ID);

        // Get all the employeeList where facilityId is less than UPDATED_FACILITY_ID
        defaultEmployeeShouldBeFound("facilityId.lessThan=" + UPDATED_FACILITY_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFacilityIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where facilityId is greater than DEFAULT_FACILITY_ID
        defaultEmployeeShouldNotBeFound("facilityId.greaterThan=" + DEFAULT_FACILITY_ID);

        // Get all the employeeList where facilityId is greater than SMALLER_FACILITY_ID
        defaultEmployeeShouldBeFound("facilityId.greaterThan=" + SMALLER_FACILITY_ID);
    }


    @Test
    @Transactional
    public void getAllEmployeesByCategoryIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where categoryId equals to DEFAULT_CATEGORY_ID
        defaultEmployeeShouldBeFound("categoryId.equals=" + DEFAULT_CATEGORY_ID);

        // Get all the employeeList where categoryId equals to UPDATED_CATEGORY_ID
        defaultEmployeeShouldNotBeFound("categoryId.equals=" + UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCategoryIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where categoryId not equals to DEFAULT_CATEGORY_ID
        defaultEmployeeShouldNotBeFound("categoryId.notEquals=" + DEFAULT_CATEGORY_ID);

        // Get all the employeeList where categoryId not equals to UPDATED_CATEGORY_ID
        defaultEmployeeShouldBeFound("categoryId.notEquals=" + UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCategoryIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where categoryId in DEFAULT_CATEGORY_ID or UPDATED_CATEGORY_ID
        defaultEmployeeShouldBeFound("categoryId.in=" + DEFAULT_CATEGORY_ID + "," + UPDATED_CATEGORY_ID);

        // Get all the employeeList where categoryId equals to UPDATED_CATEGORY_ID
        defaultEmployeeShouldNotBeFound("categoryId.in=" + UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCategoryIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where categoryId is not null
        defaultEmployeeShouldBeFound("categoryId.specified=true");

        // Get all the employeeList where categoryId is null
        defaultEmployeeShouldNotBeFound("categoryId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByCategoryIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where categoryId is greater than or equal to DEFAULT_CATEGORY_ID
        defaultEmployeeShouldBeFound("categoryId.greaterThanOrEqual=" + DEFAULT_CATEGORY_ID);

        // Get all the employeeList where categoryId is greater than or equal to UPDATED_CATEGORY_ID
        defaultEmployeeShouldNotBeFound("categoryId.greaterThanOrEqual=" + UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCategoryIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where categoryId is less than or equal to DEFAULT_CATEGORY_ID
        defaultEmployeeShouldBeFound("categoryId.lessThanOrEqual=" + DEFAULT_CATEGORY_ID);

        // Get all the employeeList where categoryId is less than or equal to SMALLER_CATEGORY_ID
        defaultEmployeeShouldNotBeFound("categoryId.lessThanOrEqual=" + SMALLER_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCategoryIdIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where categoryId is less than DEFAULT_CATEGORY_ID
        defaultEmployeeShouldNotBeFound("categoryId.lessThan=" + DEFAULT_CATEGORY_ID);

        // Get all the employeeList where categoryId is less than UPDATED_CATEGORY_ID
        defaultEmployeeShouldBeFound("categoryId.lessThan=" + UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCategoryIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where categoryId is greater than DEFAULT_CATEGORY_ID
        defaultEmployeeShouldNotBeFound("categoryId.greaterThan=" + DEFAULT_CATEGORY_ID);

        // Get all the employeeList where categoryId is greater than SMALLER_CATEGORY_ID
        defaultEmployeeShouldBeFound("categoryId.greaterThan=" + SMALLER_CATEGORY_ID);
    }


    @Test
    @Transactional
    public void getAllEmployeesByTrainingIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where trainingId equals to DEFAULT_TRAINING_ID
        defaultEmployeeShouldBeFound("trainingId.equals=" + DEFAULT_TRAINING_ID);

        // Get all the employeeList where trainingId equals to UPDATED_TRAINING_ID
        defaultEmployeeShouldNotBeFound("trainingId.equals=" + UPDATED_TRAINING_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTrainingIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where trainingId not equals to DEFAULT_TRAINING_ID
        defaultEmployeeShouldNotBeFound("trainingId.notEquals=" + DEFAULT_TRAINING_ID);

        // Get all the employeeList where trainingId not equals to UPDATED_TRAINING_ID
        defaultEmployeeShouldBeFound("trainingId.notEquals=" + UPDATED_TRAINING_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTrainingIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where trainingId in DEFAULT_TRAINING_ID or UPDATED_TRAINING_ID
        defaultEmployeeShouldBeFound("trainingId.in=" + DEFAULT_TRAINING_ID + "," + UPDATED_TRAINING_ID);

        // Get all the employeeList where trainingId equals to UPDATED_TRAINING_ID
        defaultEmployeeShouldNotBeFound("trainingId.in=" + UPDATED_TRAINING_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTrainingIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where trainingId is not null
        defaultEmployeeShouldBeFound("trainingId.specified=true");

        // Get all the employeeList where trainingId is null
        defaultEmployeeShouldNotBeFound("trainingId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByTrainingIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where trainingId is greater than or equal to DEFAULT_TRAINING_ID
        defaultEmployeeShouldBeFound("trainingId.greaterThanOrEqual=" + DEFAULT_TRAINING_ID);

        // Get all the employeeList where trainingId is greater than or equal to UPDATED_TRAINING_ID
        defaultEmployeeShouldNotBeFound("trainingId.greaterThanOrEqual=" + UPDATED_TRAINING_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTrainingIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where trainingId is less than or equal to DEFAULT_TRAINING_ID
        defaultEmployeeShouldBeFound("trainingId.lessThanOrEqual=" + DEFAULT_TRAINING_ID);

        // Get all the employeeList where trainingId is less than or equal to SMALLER_TRAINING_ID
        defaultEmployeeShouldNotBeFound("trainingId.lessThanOrEqual=" + SMALLER_TRAINING_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTrainingIdIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where trainingId is less than DEFAULT_TRAINING_ID
        defaultEmployeeShouldNotBeFound("trainingId.lessThan=" + DEFAULT_TRAINING_ID);

        // Get all the employeeList where trainingId is less than UPDATED_TRAINING_ID
        defaultEmployeeShouldBeFound("trainingId.lessThan=" + UPDATED_TRAINING_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTrainingIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where trainingId is greater than DEFAULT_TRAINING_ID
        defaultEmployeeShouldNotBeFound("trainingId.greaterThan=" + DEFAULT_TRAINING_ID);

        // Get all the employeeList where trainingId is greater than SMALLER_TRAINING_ID
        defaultEmployeeShouldBeFound("trainingId.greaterThan=" + SMALLER_TRAINING_ID);
    }


    @Test
    @Transactional
    public void getAllEmployeesByCarderIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where carderId equals to DEFAULT_CARDER_ID
        defaultEmployeeShouldBeFound("carderId.equals=" + DEFAULT_CARDER_ID);

        // Get all the employeeList where carderId equals to UPDATED_CARDER_ID
        defaultEmployeeShouldNotBeFound("carderId.equals=" + UPDATED_CARDER_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCarderIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where carderId not equals to DEFAULT_CARDER_ID
        defaultEmployeeShouldNotBeFound("carderId.notEquals=" + DEFAULT_CARDER_ID);

        // Get all the employeeList where carderId not equals to UPDATED_CARDER_ID
        defaultEmployeeShouldBeFound("carderId.notEquals=" + UPDATED_CARDER_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCarderIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where carderId in DEFAULT_CARDER_ID or UPDATED_CARDER_ID
        defaultEmployeeShouldBeFound("carderId.in=" + DEFAULT_CARDER_ID + "," + UPDATED_CARDER_ID);

        // Get all the employeeList where carderId equals to UPDATED_CARDER_ID
        defaultEmployeeShouldNotBeFound("carderId.in=" + UPDATED_CARDER_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCarderIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where carderId is not null
        defaultEmployeeShouldBeFound("carderId.specified=true");

        // Get all the employeeList where carderId is null
        defaultEmployeeShouldNotBeFound("carderId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByCarderIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where carderId is greater than or equal to DEFAULT_CARDER_ID
        defaultEmployeeShouldBeFound("carderId.greaterThanOrEqual=" + DEFAULT_CARDER_ID);

        // Get all the employeeList where carderId is greater than or equal to UPDATED_CARDER_ID
        defaultEmployeeShouldNotBeFound("carderId.greaterThanOrEqual=" + UPDATED_CARDER_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCarderIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where carderId is less than or equal to DEFAULT_CARDER_ID
        defaultEmployeeShouldBeFound("carderId.lessThanOrEqual=" + DEFAULT_CARDER_ID);

        // Get all the employeeList where carderId is less than or equal to SMALLER_CARDER_ID
        defaultEmployeeShouldNotBeFound("carderId.lessThanOrEqual=" + SMALLER_CARDER_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCarderIdIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where carderId is less than DEFAULT_CARDER_ID
        defaultEmployeeShouldNotBeFound("carderId.lessThan=" + DEFAULT_CARDER_ID);

        // Get all the employeeList where carderId is less than UPDATED_CARDER_ID
        defaultEmployeeShouldBeFound("carderId.lessThan=" + UPDATED_CARDER_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCarderIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where carderId is greater than DEFAULT_CARDER_ID
        defaultEmployeeShouldNotBeFound("carderId.greaterThan=" + DEFAULT_CARDER_ID);

        // Get all the employeeList where carderId is greater than SMALLER_CARDER_ID
        defaultEmployeeShouldBeFound("carderId.greaterThan=" + SMALLER_CARDER_ID);
    }


    @Test
    @Transactional
    public void getAllEmployeesByDepartMentCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where departMentCode equals to DEFAULT_DEPART_MENT_CODE
        defaultEmployeeShouldBeFound("departMentCode.equals=" + DEFAULT_DEPART_MENT_CODE);

        // Get all the employeeList where departMentCode equals to UPDATED_DEPART_MENT_CODE
        defaultEmployeeShouldNotBeFound("departMentCode.equals=" + UPDATED_DEPART_MENT_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDepartMentCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where departMentCode not equals to DEFAULT_DEPART_MENT_CODE
        defaultEmployeeShouldNotBeFound("departMentCode.notEquals=" + DEFAULT_DEPART_MENT_CODE);

        // Get all the employeeList where departMentCode not equals to UPDATED_DEPART_MENT_CODE
        defaultEmployeeShouldBeFound("departMentCode.notEquals=" + UPDATED_DEPART_MENT_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDepartMentCodeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where departMentCode in DEFAULT_DEPART_MENT_CODE or UPDATED_DEPART_MENT_CODE
        defaultEmployeeShouldBeFound("departMentCode.in=" + DEFAULT_DEPART_MENT_CODE + "," + UPDATED_DEPART_MENT_CODE);

        // Get all the employeeList where departMentCode equals to UPDATED_DEPART_MENT_CODE
        defaultEmployeeShouldNotBeFound("departMentCode.in=" + UPDATED_DEPART_MENT_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDepartMentCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where departMentCode is not null
        defaultEmployeeShouldBeFound("departMentCode.specified=true");

        // Get all the employeeList where departMentCode is null
        defaultEmployeeShouldNotBeFound("departMentCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByDepartMentCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where departMentCode is greater than or equal to DEFAULT_DEPART_MENT_CODE
        defaultEmployeeShouldBeFound("departMentCode.greaterThanOrEqual=" + DEFAULT_DEPART_MENT_CODE);

        // Get all the employeeList where departMentCode is greater than or equal to UPDATED_DEPART_MENT_CODE
        defaultEmployeeShouldNotBeFound("departMentCode.greaterThanOrEqual=" + UPDATED_DEPART_MENT_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDepartMentCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where departMentCode is less than or equal to DEFAULT_DEPART_MENT_CODE
        defaultEmployeeShouldBeFound("departMentCode.lessThanOrEqual=" + DEFAULT_DEPART_MENT_CODE);

        // Get all the employeeList where departMentCode is less than or equal to SMALLER_DEPART_MENT_CODE
        defaultEmployeeShouldNotBeFound("departMentCode.lessThanOrEqual=" + SMALLER_DEPART_MENT_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDepartMentCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where departMentCode is less than DEFAULT_DEPART_MENT_CODE
        defaultEmployeeShouldNotBeFound("departMentCode.lessThan=" + DEFAULT_DEPART_MENT_CODE);

        // Get all the employeeList where departMentCode is less than UPDATED_DEPART_MENT_CODE
        defaultEmployeeShouldBeFound("departMentCode.lessThan=" + UPDATED_DEPART_MENT_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDepartMentCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where departMentCode is greater than DEFAULT_DEPART_MENT_CODE
        defaultEmployeeShouldNotBeFound("departMentCode.greaterThan=" + DEFAULT_DEPART_MENT_CODE);

        // Get all the employeeList where departMentCode is greater than SMALLER_DEPART_MENT_CODE
        defaultEmployeeShouldBeFound("departMentCode.greaterThan=" + SMALLER_DEPART_MENT_CODE);
    }


    @Test
    @Transactional
    public void getAllEmployeesByAttachmentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where attachmentId equals to DEFAULT_ATTACHMENT_ID
        defaultEmployeeShouldBeFound("attachmentId.equals=" + DEFAULT_ATTACHMENT_ID);

        // Get all the employeeList where attachmentId equals to UPDATED_ATTACHMENT_ID
        defaultEmployeeShouldNotBeFound("attachmentId.equals=" + UPDATED_ATTACHMENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByAttachmentIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where attachmentId not equals to DEFAULT_ATTACHMENT_ID
        defaultEmployeeShouldNotBeFound("attachmentId.notEquals=" + DEFAULT_ATTACHMENT_ID);

        // Get all the employeeList where attachmentId not equals to UPDATED_ATTACHMENT_ID
        defaultEmployeeShouldBeFound("attachmentId.notEquals=" + UPDATED_ATTACHMENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByAttachmentIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where attachmentId in DEFAULT_ATTACHMENT_ID or UPDATED_ATTACHMENT_ID
        defaultEmployeeShouldBeFound("attachmentId.in=" + DEFAULT_ATTACHMENT_ID + "," + UPDATED_ATTACHMENT_ID);

        // Get all the employeeList where attachmentId equals to UPDATED_ATTACHMENT_ID
        defaultEmployeeShouldNotBeFound("attachmentId.in=" + UPDATED_ATTACHMENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByAttachmentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where attachmentId is not null
        defaultEmployeeShouldBeFound("attachmentId.specified=true");

        // Get all the employeeList where attachmentId is null
        defaultEmployeeShouldNotBeFound("attachmentId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByAttachmentIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where attachmentId is greater than or equal to DEFAULT_ATTACHMENT_ID
        defaultEmployeeShouldBeFound("attachmentId.greaterThanOrEqual=" + DEFAULT_ATTACHMENT_ID);

        // Get all the employeeList where attachmentId is greater than or equal to UPDATED_ATTACHMENT_ID
        defaultEmployeeShouldNotBeFound("attachmentId.greaterThanOrEqual=" + UPDATED_ATTACHMENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByAttachmentIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where attachmentId is less than or equal to DEFAULT_ATTACHMENT_ID
        defaultEmployeeShouldBeFound("attachmentId.lessThanOrEqual=" + DEFAULT_ATTACHMENT_ID);

        // Get all the employeeList where attachmentId is less than or equal to SMALLER_ATTACHMENT_ID
        defaultEmployeeShouldNotBeFound("attachmentId.lessThanOrEqual=" + SMALLER_ATTACHMENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByAttachmentIdIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where attachmentId is less than DEFAULT_ATTACHMENT_ID
        defaultEmployeeShouldNotBeFound("attachmentId.lessThan=" + DEFAULT_ATTACHMENT_ID);

        // Get all the employeeList where attachmentId is less than UPDATED_ATTACHMENT_ID
        defaultEmployeeShouldBeFound("attachmentId.lessThan=" + UPDATED_ATTACHMENT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByAttachmentIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where attachmentId is greater than DEFAULT_ATTACHMENT_ID
        defaultEmployeeShouldNotBeFound("attachmentId.greaterThan=" + DEFAULT_ATTACHMENT_ID);

        // Get all the employeeList where attachmentId is greater than SMALLER_ATTACHMENT_ID
        defaultEmployeeShouldBeFound("attachmentId.greaterThan=" + SMALLER_ATTACHMENT_ID);
    }


    @Test
    @Transactional
    public void getAllEmployeesByConfirmationIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where confirmationId equals to DEFAULT_CONFIRMATION_ID
        defaultEmployeeShouldBeFound("confirmationId.equals=" + DEFAULT_CONFIRMATION_ID);

        // Get all the employeeList where confirmationId equals to UPDATED_CONFIRMATION_ID
        defaultEmployeeShouldNotBeFound("confirmationId.equals=" + UPDATED_CONFIRMATION_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByConfirmationIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where confirmationId not equals to DEFAULT_CONFIRMATION_ID
        defaultEmployeeShouldNotBeFound("confirmationId.notEquals=" + DEFAULT_CONFIRMATION_ID);

        // Get all the employeeList where confirmationId not equals to UPDATED_CONFIRMATION_ID
        defaultEmployeeShouldBeFound("confirmationId.notEquals=" + UPDATED_CONFIRMATION_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByConfirmationIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where confirmationId in DEFAULT_CONFIRMATION_ID or UPDATED_CONFIRMATION_ID
        defaultEmployeeShouldBeFound("confirmationId.in=" + DEFAULT_CONFIRMATION_ID + "," + UPDATED_CONFIRMATION_ID);

        // Get all the employeeList where confirmationId equals to UPDATED_CONFIRMATION_ID
        defaultEmployeeShouldNotBeFound("confirmationId.in=" + UPDATED_CONFIRMATION_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByConfirmationIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where confirmationId is not null
        defaultEmployeeShouldBeFound("confirmationId.specified=true");

        // Get all the employeeList where confirmationId is null
        defaultEmployeeShouldNotBeFound("confirmationId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByConfirmationIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where confirmationId is greater than or equal to DEFAULT_CONFIRMATION_ID
        defaultEmployeeShouldBeFound("confirmationId.greaterThanOrEqual=" + DEFAULT_CONFIRMATION_ID);

        // Get all the employeeList where confirmationId is greater than or equal to UPDATED_CONFIRMATION_ID
        defaultEmployeeShouldNotBeFound("confirmationId.greaterThanOrEqual=" + UPDATED_CONFIRMATION_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByConfirmationIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where confirmationId is less than or equal to DEFAULT_CONFIRMATION_ID
        defaultEmployeeShouldBeFound("confirmationId.lessThanOrEqual=" + DEFAULT_CONFIRMATION_ID);

        // Get all the employeeList where confirmationId is less than or equal to SMALLER_CONFIRMATION_ID
        defaultEmployeeShouldNotBeFound("confirmationId.lessThanOrEqual=" + SMALLER_CONFIRMATION_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByConfirmationIdIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where confirmationId is less than DEFAULT_CONFIRMATION_ID
        defaultEmployeeShouldNotBeFound("confirmationId.lessThan=" + DEFAULT_CONFIRMATION_ID);

        // Get all the employeeList where confirmationId is less than UPDATED_CONFIRMATION_ID
        defaultEmployeeShouldBeFound("confirmationId.lessThan=" + UPDATED_CONFIRMATION_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByConfirmationIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where confirmationId is greater than DEFAULT_CONFIRMATION_ID
        defaultEmployeeShouldNotBeFound("confirmationId.greaterThan=" + DEFAULT_CONFIRMATION_ID);

        // Get all the employeeList where confirmationId is greater than SMALLER_CONFIRMATION_ID
        defaultEmployeeShouldBeFound("confirmationId.greaterThan=" + SMALLER_CONFIRMATION_ID);
    }


    @Test
    @Transactional
    public void getAllEmployeesByProjectIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where projectId equals to DEFAULT_PROJECT_ID
        defaultEmployeeShouldBeFound("projectId.equals=" + DEFAULT_PROJECT_ID);

        // Get all the employeeList where projectId equals to UPDATED_PROJECT_ID
        defaultEmployeeShouldNotBeFound("projectId.equals=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByProjectIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where projectId not equals to DEFAULT_PROJECT_ID
        defaultEmployeeShouldNotBeFound("projectId.notEquals=" + DEFAULT_PROJECT_ID);

        // Get all the employeeList where projectId not equals to UPDATED_PROJECT_ID
        defaultEmployeeShouldBeFound("projectId.notEquals=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByProjectIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where projectId in DEFAULT_PROJECT_ID or UPDATED_PROJECT_ID
        defaultEmployeeShouldBeFound("projectId.in=" + DEFAULT_PROJECT_ID + "," + UPDATED_PROJECT_ID);

        // Get all the employeeList where projectId equals to UPDATED_PROJECT_ID
        defaultEmployeeShouldNotBeFound("projectId.in=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByProjectIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where projectId is not null
        defaultEmployeeShouldBeFound("projectId.specified=true");

        // Get all the employeeList where projectId is null
        defaultEmployeeShouldNotBeFound("projectId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByProjectIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where projectId is greater than or equal to DEFAULT_PROJECT_ID
        defaultEmployeeShouldBeFound("projectId.greaterThanOrEqual=" + DEFAULT_PROJECT_ID);

        // Get all the employeeList where projectId is greater than or equal to UPDATED_PROJECT_ID
        defaultEmployeeShouldNotBeFound("projectId.greaterThanOrEqual=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByProjectIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where projectId is less than or equal to DEFAULT_PROJECT_ID
        defaultEmployeeShouldBeFound("projectId.lessThanOrEqual=" + DEFAULT_PROJECT_ID);

        // Get all the employeeList where projectId is less than or equal to SMALLER_PROJECT_ID
        defaultEmployeeShouldNotBeFound("projectId.lessThanOrEqual=" + SMALLER_PROJECT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByProjectIdIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where projectId is less than DEFAULT_PROJECT_ID
        defaultEmployeeShouldNotBeFound("projectId.lessThan=" + DEFAULT_PROJECT_ID);

        // Get all the employeeList where projectId is less than UPDATED_PROJECT_ID
        defaultEmployeeShouldBeFound("projectId.lessThan=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByProjectIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where projectId is greater than DEFAULT_PROJECT_ID
        defaultEmployeeShouldNotBeFound("projectId.greaterThan=" + DEFAULT_PROJECT_ID);

        // Get all the employeeList where projectId is greater than SMALLER_PROJECT_ID
        defaultEmployeeShouldBeFound("projectId.greaterThan=" + SMALLER_PROJECT_ID);
    }


    @Test
    @Transactional
    public void getAllEmployeesByDepartmentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        Department departmentId = DepartmentResourceIT.createEntity(em);
        em.persist(departmentId);
        em.flush();
        employee.setDepartmentId(departmentId);
        employeeRepository.saveAndFlush(employee);
        Long departmentIdId = departmentId.getId();

        // Get all the employeeList where departmentId equals to departmentIdId
        defaultEmployeeShouldBeFound("departmentIdId.equals=" + departmentIdId);

        // Get all the employeeList where departmentId equals to departmentIdId + 1
        defaultEmployeeShouldNotBeFound("departmentIdId.equals=" + (departmentIdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeShouldBeFound(String filter) throws Exception {
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeNumber").value(hasItem(DEFAULT_EMPLOYEE_NUMBER)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].cellPhone").value(hasItem(DEFAULT_CELL_PHONE)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].contractStartDate").value(hasItem(DEFAULT_CONTRACT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].contractEndDate").value(hasItem(DEFAULT_CONTRACT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].bankAccount").value(hasItem(DEFAULT_BANK_ACCOUNT)))
            .andExpect(jsonPath("$.[*].insuranceRegistrationNumber").value(hasItem(DEFAULT_INSURANCE_REGISTRATION_NUMBER)))
            .andExpect(jsonPath("$.[*].districtId").value(hasItem(DEFAULT_DISTRICT_ID.intValue())))
            .andExpect(jsonPath("$.[*].facilityId").value(hasItem(DEFAULT_FACILITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].trainingId").value(hasItem(DEFAULT_TRAINING_ID.intValue())))
            .andExpect(jsonPath("$.[*].carderId").value(hasItem(DEFAULT_CARDER_ID.intValue())))
            .andExpect(jsonPath("$.[*].pictureContentType").value(hasItem(DEFAULT_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(Base64Utils.encodeToString(DEFAULT_PICTURE))))
            .andExpect(jsonPath("$.[*].departMentCode").value(hasItem(DEFAULT_DEPART_MENT_CODE.intValue())))
            .andExpect(jsonPath("$.[*].attachmentId").value(hasItem(DEFAULT_ATTACHMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].confirmationId").value(hasItem(DEFAULT_CONFIRMATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.intValue())));

        // Check, that the count call also returns 1
        restEmployeeMockMvc.perform(get("/api/employees/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeShouldNotBeFound(String filter) throws Exception {
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeMockMvc.perform(get("/api/employees/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee
        Employee updatedEmployee = employeeRepository.findById(employee.getId()).get();
        // Disconnect from session so that the updates on updatedEmployee are not directly saved in db
        em.detach(updatedEmployee);
        updatedEmployee
            .employeeNumber(UPDATED_EMPLOYEE_NUMBER)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .gender(UPDATED_GENDER)
            .birthDate(UPDATED_BIRTH_DATE)
            .email(UPDATED_EMAIL)
            .cellPhone(UPDATED_CELL_PHONE)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .active(UPDATED_ACTIVE)
            .contractStartDate(UPDATED_CONTRACT_START_DATE)
            .contractEndDate(UPDATED_CONTRACT_END_DATE)
            .bankName(UPDATED_BANK_NAME)
            .branchName(UPDATED_BRANCH_NAME)
            .bankAccount(UPDATED_BANK_ACCOUNT)
            .insuranceRegistrationNumber(UPDATED_INSURANCE_REGISTRATION_NUMBER)
            .districtId(UPDATED_DISTRICT_ID)
            .facilityId(UPDATED_FACILITY_ID)
            .categoryId(UPDATED_CATEGORY_ID)
            .trainingId(UPDATED_TRAINING_ID)
            .carderId(UPDATED_CARDER_ID)
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE)
            .departMentCode(UPDATED_DEPART_MENT_CODE)
            .attachmentId(UPDATED_ATTACHMENT_ID)
            .confirmationId(UPDATED_CONFIRMATION_ID)
            .projectId(UPDATED_PROJECT_ID);
        EmployeeDTO employeeDTO = employeeMapper.toDto(updatedEmployee);

        restEmployeeMockMvc.perform(put("/api/employees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getEmployeeNumber()).isEqualTo(UPDATED_EMPLOYEE_NUMBER);
        assertThat(testEmployee.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testEmployee.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testEmployee.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testEmployee.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testEmployee.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testEmployee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployee.getCellPhone()).isEqualTo(UPDATED_CELL_PHONE);
        assertThat(testEmployee.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testEmployee.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testEmployee.getContractStartDate()).isEqualTo(UPDATED_CONTRACT_START_DATE);
        assertThat(testEmployee.getContractEndDate()).isEqualTo(UPDATED_CONTRACT_END_DATE);
        assertThat(testEmployee.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testEmployee.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testEmployee.getBankAccount()).isEqualTo(UPDATED_BANK_ACCOUNT);
        assertThat(testEmployee.getInsuranceRegistrationNumber()).isEqualTo(UPDATED_INSURANCE_REGISTRATION_NUMBER);
        assertThat(testEmployee.getDistrictId()).isEqualTo(UPDATED_DISTRICT_ID);
        assertThat(testEmployee.getFacilityId()).isEqualTo(UPDATED_FACILITY_ID);
        assertThat(testEmployee.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testEmployee.getTrainingId()).isEqualTo(UPDATED_TRAINING_ID);
        assertThat(testEmployee.getCarderId()).isEqualTo(UPDATED_CARDER_ID);
        assertThat(testEmployee.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testEmployee.getPictureContentType()).isEqualTo(UPDATED_PICTURE_CONTENT_TYPE);
        assertThat(testEmployee.getDepartMentCode()).isEqualTo(UPDATED_DEPART_MENT_CODE);
        assertThat(testEmployee.getAttachmentId()).isEqualTo(UPDATED_ATTACHMENT_ID);
        assertThat(testEmployee.getConfirmationId()).isEqualTo(UPDATED_CONFIRMATION_ID);
        assertThat(testEmployee.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc.perform(put("/api/employees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeDelete = employeeRepository.findAll().size();

        // Delete the employee
        restEmployeeMockMvc.perform(delete("/api/employees/{id}", employee.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
