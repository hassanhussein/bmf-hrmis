package tz.or.mkapafoundation.hrmis.web.rest;

import tz.or.mkapafoundation.hrmis.service.FacilityTypeService;
import tz.or.mkapafoundation.hrmis.web.rest.errors.BadRequestAlertException;
import tz.or.mkapafoundation.hrmis.service.dto.FacilityTypeDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link tz.or.mkapafoundation.hrmis.domain.FacilityType}.
 */
@RestController
@RequestMapping("/api")
public class FacilityTypeResource {

    private final Logger log = LoggerFactory.getLogger(FacilityTypeResource.class);

    private static final String ENTITY_NAME = "facilityType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FacilityTypeService facilityTypeService;

    public FacilityTypeResource(FacilityTypeService facilityTypeService) {
        this.facilityTypeService = facilityTypeService;
    }

    /**
     * {@code POST  /facility-types} : Create a new facilityType.
     *
     * @param facilityTypeDTO the facilityTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new facilityTypeDTO, or with status {@code 400 (Bad Request)} if the facilityType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/facility-types")
    public ResponseEntity<FacilityTypeDTO> createFacilityType(@Valid @RequestBody FacilityTypeDTO facilityTypeDTO) throws URISyntaxException {
        log.debug("REST request to save FacilityType : {}", facilityTypeDTO);
        if (facilityTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new facilityType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FacilityTypeDTO result = facilityTypeService.save(facilityTypeDTO);
        return ResponseEntity.created(new URI("/api/facility-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /facility-types} : Updates an existing facilityType.
     *
     * @param facilityTypeDTO the facilityTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facilityTypeDTO,
     * or with status {@code 400 (Bad Request)} if the facilityTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the facilityTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/facility-types")
    public ResponseEntity<FacilityTypeDTO> updateFacilityType(@Valid @RequestBody FacilityTypeDTO facilityTypeDTO) throws URISyntaxException {
        log.debug("REST request to update FacilityType : {}", facilityTypeDTO);
        if (facilityTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FacilityTypeDTO result = facilityTypeService.save(facilityTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, facilityTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /facility-types} : get all the facilityTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of facilityTypes in body.
     */
    @GetMapping("/facility-types")
    public List<FacilityTypeDTO> getAllFacilityTypes() {
        log.debug("REST request to get all FacilityTypes");
        return facilityTypeService.findAll();
    }

    /**
     * {@code GET  /facility-types/:id} : get the "id" facilityType.
     *
     * @param id the id of the facilityTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the facilityTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/facility-types/{id}")
    public ResponseEntity<FacilityTypeDTO> getFacilityType(@PathVariable Long id) {
        log.debug("REST request to get FacilityType : {}", id);
        Optional<FacilityTypeDTO> facilityTypeDTO = facilityTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(facilityTypeDTO);
    }

    /**
     * {@code DELETE  /facility-types/:id} : delete the "id" facilityType.
     *
     * @param id the id of the facilityTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/facility-types/{id}")
    public ResponseEntity<Void> deleteFacilityType(@PathVariable Long id) {
        log.debug("REST request to delete FacilityType : {}", id);
        facilityTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
