package tz.or.mkapafoundation.hrmis.service;

import tz.or.mkapafoundation.hrmis.domain.EmployeeRecord;
import tz.or.mkapafoundation.hrmis.repository.EmployeeRecordRepository;
import tz.or.mkapafoundation.hrmis.service.dto.EmployeeRecordDTO;
import tz.or.mkapafoundation.hrmis.service.mapper.EmployeeRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmployeeRecord}.
 */
@Service
@Transactional
public class EmployeeRecordService {

    private final Logger log = LoggerFactory.getLogger(EmployeeRecordService.class);

    private final EmployeeRecordRepository employeeRecordRepository;

    private final EmployeeRecordMapper employeeRecordMapper;

    public EmployeeRecordService(EmployeeRecordRepository employeeRecordRepository, EmployeeRecordMapper employeeRecordMapper) {
        this.employeeRecordRepository = employeeRecordRepository;
        this.employeeRecordMapper = employeeRecordMapper;
    }

    /**
     * Save a employeeRecord.
     *
     * @param employeeRecordDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeRecordDTO save(EmployeeRecordDTO employeeRecordDTO) {
        log.debug("Request to save EmployeeRecord : {}", employeeRecordDTO);
        EmployeeRecord employeeRecord = employeeRecordMapper.toEntity(employeeRecordDTO);
        employeeRecord = employeeRecordRepository.save(employeeRecord);
        return employeeRecordMapper.toDto(employeeRecord);
    }

    /**
     * Get all the employeeRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeRecordDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeRecords");
        return employeeRecordRepository.findAll(pageable)
            .map(employeeRecordMapper::toDto);
    }


    /**
     * Get one employeeRecord by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeRecordDTO> findOne(Long id) {
        log.debug("Request to get EmployeeRecord : {}", id);
        return employeeRecordRepository.findById(id)
            .map(employeeRecordMapper::toDto);
    }

    /**
     * Delete the employeeRecord by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeRecord : {}", id);
        employeeRecordRepository.deleteById(id);
    }
}
