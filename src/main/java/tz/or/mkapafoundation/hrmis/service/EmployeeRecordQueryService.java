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

import tz.or.mkapafoundation.hrmis.domain.EmployeeRecord;
import tz.or.mkapafoundation.hrmis.domain.*; // for static metamodels
import tz.or.mkapafoundation.hrmis.repository.EmployeeRecordRepository;
import tz.or.mkapafoundation.hrmis.service.dto.EmployeeRecordCriteria;
import tz.or.mkapafoundation.hrmis.service.dto.EmployeeRecordDTO;
import tz.or.mkapafoundation.hrmis.service.mapper.EmployeeRecordMapper;

/**
 * Service for executing complex queries for {@link EmployeeRecord} entities in the database.
 * The main input is a {@link EmployeeRecordCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeRecordDTO} or a {@link Page} of {@link EmployeeRecordDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeRecordQueryService extends QueryService<EmployeeRecord> {

    private final Logger log = LoggerFactory.getLogger(EmployeeRecordQueryService.class);

    private final EmployeeRecordRepository employeeRecordRepository;

    private final EmployeeRecordMapper employeeRecordMapper;

    public EmployeeRecordQueryService(EmployeeRecordRepository employeeRecordRepository, EmployeeRecordMapper employeeRecordMapper) {
        this.employeeRecordRepository = employeeRecordRepository;
        this.employeeRecordMapper = employeeRecordMapper;
    }

    /**
     * Return a {@link List} of {@link EmployeeRecordDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeRecordDTO> findByCriteria(EmployeeRecordCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeRecord> specification = createSpecification(criteria);
        return employeeRecordMapper.toDto(employeeRecordRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeeRecordDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeRecordDTO> findByCriteria(EmployeeRecordCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeRecord> specification = createSpecification(criteria);
        return employeeRecordRepository.findAll(specification, page)
            .map(employeeRecordMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeRecordCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeRecord> specification = createSpecification(criteria);
        return employeeRecordRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeRecordCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeRecord> createSpecification(EmployeeRecordCriteria criteria) {
        Specification<EmployeeRecord> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeRecord_.id));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), EmployeeRecord_.firstName));
            }
            if (criteria.getMiddleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMiddleName(), EmployeeRecord_.middleName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), EmployeeRecord_.lastName));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), EmployeeRecord_.address));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGender(), EmployeeRecord_.gender));
            }
            if (criteria.getBirthDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBirthDate(), EmployeeRecord_.birthDate));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), EmployeeRecord_.email));
            }
            if (criteria.getCellPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCellPhone(), EmployeeRecord_.cellPhone));
            }
            if (criteria.getTelephone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelephone(), EmployeeRecord_.telephone));
            }
            if (criteria.getMaritalStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaritalStatus(), EmployeeRecord_.maritalStatus));
            }
            if (criteria.getEmployeeNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmployeeNumber(), EmployeeRecord_.employeeNumber));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), EmployeeRecord_.active));
            }
            if (criteria.getDateJoining() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateJoining(), EmployeeRecord_.dateJoining));
            }
            if (criteria.getSalary() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSalary(), EmployeeRecord_.salary));
            }
            if (criteria.getContractStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContractStartDate(), EmployeeRecord_.contractStartDate));
            }
            if (criteria.getContractEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContractEndDate(), EmployeeRecord_.contractEndDate));
            }
            if (criteria.getBankName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankName(), EmployeeRecord_.bankName));
            }
            if (criteria.getBranchName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchName(), EmployeeRecord_.branchName));
            }
            if (criteria.getBankAccount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankAccount(), EmployeeRecord_.bankAccount));
            }
            if (criteria.getInsuranceRegistrationNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInsuranceRegistrationNumber(), EmployeeRecord_.insuranceRegistrationNumber));
            }
            if (criteria.getAttachmentsId() != null) {
                specification = specification.and(buildSpecification(criteria.getAttachmentsId(),
                    root -> root.join(EmployeeRecord_.attachments, JoinType.LEFT).get(Attachment_.id)));
            }
            if (criteria.getDepartmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getDepartmentId(),
                    root -> root.join(EmployeeRecord_.department, JoinType.LEFT).get(Department_.id)));
            }
            if (criteria.getEmployeeTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeTypeId(),
                    root -> root.join(EmployeeRecord_.employeeType, JoinType.LEFT).get(EmploymentCategory_.id)));
            }
            if (criteria.getDesignationId() != null) {
                specification = specification.and(buildSpecification(criteria.getDesignationId(),
                    root -> root.join(EmployeeRecord_.designation, JoinType.LEFT).get(Carder_.id)));
            }
            if (criteria.getFacilityId() != null) {
                specification = specification.and(buildSpecification(criteria.getFacilityId(),
                    root -> root.join(EmployeeRecord_.facility, JoinType.LEFT).get(Facility_.id)));
            }
            if (criteria.getProjectId() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectId(),
                    root -> root.join(EmployeeRecord_.project, JoinType.LEFT).get(Project_.id)));
            }
        }
        return specification;
    }
}
