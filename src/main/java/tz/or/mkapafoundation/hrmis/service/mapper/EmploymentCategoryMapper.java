package tz.or.mkapafoundation.hrmis.service.mapper;


import tz.or.mkapafoundation.hrmis.domain.*;
import tz.or.mkapafoundation.hrmis.service.dto.EmploymentCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmploymentCategory} and its DTO {@link EmploymentCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmploymentCategoryMapper extends EntityMapper<EmploymentCategoryDTO, EmploymentCategory> {



    default EmploymentCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmploymentCategory employmentCategory = new EmploymentCategory();
        employmentCategory.setId(id);
        return employmentCategory;
    }
}
