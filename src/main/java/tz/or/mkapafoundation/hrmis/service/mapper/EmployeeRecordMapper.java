package tz.or.mkapafoundation.hrmis.service.mapper;


import tz.or.mkapafoundation.hrmis.domain.*;
import tz.or.mkapafoundation.hrmis.service.dto.EmployeeRecordDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeeRecord} and its DTO {@link EmployeeRecordDTO}.
 */
@Mapper(componentModel = "spring", uses = {DepartmentMapper.class, EmploymentCategoryMapper.class, CarderMapper.class, FacilityMapper.class, ProjectMapper.class})
public interface EmployeeRecordMapper extends EntityMapper<EmployeeRecordDTO, EmployeeRecord> {

    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    @Mapping(source = "employeeType.id", target = "employeeTypeId")
    @Mapping(source = "employeeType.name", target = "employeeTypeName")
    @Mapping(source = "designation.id", target = "designationId")
    @Mapping(source = "designation.name", target = "designationName")
    @Mapping(source = "facility.id", target = "facilityId")
    @Mapping(source = "facility.name", target = "facilityName")
    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.name", target = "projectName")
    EmployeeRecordDTO toDto(EmployeeRecord employeeRecord);

    @Mapping(target = "attachments", ignore = true)
    @Mapping(target = "removeAttachments", ignore = true)
    @Mapping(source = "departmentId", target = "department")
    @Mapping(source = "employeeTypeId", target = "employeeType")
    @Mapping(source = "designationId", target = "designation")
    @Mapping(source = "facilityId", target = "facility")
    @Mapping(source = "projectId", target = "project")
    EmployeeRecord toEntity(EmployeeRecordDTO employeeRecordDTO);

    default EmployeeRecord fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmployeeRecord employeeRecord = new EmployeeRecord();
        employeeRecord.setId(id);
        return employeeRecord;
    }
}
