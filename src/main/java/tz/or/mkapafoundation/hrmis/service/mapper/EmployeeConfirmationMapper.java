package tz.or.mkapafoundation.hrmis.service.mapper;


import tz.or.mkapafoundation.hrmis.domain.*;
import tz.or.mkapafoundation.hrmis.service.dto.EmployeeConfirmationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeeConfirmation} and its DTO {@link EmployeeConfirmationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmployeeConfirmationMapper extends EntityMapper<EmployeeConfirmationDTO, EmployeeConfirmation> {



    default EmployeeConfirmation fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmployeeConfirmation employeeConfirmation = new EmployeeConfirmation();
        employeeConfirmation.setId(id);
        return employeeConfirmation;
    }
}
