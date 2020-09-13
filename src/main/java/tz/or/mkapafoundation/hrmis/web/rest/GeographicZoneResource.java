package tz.or.mkapafoundation.hrmis.web.rest;

import tz.or.mkapafoundation.hrmis.service.GeographicZoneService;
import tz.or.mkapafoundation.hrmis.web.rest.errors.BadRequestAlertException;
import tz.or.mkapafoundation.hrmis.service.dto.GeographicZoneDTO;

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
 * REST controller for managing {@link tz.or.mkapafoundation.hrmis.domain.GeographicZone}.
 */
@RestController
@RequestMapping("/api")
public class GeographicZoneResource {

    private final Logger log = LoggerFactory.getLogger(GeographicZoneResource.class);

    private static final String ENTITY_NAME = "geographicZone";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GeographicZoneService geographicZoneService;

    public GeographicZoneResource(GeographicZoneService geographicZoneService) {
        this.geographicZoneService = geographicZoneService;
    }

    /**
     * {@code POST  /geographic-zones} : Create a new geographicZone.
     *
     * @param geographicZoneDTO the geographicZoneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new geographicZoneDTO, or with status {@code 400 (Bad Request)} if the geographicZone has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/geographic-zones")
    public ResponseEntity<GeographicZoneDTO> createGeographicZone(@Valid @RequestBody GeographicZoneDTO geographicZoneDTO) throws URISyntaxException {
        log.debug("REST request to save GeographicZone : {}", geographicZoneDTO);
        if (geographicZoneDTO.getId() != null) {
            throw new BadRequestAlertException("A new geographicZone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GeographicZoneDTO result = geographicZoneService.save(geographicZoneDTO);
        return ResponseEntity.created(new URI("/api/geographic-zones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /geographic-zones} : Updates an existing geographicZone.
     *
     * @param geographicZoneDTO the geographicZoneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated geographicZoneDTO,
     * or with status {@code 400 (Bad Request)} if the geographicZoneDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the geographicZoneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/geographic-zones")
    public ResponseEntity<GeographicZoneDTO> updateGeographicZone(@Valid @RequestBody GeographicZoneDTO geographicZoneDTO) throws URISyntaxException {
        log.debug("REST request to update GeographicZone : {}", geographicZoneDTO);
        if (geographicZoneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GeographicZoneDTO result = geographicZoneService.save(geographicZoneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, geographicZoneDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /geographic-zones} : get all the geographicZones.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of geographicZones in body.
     */
    @GetMapping("/geographic-zones")
    public ResponseEntity<List<GeographicZoneDTO>> getAllGeographicZones(Pageable pageable) {
        log.debug("REST request to get a page of GeographicZones");
        Page<GeographicZoneDTO> page = geographicZoneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /geographic-zones/:id} : get the "id" geographicZone.
     *
     * @param id the id of the geographicZoneDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the geographicZoneDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/geographic-zones/{id}")
    public ResponseEntity<GeographicZoneDTO> getGeographicZone(@PathVariable Long id) {
        log.debug("REST request to get GeographicZone : {}", id);
        Optional<GeographicZoneDTO> geographicZoneDTO = geographicZoneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(geographicZoneDTO);
    }

    /**
     * {@code DELETE  /geographic-zones/:id} : delete the "id" geographicZone.
     *
     * @param id the id of the geographicZoneDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/geographic-zones/{id}")
    public ResponseEntity<Void> deleteGeographicZone(@PathVariable Long id) {
        log.debug("REST request to delete GeographicZone : {}", id);
        geographicZoneService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
