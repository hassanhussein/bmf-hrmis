package tz.or.mkapafoundation.hrmis.web.rest;

import tz.or.mkapafoundation.hrmis.service.EmploymentCategoryService;
import tz.or.mkapafoundation.hrmis.web.rest.errors.BadRequestAlertException;
import tz.or.mkapafoundation.hrmis.service.dto.EmploymentCategoryDTO;

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
 * REST controller for managing {@link tz.or.mkapafoundation.hrmis.domain.EmploymentCategory}.
 */
@RestController
@RequestMapping("/api")
public class EmploymentCategoryResource {

    private final Logger log = LoggerFactory.getLogger(EmploymentCategoryResource.class);

    private static final String ENTITY_NAME = "employmentCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmploymentCategoryService employmentCategoryService;

    public EmploymentCategoryResource(EmploymentCategoryService employmentCategoryService) {
        this.employmentCategoryService = employmentCategoryService;
    }

    /**
     * {@code POST  /employment-categories} : Create a new employmentCategory.
     *
     * @param employmentCategoryDTO the employmentCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employmentCategoryDTO, or with status {@code 400 (Bad Request)} if the employmentCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employment-categories")
    public ResponseEntity<EmploymentCategoryDTO> createEmploymentCategory(@Valid @RequestBody EmploymentCategoryDTO employmentCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save EmploymentCategory : {}", employmentCategoryDTO);
        if (employmentCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new employmentCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmploymentCategoryDTO result = employmentCategoryService.save(employmentCategoryDTO);
        return ResponseEntity.created(new URI("/api/employment-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employment-categories} : Updates an existing employmentCategory.
     *
     * @param employmentCategoryDTO the employmentCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employmentCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the employmentCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employmentCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employment-categories")
    public ResponseEntity<EmploymentCategoryDTO> updateEmploymentCategory(@Valid @RequestBody EmploymentCategoryDTO employmentCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update EmploymentCategory : {}", employmentCategoryDTO);
        if (employmentCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmploymentCategoryDTO result = employmentCategoryService.save(employmentCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employmentCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employment-categories} : get all the employmentCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employmentCategories in body.
     */
    @GetMapping("/employment-categories")
    public List<EmploymentCategoryDTO> getAllEmploymentCategories() {
        log.debug("REST request to get all EmploymentCategories");
        return employmentCategoryService.findAll();
    }

    /**
     * {@code GET  /employment-categories/:id} : get the "id" employmentCategory.
     *
     * @param id the id of the employmentCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employmentCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employment-categories/{id}")
    public ResponseEntity<EmploymentCategoryDTO> getEmploymentCategory(@PathVariable Long id) {
        log.debug("REST request to get EmploymentCategory : {}", id);
        Optional<EmploymentCategoryDTO> employmentCategoryDTO = employmentCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employmentCategoryDTO);
    }

    /**
     * {@code DELETE  /employment-categories/:id} : delete the "id" employmentCategory.
     *
     * @param id the id of the employmentCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employment-categories/{id}")
    public ResponseEntity<Void> deleteEmploymentCategory(@PathVariable Long id) {
        log.debug("REST request to delete EmploymentCategory : {}", id);
        employmentCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
