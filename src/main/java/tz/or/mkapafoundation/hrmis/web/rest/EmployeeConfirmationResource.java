package tz.or.mkapafoundation.hrmis.web.rest;

import tz.or.mkapafoundation.hrmis.service.EmployeeConfirmationService;
import tz.or.mkapafoundation.hrmis.web.rest.errors.BadRequestAlertException;
import tz.or.mkapafoundation.hrmis.service.dto.EmployeeConfirmationDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link tz.or.mkapafoundation.hrmis.domain.EmployeeConfirmation}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeConfirmationResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeConfirmationResource.class);

    private static final String ENTITY_NAME = "employeeConfirmation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeConfirmationService employeeConfirmationService;

    public EmployeeConfirmationResource(EmployeeConfirmationService employeeConfirmationService) {
        this.employeeConfirmationService = employeeConfirmationService;
    }

    /**
     * {@code POST  /employee-confirmations} : Create a new employeeConfirmation.
     *
     * @param employeeConfirmationDTO the employeeConfirmationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeConfirmationDTO, or with status {@code 400 (Bad Request)} if the employeeConfirmation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-confirmations")
    public ResponseEntity<EmployeeConfirmationDTO> createEmployeeConfirmation(@RequestBody EmployeeConfirmationDTO employeeConfirmationDTO) throws URISyntaxException {
        log.debug("REST request to save EmployeeConfirmation : {}", employeeConfirmationDTO);
        if (employeeConfirmationDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeeConfirmation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeConfirmationDTO result = employeeConfirmationService.save(employeeConfirmationDTO);
        return ResponseEntity.created(new URI("/api/employee-confirmations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-confirmations} : Updates an existing employeeConfirmation.
     *
     * @param employeeConfirmationDTO the employeeConfirmationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeConfirmationDTO,
     * or with status {@code 400 (Bad Request)} if the employeeConfirmationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeConfirmationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-confirmations")
    public ResponseEntity<EmployeeConfirmationDTO> updateEmployeeConfirmation(@RequestBody EmployeeConfirmationDTO employeeConfirmationDTO) throws URISyntaxException {
        log.debug("REST request to update EmployeeConfirmation : {}", employeeConfirmationDTO);
        if (employeeConfirmationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeConfirmationDTO result = employeeConfirmationService.save(employeeConfirmationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeConfirmationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-confirmations} : get all the employeeConfirmations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeConfirmations in body.
     */
    @GetMapping("/employee-confirmations")
    public List<EmployeeConfirmationDTO> getAllEmployeeConfirmations() {
        log.debug("REST request to get all EmployeeConfirmations");
        return employeeConfirmationService.findAll();
    }

    /**
     * {@code GET  /employee-confirmations/:id} : get the "id" employeeConfirmation.
     *
     * @param id the id of the employeeConfirmationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeConfirmationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-confirmations/{id}")
    public ResponseEntity<EmployeeConfirmationDTO> getEmployeeConfirmation(@PathVariable Long id) {
        log.debug("REST request to get EmployeeConfirmation : {}", id);
        Optional<EmployeeConfirmationDTO> employeeConfirmationDTO = employeeConfirmationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeConfirmationDTO);
    }

    /**
     * {@code DELETE  /employee-confirmations/:id} : delete the "id" employeeConfirmation.
     *
     * @param id the id of the employeeConfirmationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-confirmations/{id}")
    public ResponseEntity<Void> deleteEmployeeConfirmation(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeConfirmation : {}", id);
        employeeConfirmationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
