package tz.or.mkapafoundation.hrmis.web.rest;

import tz.or.mkapafoundation.hrmis.service.FacilityService;
import tz.or.mkapafoundation.hrmis.web.rest.errors.BadRequestAlertException;
import tz.or.mkapafoundation.hrmis.service.dto.FacilityDTO;

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
 * REST controller for managing {@link tz.or.mkapafoundation.hrmis.domain.Facility}.
 */
@RestController
@RequestMapping("/api")
public class FacilityResource {

    private final Logger log = LoggerFactory.getLogger(FacilityResource.class);

    private static final String ENTITY_NAME = "facility";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FacilityService facilityService;

    public FacilityResource(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    /**
     * {@code POST  /facilities} : Create a new facility.
     *
     * @param facilityDTO the facilityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new facilityDTO, or with status {@code 400 (Bad Request)} if the facility has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/facilities")
    public ResponseEntity<FacilityDTO> createFacility(@Valid @RequestBody FacilityDTO facilityDTO) throws URISyntaxException {
        log.debug("REST request to save Facility : {}", facilityDTO);
        if (facilityDTO.getId() != null) {
            throw new BadRequestAlertException("A new facility cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FacilityDTO result = facilityService.save(facilityDTO);
        return ResponseEntity.created(new URI("/api/facilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /facilities} : Updates an existing facility.
     *
     * @param facilityDTO the facilityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facilityDTO,
     * or with status {@code 400 (Bad Request)} if the facilityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the facilityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/facilities")
    public ResponseEntity<FacilityDTO> updateFacility(@Valid @RequestBody FacilityDTO facilityDTO) throws URISyntaxException {
        log.debug("REST request to update Facility : {}", facilityDTO);
        if (facilityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FacilityDTO result = facilityService.save(facilityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, facilityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /facilities} : get all the facilities.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of facilities in body.
     */
    @GetMapping("/facilities")
    public ResponseEntity<List<FacilityDTO>> getAllFacilities(Pageable pageable) {
        log.debug("REST request to get a page of Facilities");
        Page<FacilityDTO> page = facilityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /facilities/:id} : get the "id" facility.
     *
     * @param id the id of the facilityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the facilityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/facilities/{id}")
    public ResponseEntity<FacilityDTO> getFacility(@PathVariable Long id) {
        log.debug("REST request to get Facility : {}", id);
        Optional<FacilityDTO> facilityDTO = facilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(facilityDTO);
    }

    /**
     * {@code DELETE  /facilities/:id} : delete the "id" facility.
     *
     * @param id the id of the facilityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/facilities/{id}")
    public ResponseEntity<Void> deleteFacility(@PathVariable Long id) {
        log.debug("REST request to delete Facility : {}", id);
        facilityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
