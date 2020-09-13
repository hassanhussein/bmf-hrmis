package tz.or.mkapafoundation.hrmis.repository;

import tz.or.mkapafoundation.hrmis.domain.GeographicLevel;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GeographicLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeographicLevelRepository extends JpaRepository<GeographicLevel, Long>, JpaSpecificationExecutor<GeographicLevel> {
}
