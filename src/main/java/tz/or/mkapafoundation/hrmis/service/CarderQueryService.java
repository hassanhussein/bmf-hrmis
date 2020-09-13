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

import tz.or.mkapafoundation.hrmis.domain.Carder;
import tz.or.mkapafoundation.hrmis.domain.*; // for static metamodels
import tz.or.mkapafoundation.hrmis.repository.CarderRepository;
import tz.or.mkapafoundation.hrmis.service.dto.CarderCriteria;
import tz.or.mkapafoundation.hrmis.service.dto.CarderDTO;
import tz.or.mkapafoundation.hrmis.service.mapper.CarderMapper;

/**
 * Service for executing complex queries for {@link Carder} entities in the database.
 * The main input is a {@link CarderCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CarderDTO} or a {@link Page} of {@link CarderDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CarderQueryService extends QueryService<Carder> {

    private final Logger log = LoggerFactory.getLogger(CarderQueryService.class);

    private final CarderRepository carderRepository;

    private final CarderMapper carderMapper;

    public CarderQueryService(CarderRepository carderRepository, CarderMapper carderMapper) {
        this.carderRepository = carderRepository;
        this.carderMapper = carderMapper;
    }

    /**
     * Return a {@link List} of {@link CarderDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CarderDTO> findByCriteria(CarderCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Carder> specification = createSpecification(criteria);
        return carderMapper.toDto(carderRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CarderDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CarderDTO> findByCriteria(CarderCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Carder> specification = createSpecification(criteria);
        return carderRepository.findAll(specification, page)
            .map(carderMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CarderCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Carder> specification = createSpecification(criteria);
        return carderRepository.count(specification);
    }

    /**
     * Function to convert {@link CarderCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Carder> createSpecification(CarderCriteria criteria) {
        Specification<Carder> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Carder_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Carder_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Carder_.name));
            }
        }
        return specification;
    }
}
