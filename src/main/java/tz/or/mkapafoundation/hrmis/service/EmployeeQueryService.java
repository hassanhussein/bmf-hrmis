package tz.or.mkapafoundation.hrmis.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import tz.or.mkapafoundation.hrmis.domain.Employee;
import tz.or.mkapafoundation.hrmis.domain.*; // for static metamodels
import tz.or.mkapafoundation.hrmis.repository.EmployeeRepository;
import tz.or.mkapafoundation.hrmis.service.dto.EmployeeCriteria;
import tz.or.mkapafoundation.hrmis.service.dto.EmployeeDTO;
import tz.or.mkapafoundation.hrmis.service.mapper.EmployeeMapper;

/**
 * Service for executing complex queries for {@link Employee} entities in the database.
 * The main input is a {@link EmployeeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeDTO} or a {@link Page} of {@link EmployeeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeQueryService extends QueryService<Employee> {

    private final Logger log = LoggerFactory.getLogger(EmployeeQueryService.class);

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    public EmployeeQueryService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    /**
     * Return a {@link List} of {@link EmployeeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findByCriteria(EmployeeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeMapper.toDto(employeeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeDTO> findByCriteria(EmployeeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeRepository.findAll(specification, page)
            .map(employeeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Employee> createSpecification(EmployeeCriteria criteria) {
        Specification<Employee> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Employee_.id));
            }
            if (criteria.getEmployeeNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmployeeNumber(), Employee_.employeeNumber));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Employee_.firstName));
            }
            if (criteria.getMiddleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMiddleName(), Employee_.middleName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Employee_.lastName));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGender(), Employee_.gender));
            }
            if (criteria.getBirthDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBirthDate(), Employee_.birthDate));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Employee_.email));
            }
            if (criteria.getCellPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCellPhone(), Employee_.cellPhone));
            }
            if (criteria.getMaritalStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaritalStatus(), Employee_.maritalStatus));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), Employee_.active));
            }
            if (criteria.getContractStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContractStartDate(), Employee_.contractStartDate));
            }
            if (criteria.getContractEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContractEndDate(), Employee_.contractEndDate));
            }
            if (criteria.getBankName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankName(), Employee_.bankName));
            }
            if (criteria.getBranchName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchName(), Employee_.branchName));
            }
            if (criteria.getBankAccount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankAccount(), Employee_.bankAccount));
            }
            if (criteria.getInsuranceRegistrationNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInsuranceRegistrationNumber(), Employee_.insuranceRegistrationNumber));
            }
            if (criteria.getDistrictId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDistrictId(), Employee_.districtId));
            }
            if (criteria.getFacilityId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFacilityId(), Employee_.facilityId));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCategoryId(), Employee_.categoryId));
            }
            if (criteria.getTrainingId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTrainingId(), Employee_.trainingId));
            }
            if (criteria.getCarderId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCarderId(), Employee_.carderId));
            }
            if (criteria.getDepartMentCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDepartMentCode(), Employee_.departMentCode));
            }
            if (criteria.getAttachmentId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAttachmentId(), Employee_.attachmentId));
            }
            if (criteria.getConfirmationId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConfirmationId(), Employee_.confirmationId));
            }
            if (criteria.getProjectId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProjectId(), Employee_.projectId));
            }
            if (criteria.getDepartmentIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getDepartmentIdId(),
                    root -> root.join(Employee_.departmentId, JoinType.LEFT).get(Department_.id)));
            }
        }
        return specification;
    }
}
