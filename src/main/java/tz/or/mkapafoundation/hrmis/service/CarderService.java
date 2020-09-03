package tz.or.mkapafoundation.hrmis.service;

import tz.or.mkapafoundation.hrmis.domain.Carder;
import tz.or.mkapafoundation.hrmis.repository.CarderRepository;
import tz.or.mkapafoundation.hrmis.service.dto.CarderDTO;
import tz.or.mkapafoundation.hrmis.service.mapper.CarderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Carder}.
 */
@Service
@Transactional
public class CarderService {

    private final Logger log = LoggerFactory.getLogger(CarderService.class);

    private final CarderRepository carderRepository;

    private final CarderMapper carderMapper;

    public CarderService(CarderRepository carderRepository, CarderMapper carderMapper) {
        this.carderRepository = carderRepository;
        this.carderMapper = carderMapper;
    }

    /**
     * Save a carder.
     *
     * @param carderDTO the entity to save.
     * @return the persisted entity.
     */
    public CarderDTO save(CarderDTO carderDTO) {
        log.debug("Request to save Carder : {}", carderDTO);
        Carder carder = carderMapper.toEntity(carderDTO);
        carder = carderRepository.save(carder);
        return carderMapper.toDto(carder);
    }

    /**
     * Get all the carders.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CarderDTO> findAll() {
        log.debug("Request to get all Carders");
        return carderRepository.findAll().stream()
            .map(carderMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one carder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CarderDTO> findOne(Long id) {
        log.debug("Request to get Carder : {}", id);
        return carderRepository.findById(id)
            .map(carderMapper::toDto);
    }

    /**
     * Delete the carder by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Carder : {}", id);
        carderRepository.deleteById(id);
    }
}
