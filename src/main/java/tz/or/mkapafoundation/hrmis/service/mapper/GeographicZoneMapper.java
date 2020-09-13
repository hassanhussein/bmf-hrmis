package tz.or.mkapafoundation.hrmis.service.mapper;


import tz.or.mkapafoundation.hrmis.domain.*;
import tz.or.mkapafoundation.hrmis.service.dto.GeographicZoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GeographicZone} and its DTO {@link GeographicZoneDTO}.
 */
@Mapper(componentModel = "spring", uses = {GeographicLevelMapper.class})
public interface GeographicZoneMapper extends EntityMapper<GeographicZoneDTO, GeographicZone> {

    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    @Mapping(source = "level.id", target = "levelId")
    @Mapping(source = "level.name", target = "levelName")
    GeographicZoneDTO toDto(GeographicZone geographicZone);

    @Mapping(source = "parentId", target = "parent")
    @Mapping(source = "levelId", target = "level")
    GeographicZone toEntity(GeographicZoneDTO geographicZoneDTO);

    default GeographicZone fromId(Long id) {
        if (id == null) {
            return null;
        }
        GeographicZone geographicZone = new GeographicZone();
        geographicZone.setId(id);
        return geographicZone;
    }
}
