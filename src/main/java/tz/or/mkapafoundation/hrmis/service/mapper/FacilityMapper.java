package tz.or.mkapafoundation.hrmis.service.mapper;


import tz.or.mkapafoundation.hrmis.domain.*;
import tz.or.mkapafoundation.hrmis.service.dto.FacilityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Facility} and its DTO {@link FacilityDTO}.
 */
@Mapper(componentModel = "spring", uses = {GeographicZoneMapper.class, FacilityTypeMapper.class})
public interface FacilityMapper extends EntityMapper<FacilityDTO, Facility> {

    @Mapping(source = "district.id", target = "districtId")
    @Mapping(source = "district.name", target = "districtName")
    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "type.name", target = "typeName")
    FacilityDTO toDto(Facility facility);

    @Mapping(source = "districtId", target = "district")
    @Mapping(source = "typeId", target = "type")
    Facility toEntity(FacilityDTO facilityDTO);

    default Facility fromId(Long id) {
        if (id == null) {
            return null;
        }
        Facility facility = new Facility();
        facility.setId(id);
        return facility;
    }
}
