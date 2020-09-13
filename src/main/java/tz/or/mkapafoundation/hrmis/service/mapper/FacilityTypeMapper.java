package tz.or.mkapafoundation.hrmis.service.mapper;


import tz.or.mkapafoundation.hrmis.domain.*;
import tz.or.mkapafoundation.hrmis.service.dto.FacilityTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FacilityType} and its DTO {@link FacilityTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FacilityTypeMapper extends EntityMapper<FacilityTypeDTO, FacilityType> {



    default FacilityType fromId(Long id) {
        if (id == null) {
            return null;
        }
        FacilityType facilityType = new FacilityType();
        facilityType.setId(id);
        return facilityType;
    }
}
