package tz.or.mkapafoundation.hrmis.repository;

import tz.or.mkapafoundation.hrmis.domain.EmployeeConfirmation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmployeeConfirmation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeConfirmationRepository extends JpaRepository<EmployeeConfirmation, Long> {
}
