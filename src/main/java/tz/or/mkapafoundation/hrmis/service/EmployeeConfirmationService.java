package tz.or.mkapafoundation.hrmis.service;

import tz.or.mkapafoundation.hrmis.domain.EmployeeConfirmation;
import tz.or.mkapafoundation.hrmis.repository.EmployeeConfirmationRepository;
import tz.or.mkapafoundation.hrmis.service.dto.EmployeeConfirmationDTO;
import tz.or.mkapafoundation.hrmis.service.mapper.EmployeeConfirmationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link EmployeeConfirmation}.
 */
@Service
@Transactional
public class EmployeeConfirmationService {

    private final Logger log = LoggerFactory.getLogger(EmployeeConfirmationService.class);

    private final EmployeeConfirmationRepository employeeConfirmationRepository;

    private final EmployeeConfirmationMapper employeeConfirmationMapper;

    public EmployeeConfirmationService(EmployeeConfirmationRepository employeeConfirmationRepository, EmployeeConfirmationMapper employeeConfirmationMapper) {
        this.employeeConfirmationRepository = employeeConfirmationRepository;
        this.employeeConfirmationMapper = employeeConfirmationMapper;
    }

    /**
     * Save a employeeConfirmation.
     *
     * @param employeeConfirmationDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeConfirmationDTO save(EmployeeConfirmationDTO employeeConfirmationDTO) {
        log.debug("Request to save EmployeeConfirmation : {}", employeeConfirmationDTO);
        EmployeeConfirmation employeeConfirmation = employeeConfirmationMapper.toEntity(employeeConfirmationDTO);
        employeeConfirmation = employeeConfirmationRepository.save(employeeConfirmation);
        return employeeConfirmationMapper.toDto(employeeConfirmation);
    }

    /**
     * Get all the employeeConfirmations.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeConfirmationDTO> findAll() {
        log.debug("Request to get all EmployeeConfirmations");
        return employeeConfirmationRepository.findAll().stream()
            .map(employeeConfirmationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one employeeConfirmation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeConfirmationDTO> findOne(Long id) {
        log.debug("Request to get EmployeeConfirmation : {}", id);
        return employeeConfirmationRepository.findById(id)
            .map(employeeConfirmationMapper::toDto);
    }

    /**
     * Delete the employeeConfirmation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeConfirmation : {}", id);
        employeeConfirmationRepository.deleteById(id);
    }
}
