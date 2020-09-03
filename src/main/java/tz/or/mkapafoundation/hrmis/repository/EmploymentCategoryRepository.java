package tz.or.mkapafoundation.hrmis.repository;

import tz.or.mkapafoundation.hrmis.domain.EmploymentCategory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmploymentCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmploymentCategoryRepository extends JpaRepository<EmploymentCategory, Long> {
}
