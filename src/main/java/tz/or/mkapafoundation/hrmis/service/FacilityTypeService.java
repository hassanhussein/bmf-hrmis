package tz.or.mkapafoundation.hrmis.service;

import tz.or.mkapafoundation.hrmis.domain.FacilityType;
import tz.or.mkapafoundation.hrmis.repository.FacilityTypeRepository;
import tz.or.mkapafoundation.hrmis.service.dto.FacilityTypeDTO;
import tz.or.mkapafoundation.hrmis.service.mapper.FacilityTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link FacilityType}.
 */
@Service
@Transactional
public class FacilityTypeService {

    private final Logger log = LoggerFactory.getLogger(FacilityTypeService.class);

    private final FacilityTypeRepository facilityTypeRepository;

    private final FacilityTypeMapper facilityTypeMapper;

    public FacilityTypeService(FacilityTypeRepository facilityTypeRepository, FacilityTypeMapper facilityTypeMapper) {
        this.facilityTypeRepository = facilityTypeRepository;
        this.facilityTypeMapper = facilityTypeMapper;
    }

    /**
     * Save a facilityType.
     *
     * @param facilityTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public FacilityTypeDTO save(FacilityTypeDTO facilityTypeDTO) {
        log.debug("Request to save FacilityType : {}", facilityTypeDTO);
        FacilityType facilityType = facilityTypeMapper.toEntity(facilityTypeDTO);
        facilityType = facilityTypeRepository.save(facilityType);
        return facilityTypeMapper.toDto(facilityType);
    }

    /**
     * Get all the facilityTypes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FacilityTypeDTO> findAll() {
        log.debug("Request to get all FacilityTypes");
        return facilityTypeRepository.findAll().stream()
            .map(facilityTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one facilityType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FacilityTypeDTO> findOne(Long id) {
        log.debug("Request to get FacilityType : {}", id);
        return facilityTypeRepository.findById(id)
            .map(facilityTypeMapper::toDto);
    }

    /**
     * Delete the facilityType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FacilityType : {}", id);
        facilityTypeRepository.deleteById(id);
    }
}
