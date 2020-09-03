package tz.or.mkapafoundation.hrmis.service;

import tz.or.mkapafoundation.hrmis.domain.EmploymentCategory;
import tz.or.mkapafoundation.hrmis.repository.EmploymentCategoryRepository;
import tz.or.mkapafoundation.hrmis.service.dto.EmploymentCategoryDTO;
import tz.or.mkapafoundation.hrmis.service.mapper.EmploymentCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link EmploymentCategory}.
 */
@Service
@Transactional
public class EmploymentCategoryService {

    private final Logger log = LoggerFactory.getLogger(EmploymentCategoryService.class);

    private final EmploymentCategoryRepository employmentCategoryRepository;

    private final EmploymentCategoryMapper employmentCategoryMapper;

    public EmploymentCategoryService(EmploymentCategoryRepository employmentCategoryRepository, EmploymentCategoryMapper employmentCategoryMapper) {
        this.employmentCategoryRepository = employmentCategoryRepository;
        this.employmentCategoryMapper = employmentCategoryMapper;
    }

    /**
     * Save a employmentCategory.
     *
     * @param employmentCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    public EmploymentCategoryDTO save(EmploymentCategoryDTO employmentCategoryDTO) {
        log.debug("Request to save EmploymentCategory : {}", employmentCategoryDTO);
        EmploymentCategory employmentCategory = employmentCategoryMapper.toEntity(employmentCategoryDTO);
        employmentCategory = employmentCategoryRepository.save(employmentCategory);
        return employmentCategoryMapper.toDto(employmentCategory);
    }

    /**
     * Get all the employmentCategories.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EmploymentCategoryDTO> findAll() {
        log.debug("Request to get all EmploymentCategories");
        return employmentCategoryRepository.findAll().stream()
            .map(employmentCategoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one employmentCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmploymentCategoryDTO> findOne(Long id) {
        log.debug("Request to get EmploymentCategory : {}", id);
        return employmentCategoryRepository.findById(id)
            .map(employmentCategoryMapper::toDto);
    }

    /**
     * Delete the employmentCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmploymentCategory : {}", id);
        employmentCategoryRepository.deleteById(id);
    }
}
