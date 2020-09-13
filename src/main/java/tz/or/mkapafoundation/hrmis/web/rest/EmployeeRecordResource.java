package tz.or.mkapafoundation.hrmis.web.rest;

import tz.or.mkapafoundation.hrmis.service.EmployeeRecordService;
import tz.or.mkapafoundation.hrmis.web.rest.errors.BadRequestAlertException;
import tz.or.mkapafoundation.hrmis.service.dto.EmployeeRecordDTO;
import tz.or.mkapafoundation.hrmis.service.dto.EmployeeRecordCriteria;
import tz.or.mkapafoundation.hrmis.service.EmployeeRecordQueryService;

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
 * REST controller for managing {@link tz.or.mkapafoundation.hrmis.domain.EmployeeRecord}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeRecordResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeRecordResource.class);

    private static final String ENTITY_NAME = "employeeRecord";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeRecordService employeeRecordService;

    private final EmployeeRecordQueryService employeeRecordQueryService;

    public EmployeeRecordResource(EmployeeRecordService employeeRecordService, EmployeeRecordQueryService employeeRecordQueryService) {
        this.employeeRecordService = employeeRecordService;
        this.employeeRecordQueryService = employeeRecordQueryService;
    }

    /**
     * {@code POST  /employee-records} : Create a new employeeRecord.
     *
     * @param employeeRecordDTO the employeeRecordDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeRecordDTO, or with status {@code 400 (Bad Request)} if the employeeRecord has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-records")
    public ResponseEntity<EmployeeRecordDTO> createEmployeeRecord(@Valid @RequestBody EmployeeRecordDTO employeeRecordDTO) throws URISyntaxException {
        log.debug("REST request to save EmployeeRecord : {}", employeeRecordDTO);
        if (employeeRecordDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeeRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeRecordDTO result = employeeRecordService.save(employeeRecordDTO);
        return ResponseEntity.created(new URI("/api/employee-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-records} : Updates an existing employeeRecord.
     *
     * @param employeeRecordDTO the employeeRecordDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeRecordDTO,
     * or with status {@code 400 (Bad Request)} if the employeeRecordDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeRecordDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-records")
    public ResponseEntity<EmployeeRecordDTO> updateEmployeeRecord(@Valid @RequestBody EmployeeRecordDTO employeeRecordDTO) throws URISyntaxException {
        log.debug("REST request to update EmployeeRecord : {}", employeeRecordDTO);
        if (employeeRecordDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeRecordDTO result = employeeRecordService.save(employeeRecordDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeRecordDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-records} : get all the employeeRecords.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeRecords in body.
     */
    @GetMapping("/employee-records")
    public ResponseEntity<List<EmployeeRecordDTO>> getAllEmployeeRecords(EmployeeRecordCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EmployeeRecords by criteria: {}", criteria);
        Page<EmployeeRecordDTO> page = employeeRecordQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-records/count} : count all the employeeRecords.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-records/count")
    public ResponseEntity<Long> countEmployeeRecords(EmployeeRecordCriteria criteria) {
        log.debug("REST request to count EmployeeRecords by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeRecordQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-records/:id} : get the "id" employeeRecord.
     *
     * @param id the id of the employeeRecordDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeRecordDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-records/{id}")
    public ResponseEntity<EmployeeRecordDTO> getEmployeeRecord(@PathVariable Long id) {
        log.debug("REST request to get EmployeeRecord : {}", id);
        Optional<EmployeeRecordDTO> employeeRecordDTO = employeeRecordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeRecordDTO);
    }

    /**
     * {@code DELETE  /employee-records/:id} : delete the "id" employeeRecord.
     *
     * @param id the id of the employeeRecordDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-records/{id}")
    public ResponseEntity<Void> deleteEmployeeRecord(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeRecord : {}", id);
        employeeRecordService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
