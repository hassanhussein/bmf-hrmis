package tz.or.mkapafoundation.hrmis.web.rest;

import tz.or.mkapafoundation.hrmis.HrmisApp;
import tz.or.mkapafoundation.hrmis.config.TestSecurityConfiguration;
import tz.or.mkapafoundation.hrmis.domain.Employee;
import tz.or.mkapafoundation.hrmis.domain.Attachment;
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

    private static final LocalDate DEFAULT_DATE_JOINING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_JOINING = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_JOINING = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

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

    private static final String DEFAULT_DEPARTMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_CONFIRMATION_ID = 1L;
    private static final Long UPDATED_CONFIRMATION_ID = 2L;
    private static final Long SMALLER_CONFIRMATION_ID = 1L - 1L;

    private static final Boolean DEFAULT_IS_CONFIRMED = false;
    private static final Boolean UPDATED_IS_CONFIRMED = true;

    private static final String DEFAULT_CONFIRMATION_LETTER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONFIRMATION_LETTER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

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
            .contractStartDate(DEFAULT_CONTRACT_START_DATE)
            .contractEndDate(DEFAULT_CONTRACT_END_DATE)
            .bankName(DEFAULT_BANK_NAME)
            .branchName(DEFAULT_BRANCH_NAME)
            .bankAccount(DEFAULT_BANK_ACCOUNT)
            .insuranceRegistrationNumber(DEFAULT_INSURANCE_REGISTRATION_NUMBER)
            .dateJoining(DEFAULT_DATE_JOINING)
            .designation(DEFAULT_DESIGNATION)
            .districtId(DEFAULT_DISTRICT_ID)
            .facilityId(DEFAULT_FACILITY_ID)
            .categoryId(DEFAULT_CATEGORY_ID)
            .trainingId(DEFAULT_TRAINING_ID)
            .carderId(DEFAULT_CARDER_ID)
            .departmentName(DEFAULT_DEPARTMENT_NAME)
            .confirmationId(DEFAULT_CONFIRMATION_ID)
            .isConfirmed(DEFAULT_IS_CONFIRMED)
            .confirmationLetterName(DEFAULT_CONFIRMATION_LETTER_NAME)
            .projectName(DEFAULT_PROJECT_NAME)
            .active(DEFAULT_ACTIVE);
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
            .contractStartDate(UPDATED_CONTRACT_START_DATE)
            .contractEndDate(UPDATED_CONTRACT_END_DATE)
            .bankName(UPDATED_BANK_NAME)
            .branchName(UPDATED_BRANCH_NAME)
            .bankAccount(UPDATED_BANK_ACCOUNT)
            .insuranceRegistrationNumber(UPDATED_INSURANCE_REGISTRATION_NUMBER)
            .dateJoining(UPDATED_DATE_JOINING)
            .designation(UPDATED_DESIGNATION)
            .districtId(UPDATED_DISTRICT_ID)
            .facilityId(UPDATED_FACILITY_ID)
            .categoryId(UPDATED_CATEGORY_ID)
            .trainingId(UPDATED_TRAINING_ID)
            .carderId(UPDATED_CARDER_ID)
            .departmentName(UPDATED_DEPARTMENT_NAME)
            .confirmationId(UPDATED_CONFIRMATION_ID)
            .isConfirmed(UPDATED_IS_CONFIRMED)
            .confirmationLetterName(UPDATED_CONFIRMATION_LETTER_NAME)
            .projectName(UPDATED_PROJECT_NAME)
            .active(UPDATED_ACTIVE);
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
        assertThat(testEmployee.getContractStartDate()).isEqualTo(DEFAULT_CONTRACT_START_DATE);
        assertThat(testEmployee.getContractEndDate()).isEqualTo(DEFAULT_CONTRACT_END_DATE);
        assertThat(testEmployee.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testEmployee.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testEmployee.getBankAccount()).isEqualTo(DEFAULT_BANK_ACCOUNT);
        assertThat(testEmployee.getInsuranceRegistrationNumber()).isEqualTo(DEFAULT_INSURANCE_REGISTRATION_NUMBER);
        assertThat(testEmployee.getDateJoining()).isEqualTo(DEFAULT_DATE_JOINING);
        assertThat(testEmployee.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testEmployee.getDistrictId()).isEqualTo(DEFAULT_DISTRICT_ID);
        assertThat(testEmployee.getFacilityId()).isEqualTo(DEFAULT_FACILITY_ID);
        assertThat(testEmployee.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testEmployee.getTrainingId()).isEqualTo(DEFAULT_TRAINING_ID);
        assertThat(testEmployee.getCarderId()).isEqualTo(DEFAULT_CARDER_ID);
        assertThat(testEmployee.getDepartmentName()).isEqualTo(DEFAULT_DEPARTMENT_NAME);
        assertThat(testEmployee.getConfirmationId()).isEqualTo(DEFAULT_CONFIRMATION_ID);
        assertThat(testEmployee.isIsConfirmed()).isEqualTo(DEFAULT_IS_CONFIRMED);
        assertThat(testEmployee.getConfirmationLetterName()).isEqualTo(DEFAULT_CONFIRMATION_LETTER_NAME);
        assertThat(testEmployee.getProjectName()).isEqualTo(DEFAULT_PROJECT_NAME);
        assertThat(testEmployee.isActive()).isEqualTo(DEFAULT_ACTIVE);
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
    public void checkDistrictIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setDistrictId(null);

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
    public void checkCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setCategoryId(null);

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
            .andExpect(jsonPath("$.[*].contractStartDate").value(hasItem(DEFAULT_CONTRACT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].contractEndDate").value(hasItem(DEFAULT_CONTRACT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].bankAccount").value(hasItem(DEFAULT_BANK_ACCOUNT)))
            .andExpect(jsonPath("$.[*].insuranceRegistrationNumber").value(hasItem(DEFAULT_INSURANCE_REGISTRATION_NUMBER)))
            .andExpect(jsonPath("$.[*].dateJoining").value(hasItem(DEFAULT_DATE_JOINING.toString())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].districtId").value(hasItem(DEFAULT_DISTRICT_ID.intValue())))
            .andExpect(jsonPath("$.[*].facilityId").value(hasItem(DEFAULT_FACILITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].trainingId").value(hasItem(DEFAULT_TRAINING_ID.intValue())))
            .andExpect(jsonPath("$.[*].carderId").value(hasItem(DEFAULT_CARDER_ID.intValue())))
            .andExpect(jsonPath("$.[*].departmentName").value(hasItem(DEFAULT_DEPARTMENT_NAME)))
            .andExpect(jsonPath("$.[*].confirmationId").value(hasItem(DEFAULT_CONFIRMATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].isConfirmed").value(hasItem(DEFAULT_IS_CONFIRMED.booleanValue())))
            .andExpect(jsonPath("$.[*].confirmationLetterName").value(hasItem(DEFAULT_CONFIRMATION_LETTER_NAME)))
            .andExpect(jsonPath("$.[*].projectName").value(hasItem(DEFAULT_PROJECT_NAME)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
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
            .andExpect(jsonPath("$.contractStartDate").value(DEFAULT_CONTRACT_START_DATE.toString()))
            .andExpect(jsonPath("$.contractEndDate").value(DEFAULT_CONTRACT_END_DATE.toString()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME))
            .andExpect(jsonPath("$.bankAccount").value(DEFAULT_BANK_ACCOUNT))
            .andExpect(jsonPath("$.insuranceRegistrationNumber").value(DEFAULT_INSURANCE_REGISTRATION_NUMBER))
            .andExpect(jsonPath("$.dateJoining").value(DEFAULT_DATE_JOINING.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.districtId").value(DEFAULT_DISTRICT_ID.intValue()))
            .andExpect(jsonPath("$.facilityId").value(DEFAULT_FACILITY_ID.intValue()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.trainingId").value(DEFAULT_TRAINING_ID.intValue()))
            .andExpect(jsonPath("$.carderId").value(DEFAULT_CARDER_ID.intValue()))
            .andExpect(jsonPath("$.departmentName").value(DEFAULT_DEPARTMENT_NAME))
            .andExpect(jsonPath("$.confirmationId").value(DEFAULT_CONFIRMATION_ID.intValue()))
            .andExpect(jsonPath("$.isConfirmed").value(DEFAULT_IS_CONFIRMED.booleanValue()))
            .andExpect(jsonPath("$.confirmationLetterName").value(DEFAULT_CONFIRMATION_LETTER_NAME))
            .andExpect(jsonPath("$.projectName").value(DEFAULT_PROJECT_NAME))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
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
    public void getAllEmployeesByDateJoiningIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateJoining equals to DEFAULT_DATE_JOINING
        defaultEmployeeShouldBeFound("dateJoining.equals=" + DEFAULT_DATE_JOINING);

        // Get all the employeeList where dateJoining equals to UPDATED_DATE_JOINING
        defaultEmployeeShouldNotBeFound("dateJoining.equals=" + UPDATED_DATE_JOINING);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDateJoiningIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateJoining not equals to DEFAULT_DATE_JOINING
        defaultEmployeeShouldNotBeFound("dateJoining.notEquals=" + DEFAULT_DATE_JOINING);

        // Get all the employeeList where dateJoining not equals to UPDATED_DATE_JOINING
        defaultEmployeeShouldBeFound("dateJoining.notEquals=" + UPDATED_DATE_JOINING);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDateJoiningIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateJoining in DEFAULT_DATE_JOINING or UPDATED_DATE_JOINING
        defaultEmployeeShouldBeFound("dateJoining.in=" + DEFAULT_DATE_JOINING + "," + UPDATED_DATE_JOINING);

        // Get all the employeeList where dateJoining equals to UPDATED_DATE_JOINING
        defaultEmployeeShouldNotBeFound("dateJoining.in=" + UPDATED_DATE_JOINING);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDateJoiningIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateJoining is not null
        defaultEmployeeShouldBeFound("dateJoining.specified=true");

        // Get all the employeeList where dateJoining is null
        defaultEmployeeShouldNotBeFound("dateJoining.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByDateJoiningIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateJoining is greater than or equal to DEFAULT_DATE_JOINING
        defaultEmployeeShouldBeFound("dateJoining.greaterThanOrEqual=" + DEFAULT_DATE_JOINING);

        // Get all the employeeList where dateJoining is greater than or equal to UPDATED_DATE_JOINING
        defaultEmployeeShouldNotBeFound("dateJoining.greaterThanOrEqual=" + UPDATED_DATE_JOINING);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDateJoiningIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateJoining is less than or equal to DEFAULT_DATE_JOINING
        defaultEmployeeShouldBeFound("dateJoining.lessThanOrEqual=" + DEFAULT_DATE_JOINING);

        // Get all the employeeList where dateJoining is less than or equal to SMALLER_DATE_JOINING
        defaultEmployeeShouldNotBeFound("dateJoining.lessThanOrEqual=" + SMALLER_DATE_JOINING);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDateJoiningIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateJoining is less than DEFAULT_DATE_JOINING
        defaultEmployeeShouldNotBeFound("dateJoining.lessThan=" + DEFAULT_DATE_JOINING);

        // Get all the employeeList where dateJoining is less than UPDATED_DATE_JOINING
        defaultEmployeeShouldBeFound("dateJoining.lessThan=" + UPDATED_DATE_JOINING);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDateJoiningIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateJoining is greater than DEFAULT_DATE_JOINING
        defaultEmployeeShouldNotBeFound("dateJoining.greaterThan=" + DEFAULT_DATE_JOINING);

        // Get all the employeeList where dateJoining is greater than SMALLER_DATE_JOINING
        defaultEmployeeShouldBeFound("dateJoining.greaterThan=" + SMALLER_DATE_JOINING);
    }


    @Test
    @Transactional
    public void getAllEmployeesByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where designation equals to DEFAULT_DESIGNATION
        defaultEmployeeShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the employeeList where designation equals to UPDATED_DESIGNATION
        defaultEmployeeShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDesignationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where designation not equals to DEFAULT_DESIGNATION
        defaultEmployeeShouldNotBeFound("designation.notEquals=" + DEFAULT_DESIGNATION);

        // Get all the employeeList where designation not equals to UPDATED_DESIGNATION
        defaultEmployeeShouldBeFound("designation.notEquals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultEmployeeShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the employeeList where designation equals to UPDATED_DESIGNATION
        defaultEmployeeShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where designation is not null
        defaultEmployeeShouldBeFound("designation.specified=true");

        // Get all the employeeList where designation is null
        defaultEmployeeShouldNotBeFound("designation.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByDesignationContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where designation contains DEFAULT_DESIGNATION
        defaultEmployeeShouldBeFound("designation.contains=" + DEFAULT_DESIGNATION);

        // Get all the employeeList where designation contains UPDATED_DESIGNATION
        defaultEmployeeShouldNotBeFound("designation.contains=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDesignationNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where designation does not contain DEFAULT_DESIGNATION
        defaultEmployeeShouldNotBeFound("designation.doesNotContain=" + DEFAULT_DESIGNATION);

        // Get all the employeeList where designation does not contain UPDATED_DESIGNATION
        defaultEmployeeShouldBeFound("designation.doesNotContain=" + UPDATED_DESIGNATION);
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
    public void getAllEmployeesByDepartmentNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where departmentName equals to DEFAULT_DEPARTMENT_NAME
        defaultEmployeeShouldBeFound("departmentName.equals=" + DEFAULT_DEPARTMENT_NAME);

        // Get all the employeeList where departmentName equals to UPDATED_DEPARTMENT_NAME
        defaultEmployeeShouldNotBeFound("departmentName.equals=" + UPDATED_DEPARTMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDepartmentNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where departmentName not equals to DEFAULT_DEPARTMENT_NAME
        defaultEmployeeShouldNotBeFound("departmentName.notEquals=" + DEFAULT_DEPARTMENT_NAME);

        // Get all the employeeList where departmentName not equals to UPDATED_DEPARTMENT_NAME
        defaultEmployeeShouldBeFound("departmentName.notEquals=" + UPDATED_DEPARTMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDepartmentNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where departmentName in DEFAULT_DEPARTMENT_NAME or UPDATED_DEPARTMENT_NAME
        defaultEmployeeShouldBeFound("departmentName.in=" + DEFAULT_DEPARTMENT_NAME + "," + UPDATED_DEPARTMENT_NAME);

        // Get all the employeeList where departmentName equals to UPDATED_DEPARTMENT_NAME
        defaultEmployeeShouldNotBeFound("departmentName.in=" + UPDATED_DEPARTMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDepartmentNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where departmentName is not null
        defaultEmployeeShouldBeFound("departmentName.specified=true");

        // Get all the employeeList where departmentName is null
        defaultEmployeeShouldNotBeFound("departmentName.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByDepartmentNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where departmentName contains DEFAULT_DEPARTMENT_NAME
        defaultEmployeeShouldBeFound("departmentName.contains=" + DEFAULT_DEPARTMENT_NAME);

        // Get all the employeeList where departmentName contains UPDATED_DEPARTMENT_NAME
        defaultEmployeeShouldNotBeFound("departmentName.contains=" + UPDATED_DEPARTMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByDepartmentNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where departmentName does not contain DEFAULT_DEPARTMENT_NAME
        defaultEmployeeShouldNotBeFound("departmentName.doesNotContain=" + DEFAULT_DEPARTMENT_NAME);

        // Get all the employeeList where departmentName does not contain UPDATED_DEPARTMENT_NAME
        defaultEmployeeShouldBeFound("departmentName.doesNotContain=" + UPDATED_DEPARTMENT_NAME);
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
    public void getAllEmployeesByIsConfirmedIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where isConfirmed equals to DEFAULT_IS_CONFIRMED
        defaultEmployeeShouldBeFound("isConfirmed.equals=" + DEFAULT_IS_CONFIRMED);

        // Get all the employeeList where isConfirmed equals to UPDATED_IS_CONFIRMED
        defaultEmployeeShouldNotBeFound("isConfirmed.equals=" + UPDATED_IS_CONFIRMED);
    }

    @Test
    @Transactional
    public void getAllEmployeesByIsConfirmedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where isConfirmed not equals to DEFAULT_IS_CONFIRMED
        defaultEmployeeShouldNotBeFound("isConfirmed.notEquals=" + DEFAULT_IS_CONFIRMED);

        // Get all the employeeList where isConfirmed not equals to UPDATED_IS_CONFIRMED
        defaultEmployeeShouldBeFound("isConfirmed.notEquals=" + UPDATED_IS_CONFIRMED);
    }

    @Test
    @Transactional
    public void getAllEmployeesByIsConfirmedIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where isConfirmed in DEFAULT_IS_CONFIRMED or UPDATED_IS_CONFIRMED
        defaultEmployeeShouldBeFound("isConfirmed.in=" + DEFAULT_IS_CONFIRMED + "," + UPDATED_IS_CONFIRMED);

        // Get all the employeeList where isConfirmed equals to UPDATED_IS_CONFIRMED
        defaultEmployeeShouldNotBeFound("isConfirmed.in=" + UPDATED_IS_CONFIRMED);
    }

    @Test
    @Transactional
    public void getAllEmployeesByIsConfirmedIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where isConfirmed is not null
        defaultEmployeeShouldBeFound("isConfirmed.specified=true");

        // Get all the employeeList where isConfirmed is null
        defaultEmployeeShouldNotBeFound("isConfirmed.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByConfirmationLetterNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where confirmationLetterName equals to DEFAULT_CONFIRMATION_LETTER_NAME
        defaultEmployeeShouldBeFound("confirmationLetterName.equals=" + DEFAULT_CONFIRMATION_LETTER_NAME);

        // Get all the employeeList where confirmationLetterName equals to UPDATED_CONFIRMATION_LETTER_NAME
        defaultEmployeeShouldNotBeFound("confirmationLetterName.equals=" + UPDATED_CONFIRMATION_LETTER_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByConfirmationLetterNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where confirmationLetterName not equals to DEFAULT_CONFIRMATION_LETTER_NAME
        defaultEmployeeShouldNotBeFound("confirmationLetterName.notEquals=" + DEFAULT_CONFIRMATION_LETTER_NAME);

        // Get all the employeeList where confirmationLetterName not equals to UPDATED_CONFIRMATION_LETTER_NAME
        defaultEmployeeShouldBeFound("confirmationLetterName.notEquals=" + UPDATED_CONFIRMATION_LETTER_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByConfirmationLetterNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where confirmationLetterName in DEFAULT_CONFIRMATION_LETTER_NAME or UPDATED_CONFIRMATION_LETTER_NAME
        defaultEmployeeShouldBeFound("confirmationLetterName.in=" + DEFAULT_CONFIRMATION_LETTER_NAME + "," + UPDATED_CONFIRMATION_LETTER_NAME);

        // Get all the employeeList where confirmationLetterName equals to UPDATED_CONFIRMATION_LETTER_NAME
        defaultEmployeeShouldNotBeFound("confirmationLetterName.in=" + UPDATED_CONFIRMATION_LETTER_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByConfirmationLetterNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where confirmationLetterName is not null
        defaultEmployeeShouldBeFound("confirmationLetterName.specified=true");

        // Get all the employeeList where confirmationLetterName is null
        defaultEmployeeShouldNotBeFound("confirmationLetterName.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByConfirmationLetterNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where confirmationLetterName contains DEFAULT_CONFIRMATION_LETTER_NAME
        defaultEmployeeShouldBeFound("confirmationLetterName.contains=" + DEFAULT_CONFIRMATION_LETTER_NAME);

        // Get all the employeeList where confirmationLetterName contains UPDATED_CONFIRMATION_LETTER_NAME
        defaultEmployeeShouldNotBeFound("confirmationLetterName.contains=" + UPDATED_CONFIRMATION_LETTER_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByConfirmationLetterNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where confirmationLetterName does not contain DEFAULT_CONFIRMATION_LETTER_NAME
        defaultEmployeeShouldNotBeFound("confirmationLetterName.doesNotContain=" + DEFAULT_CONFIRMATION_LETTER_NAME);

        // Get all the employeeList where confirmationLetterName does not contain UPDATED_CONFIRMATION_LETTER_NAME
        defaultEmployeeShouldBeFound("confirmationLetterName.doesNotContain=" + UPDATED_CONFIRMATION_LETTER_NAME);
    }


    @Test
    @Transactional
    public void getAllEmployeesByProjectNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where projectName equals to DEFAULT_PROJECT_NAME
        defaultEmployeeShouldBeFound("projectName.equals=" + DEFAULT_PROJECT_NAME);

        // Get all the employeeList where projectName equals to UPDATED_PROJECT_NAME
        defaultEmployeeShouldNotBeFound("projectName.equals=" + UPDATED_PROJECT_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByProjectNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where projectName not equals to DEFAULT_PROJECT_NAME
        defaultEmployeeShouldNotBeFound("projectName.notEquals=" + DEFAULT_PROJECT_NAME);

        // Get all the employeeList where projectName not equals to UPDATED_PROJECT_NAME
        defaultEmployeeShouldBeFound("projectName.notEquals=" + UPDATED_PROJECT_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByProjectNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where projectName in DEFAULT_PROJECT_NAME or UPDATED_PROJECT_NAME
        defaultEmployeeShouldBeFound("projectName.in=" + DEFAULT_PROJECT_NAME + "," + UPDATED_PROJECT_NAME);

        // Get all the employeeList where projectName equals to UPDATED_PROJECT_NAME
        defaultEmployeeShouldNotBeFound("projectName.in=" + UPDATED_PROJECT_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByProjectNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where projectName is not null
        defaultEmployeeShouldBeFound("projectName.specified=true");

        // Get all the employeeList where projectName is null
        defaultEmployeeShouldNotBeFound("projectName.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByProjectNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where projectName contains DEFAULT_PROJECT_NAME
        defaultEmployeeShouldBeFound("projectName.contains=" + DEFAULT_PROJECT_NAME);

        // Get all the employeeList where projectName contains UPDATED_PROJECT_NAME
        defaultEmployeeShouldNotBeFound("projectName.contains=" + UPDATED_PROJECT_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByProjectNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where projectName does not contain DEFAULT_PROJECT_NAME
        defaultEmployeeShouldNotBeFound("projectName.doesNotContain=" + DEFAULT_PROJECT_NAME);

        // Get all the employeeList where projectName does not contain UPDATED_PROJECT_NAME
        defaultEmployeeShouldBeFound("projectName.doesNotContain=" + UPDATED_PROJECT_NAME);
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
    public void getAllEmployeesByAttachmentsIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        Attachment attachments = AttachmentResourceIT.createEntity(em);
        em.persist(attachments);
        em.flush();
        employee.addAttachments(attachments);
        employeeRepository.saveAndFlush(employee);
        Long attachmentsId = attachments.getId();

        // Get all the employeeList where attachments equals to attachmentsId
        defaultEmployeeShouldBeFound("attachmentsId.equals=" + attachmentsId);

        // Get all the employeeList where attachments equals to attachmentsId + 1
        defaultEmployeeShouldNotBeFound("attachmentsId.equals=" + (attachmentsId + 1));
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
            .andExpect(jsonPath("$.[*].contractStartDate").value(hasItem(DEFAULT_CONTRACT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].contractEndDate").value(hasItem(DEFAULT_CONTRACT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].bankAccount").value(hasItem(DEFAULT_BANK_ACCOUNT)))
            .andExpect(jsonPath("$.[*].insuranceRegistrationNumber").value(hasItem(DEFAULT_INSURANCE_REGISTRATION_NUMBER)))
            .andExpect(jsonPath("$.[*].dateJoining").value(hasItem(DEFAULT_DATE_JOINING.toString())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].districtId").value(hasItem(DEFAULT_DISTRICT_ID.intValue())))
            .andExpect(jsonPath("$.[*].facilityId").value(hasItem(DEFAULT_FACILITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].trainingId").value(hasItem(DEFAULT_TRAINING_ID.intValue())))
            .andExpect(jsonPath("$.[*].carderId").value(hasItem(DEFAULT_CARDER_ID.intValue())))
            .andExpect(jsonPath("$.[*].departmentName").value(hasItem(DEFAULT_DEPARTMENT_NAME)))
            .andExpect(jsonPath("$.[*].confirmationId").value(hasItem(DEFAULT_CONFIRMATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].isConfirmed").value(hasItem(DEFAULT_IS_CONFIRMED.booleanValue())))
            .andExpect(jsonPath("$.[*].confirmationLetterName").value(hasItem(DEFAULT_CONFIRMATION_LETTER_NAME)))
            .andExpect(jsonPath("$.[*].projectName").value(hasItem(DEFAULT_PROJECT_NAME)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

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
            .contractStartDate(UPDATED_CONTRACT_START_DATE)
            .contractEndDate(UPDATED_CONTRACT_END_DATE)
            .bankName(UPDATED_BANK_NAME)
            .branchName(UPDATED_BRANCH_NAME)
            .bankAccount(UPDATED_BANK_ACCOUNT)
            .insuranceRegistrationNumber(UPDATED_INSURANCE_REGISTRATION_NUMBER)
            .dateJoining(UPDATED_DATE_JOINING)
            .designation(UPDATED_DESIGNATION)
            .districtId(UPDATED_DISTRICT_ID)
            .facilityId(UPDATED_FACILITY_ID)
            .categoryId(UPDATED_CATEGORY_ID)
            .trainingId(UPDATED_TRAINING_ID)
            .carderId(UPDATED_CARDER_ID)
            .departmentName(UPDATED_DEPARTMENT_NAME)
            .confirmationId(UPDATED_CONFIRMATION_ID)
            .isConfirmed(UPDATED_IS_CONFIRMED)
            .confirmationLetterName(UPDATED_CONFIRMATION_LETTER_NAME)
            .projectName(UPDATED_PROJECT_NAME)
            .active(UPDATED_ACTIVE);
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
        assertThat(testEmployee.getContractStartDate()).isEqualTo(UPDATED_CONTRACT_START_DATE);
        assertThat(testEmployee.getContractEndDate()).isEqualTo(UPDATED_CONTRACT_END_DATE);
        assertThat(testEmployee.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testEmployee.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testEmployee.getBankAccount()).isEqualTo(UPDATED_BANK_ACCOUNT);
        assertThat(testEmployee.getInsuranceRegistrationNumber()).isEqualTo(UPDATED_INSURANCE_REGISTRATION_NUMBER);
        assertThat(testEmployee.getDateJoining()).isEqualTo(UPDATED_DATE_JOINING);
        assertThat(testEmployee.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testEmployee.getDistrictId()).isEqualTo(UPDATED_DISTRICT_ID);
        assertThat(testEmployee.getFacilityId()).isEqualTo(UPDATED_FACILITY_ID);
        assertThat(testEmployee.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testEmployee.getTrainingId()).isEqualTo(UPDATED_TRAINING_ID);
        assertThat(testEmployee.getCarderId()).isEqualTo(UPDATED_CARDER_ID);
        assertThat(testEmployee.getDepartmentName()).isEqualTo(UPDATED_DEPARTMENT_NAME);
        assertThat(testEmployee.getConfirmationId()).isEqualTo(UPDATED_CONFIRMATION_ID);
        assertThat(testEmployee.isIsConfirmed()).isEqualTo(UPDATED_IS_CONFIRMED);
        assertThat(testEmployee.getConfirmationLetterName()).isEqualTo(UPDATED_CONFIRMATION_LETTER_NAME);
        assertThat(testEmployee.getProjectName()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testEmployee.isActive()).isEqualTo(UPDATED_ACTIVE);
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
