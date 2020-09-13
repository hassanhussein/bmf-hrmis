package tz.or.mkapafoundation.hrmis.service;

import tz.or.mkapafoundation.hrmis.domain.GeographicZone;
import tz.or.mkapafoundation.hrmis.repository.GeographicZoneRepository;
import tz.or.mkapafoundation.hrmis.service.dto.GeographicZoneDTO;
import tz.or.mkapafoundation.hrmis.service.mapper.GeographicZoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GeographicZone}.
 */
@Service
@Transactional
public class GeographicZoneService {

    private final Logger log = LoggerFactory.getLogger(GeographicZoneService.class);

    private final GeographicZoneRepository geographicZoneRepository;

    private final GeographicZoneMapper geographicZoneMapper;

    public GeographicZoneService(GeographicZoneRepository geographicZoneRepository, GeographicZoneMapper geographicZoneMapper) {
        this.geographicZoneRepository = geographicZoneRepository;
        this.geographicZoneMapper = geographicZoneMapper;
    }

    /**
     * Save a geographicZone.
     *
     * @param geographicZoneDTO the entity to save.
     * @return the persisted entity.
     */
    public GeographicZoneDTO save(GeographicZoneDTO geographicZoneDTO) {
        log.debug("Request to save GeographicZone : {}", geographicZoneDTO);
        GeographicZone geographicZone = geographicZoneMapper.toEntity(geographicZoneDTO);
        geographicZone = geographicZoneRepository.save(geographicZone);
        return geographicZoneMapper.toDto(geographicZone);
    }

    /**
     * Get all the geographicZones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GeographicZoneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GeographicZones");
        return geographicZoneRepository.findAll(pageable)
            .map(geographicZoneMapper::toDto);
    }


    /**
     * Get one geographicZone by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GeographicZoneDTO> findOne(Long id) {
        log.debug("Request to get GeographicZone : {}", id);
        return geographicZoneRepository.findById(id)
            .map(geographicZoneMapper::toDto);
    }

    /**
     * Delete the geographicZone by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GeographicZone : {}", id);
        geographicZoneRepository.deleteById(id);
    }
}
