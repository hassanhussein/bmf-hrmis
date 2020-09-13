package tz.or.mkapafoundation.hrmis.web.rest;

import tz.or.mkapafoundation.hrmis.service.CarderService;
import tz.or.mkapafoundation.hrmis.web.rest.errors.BadRequestAlertException;
import tz.or.mkapafoundation.hrmis.service.dto.CarderDTO;
import tz.or.mkapafoundation.hrmis.service.dto.CarderCriteria;
import tz.or.mkapafoundation.hrmis.service.CarderQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link tz.or.mkapafoundation.hrmis.domain.Carder}.
 */
@RestController
@RequestMapping("/api")
public class CarderResource {

    private final Logger log = LoggerFactory.getLogger(CarderResource.class);

    private static final String ENTITY_NAME = "carder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarderService carderService;

    private final CarderQueryService carderQueryService;

    public CarderResource(CarderService carderService, CarderQueryService carderQueryService) {
        this.carderService = carderService;
        this.carderQueryService = carderQueryService;
    }

    /**
     * {@code POST  /carders} : Create a new carder.
     *
     * @param carderDTO the carderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carderDTO, or with status {@code 400 (Bad Request)} if the carder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/carders")
    public ResponseEntity<CarderDTO> createCarder(@Valid @RequestBody CarderDTO carderDTO) throws URISyntaxException {
        log.debug("REST request to save Carder : {}", carderDTO);
        if (carderDTO.getId() != null) {
            throw new BadRequestAlertException("A new carder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CarderDTO result = carderService.save(carderDTO);
        return ResponseEntity.created(new URI("/api/carders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /carders} : Updates an existing carder.
     *
     * @param carderDTO the carderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carderDTO,
     * or with status {@code 400 (Bad Request)} if the carderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/carders")
    public ResponseEntity<CarderDTO> updateCarder(@Valid @RequestBody CarderDTO carderDTO) throws URISyntaxException {
        log.debug("REST request to update Carder : {}", carderDTO);
        if (carderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CarderDTO result = carderService.save(carderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /carders} : get all the carders.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carders in body.
     */
    @GetMapping("/carders")
    public ResponseEntity<List<CarderDTO>> getAllCarders(CarderCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Carders by criteria: {}", criteria);
        Page<CarderDTO> page = carderQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /carders/count} : count all the carders.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/carders/count")
    public ResponseEntity<Long> countCarders(CarderCriteria criteria) {
        log.debug("REST request to count Carders by criteria: {}", criteria);
        return ResponseEntity.ok().body(carderQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /carders/:id} : get the "id" carder.
     *
     * @param id the id of the carderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/carders/{id}")
    public ResponseEntity<CarderDTO> getCarder(@PathVariable Long id) {
        log.debug("REST request to get Carder : {}", id);
        Optional<CarderDTO> carderDTO = carderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carderDTO);
    }

    /**
     * {@code DELETE  /carders/:id} : delete the "id" carder.
     *
     * @param id the id of the carderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/carders/{id}")
    public ResponseEntity<Void> deleteCarder(@PathVariable Long id) {
        log.debug("REST request to delete Carder : {}", id);
        carderService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
