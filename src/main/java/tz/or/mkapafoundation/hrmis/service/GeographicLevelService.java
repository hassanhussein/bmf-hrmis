package tz.or.mkapafoundation.hrmis.service;

import tz.or.mkapafoundation.hrmis.domain.GeographicLevel;
import tz.or.mkapafoundation.hrmis.repository.GeographicLevelRepository;
import tz.or.mkapafoundation.hrmis.service.dto.GeographicLevelDTO;
import tz.or.mkapafoundation.hrmis.service.mapper.GeographicLevelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link GeographicLevel}.
 */
@Service
@Transactional
public class GeographicLevelService {

    private final Logger log = LoggerFactory.getLogger(GeographicLevelService.class);

    private final GeographicLevelRepository geographicLevelRepository;

    private final GeographicLevelMapper geographicLevelMapper;

    public GeographicLevelService(GeographicLevelRepository geographicLevelRepository, GeographicLevelMapper geographicLevelMapper) {
        this.geographicLevelRepository = geographicLevelRepository;
        this.geographicLevelMapper = geographicLevelMapper;
    }

    /**
     * Save a geographicLevel.
     *
     * @param geographicLevelDTO the entity to save.
     * @return the persisted entity.
     */
    public GeographicLevelDTO save(GeographicLevelDTO geographicLevelDTO) {
        log.debug("Request to save GeographicLevel : {}", geographicLevelDTO);
        GeographicLevel geographicLevel = geographicLevelMapper.toEntity(geographicLevelDTO);
        geographicLevel = geographicLevelRepository.save(geographicLevel);
        return geographicLevelMapper.toDto(geographicLevel);
    }

    /**
     * Get all the geographicLevels.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GeographicLevelDTO> findAll() {
        log.debug("Request to get all GeographicLevels");
        return geographicLevelRepository.findAll().stream()
            .map(geographicLevelMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one geographicLevel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GeographicLevelDTO> findOne(Long id) {
        log.debug("Request to get GeographicLevel : {}", id);
        return geographicLevelRepository.findById(id)
            .map(geographicLevelMapper::toDto);
    }

    /**
     * Delete the geographicLevel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GeographicLevel : {}", id);
        geographicLevelRepository.deleteById(id);
    }
}
