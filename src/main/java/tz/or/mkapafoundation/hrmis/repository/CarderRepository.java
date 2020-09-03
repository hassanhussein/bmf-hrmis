package tz.or.mkapafoundation.hrmis.repository;

import tz.or.mkapafoundation.hrmis.domain.Carder;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Carder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarderRepository extends JpaRepository<Carder, Long> {
}
