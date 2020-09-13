package tz.or.mkapafoundation.hrmis.web.rest;

import tz.or.mkapafoundation.hrmis.service.GeographicLevelService;
import tz.or.mkapafoundation.hrmis.web.rest.errors.BadRequestAlertException;
import tz.or.mkapafoundation.hrmis.service.dto.GeographicLevelDTO;
import tz.or.mkapafoundation.hrmis.service.dto.GeographicLevelCriteria;
import tz.or.mkapafoundation.hrmis.service.GeographicLevelQueryService;

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
 * REST controller for managing {@link tz.or.mkapafoundation.hrmis.domain.GeographicLevel}.
 */
@RestController
@RequestMapping("/api")
public class GeographicLevelResource {

    private final Logger log = LoggerFactory.getLogger(GeographicLevelResource.class);

    private static final String ENTITY_NAME = "geographicLevel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GeographicLevelService geographicLevelService;

    private final GeographicLevelQueryService geographicLevelQueryService;

    public GeographicLevelResource(GeographicLevelService geographicLevelService, GeographicLevelQueryService geographicLevelQueryService) {
        this.geographicLevelService = geographicLevelService;
        this.geographicLevelQueryService = geographicLevelQueryService;
    }

    /**
     * {@code POST  /geographic-levels} : Create a new geographicLevel.
     *
     * @param geographicLevelDTO the geographicLevelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new geographicLevelDTO, or with status {@code 400 (Bad Request)} if the geographicLevel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/geographic-levels")
    public ResponseEntity<GeographicLevelDTO> createGeographicLevel(@Valid @RequestBody GeographicLevelDTO geographicLevelDTO) throws URISyntaxException {
        log.debug("REST request to save GeographicLevel : {}", geographicLevelDTO);
        if (geographicLevelDTO.getId() != null) {
            throw new BadRequestAlertException("A new geographicLevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GeographicLevelDTO result = geographicLevelService.save(geographicLevelDTO);
        return ResponseEntity.created(new URI("/api/geographic-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /geographic-levels} : Updates an existing geographicLevel.
     *
     * @param geographicLevelDTO the geographicLevelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated geographicLevelDTO,
     * or with status {@code 400 (Bad Request)} if the geographicLevelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the geographicLevelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/geographic-levels")
    public ResponseEntity<GeographicLevelDTO> updateGeographicLevel(@Valid @RequestBody GeographicLevelDTO geographicLevelDTO) throws URISyntaxException {
        log.debug("REST request to update GeographicLevel : {}", geographicLevelDTO);
        if (geographicLevelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GeographicLevelDTO result = geographicLevelService.save(geographicLevelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, geographicLevelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /geographic-levels} : get all the geographicLevels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of geographicLevels in body.
     */
    @GetMapping("/geographic-levels")
    public ResponseEntity<List<GeographicLevelDTO>> getAllGeographicLevels(GeographicLevelCriteria criteria) {
        log.debug("REST request to get GeographicLevels by criteria: {}", criteria);
        List<GeographicLevelDTO> entityList = geographicLevelQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /geographic-levels/count} : count all the geographicLevels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/geographic-levels/count")
    public ResponseEntity<Long> countGeographicLevels(GeographicLevelCriteria criteria) {
        log.debug("REST request to count GeographicLevels by criteria: {}", criteria);
        return ResponseEntity.ok().body(geographicLevelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /geographic-levels/:id} : get the "id" geographicLevel.
     *
     * @param id the id of the geographicLevelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the geographicLevelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/geographic-levels/{id}")
    public ResponseEntity<GeographicLevelDTO> getGeographicLevel(@PathVariable Long id) {
        log.debug("REST request to get GeographicLevel : {}", id);
        Optional<GeographicLevelDTO> geographicLevelDTO = geographicLevelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(geographicLevelDTO);
    }

    /**
     * {@code DELETE  /geographic-levels/:id} : delete the "id" geographicLevel.
     *
     * @param id the id of the geographicLevelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/geographic-levels/{id}")
    public ResponseEntity<Void> deleteGeographicLevel(@PathVariable Long id) {
        log.debug("REST request to delete GeographicLevel : {}", id);
        geographicLevelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
