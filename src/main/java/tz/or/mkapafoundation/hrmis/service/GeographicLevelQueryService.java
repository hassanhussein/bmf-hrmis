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

import tz.or.mkapafoundation.hrmis.domain.GeographicLevel;
import tz.or.mkapafoundation.hrmis.domain.*; // for static metamodels
import tz.or.mkapafoundation.hrmis.repository.GeographicLevelRepository;
import tz.or.mkapafoundation.hrmis.service.dto.GeographicLevelCriteria;
import tz.or.mkapafoundation.hrmis.service.dto.GeographicLevelDTO;
import tz.or.mkapafoundation.hrmis.service.mapper.GeographicLevelMapper;

/**
 * Service for executing complex queries for {@link GeographicLevel} entities in the database.
 * The main input is a {@link GeographicLevelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GeographicLevelDTO} or a {@link Page} of {@link GeographicLevelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GeographicLevelQueryService extends QueryService<GeographicLevel> {

    private final Logger log = LoggerFactory.getLogger(GeographicLevelQueryService.class);

    private final GeographicLevelRepository geographicLevelRepository;

    private final GeographicLevelMapper geographicLevelMapper;

    public GeographicLevelQueryService(GeographicLevelRepository geographicLevelRepository, GeographicLevelMapper geographicLevelMapper) {
        this.geographicLevelRepository = geographicLevelRepository;
        this.geographicLevelMapper = geographicLevelMapper;
    }

    /**
     * Return a {@link List} of {@link GeographicLevelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GeographicLevelDTO> findByCriteria(GeographicLevelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<GeographicLevel> specification = createSpecification(criteria);
        return geographicLevelMapper.toDto(geographicLevelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link GeographicLevelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GeographicLevelDTO> findByCriteria(GeographicLevelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<GeographicLevel> specification = createSpecification(criteria);
        return geographicLevelRepository.findAll(specification, page)
            .map(geographicLevelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GeographicLevelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<GeographicLevel> specification = createSpecification(criteria);
        return geographicLevelRepository.count(specification);
    }

    /**
     * Function to convert {@link GeographicLevelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<GeographicLevel> createSpecification(GeographicLevelCriteria criteria) {
        Specification<GeographicLevel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), GeographicLevel_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), GeographicLevel_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), GeographicLevel_.name));
            }
            if (criteria.getLevelNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLevelNumber(), GeographicLevel_.levelNumber));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), GeographicLevel_.active));
            }
        }
        return specification;
    }
}
