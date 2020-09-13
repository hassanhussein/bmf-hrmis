package tz.or.mkapafoundation.hrmis.repository;

import tz.or.mkapafoundation.hrmis.domain.EmployeeRecord;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmployeeRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeRecordRepository extends JpaRepository<EmployeeRecord, Long>, JpaSpecificationExecutor<EmployeeRecord> {
}
