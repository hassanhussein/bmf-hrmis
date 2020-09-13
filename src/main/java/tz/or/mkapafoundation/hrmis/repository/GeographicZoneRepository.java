package tz.or.mkapafoundation.hrmis.repository;

import tz.or.mkapafoundation.hrmis.domain.GeographicZone;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GeographicZone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeographicZoneRepository extends JpaRepository<GeographicZone, Long> {
}
