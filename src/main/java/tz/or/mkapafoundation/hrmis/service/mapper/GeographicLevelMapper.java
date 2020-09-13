package tz.or.mkapafoundation.hrmis.service.mapper;


import tz.or.mkapafoundation.hrmis.domain.*;
import tz.or.mkapafoundation.hrmis.service.dto.GeographicLevelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GeographicLevel} and its DTO {@link GeographicLevelDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GeographicLevelMapper extends EntityMapper<GeographicLevelDTO, GeographicLevel> {



    default GeographicLevel fromId(Long id) {
        if (id == null) {
            return null;
        }
        GeographicLevel geographicLevel = new GeographicLevel();
        geographicLevel.setId(id);
        return geographicLevel;
    }
}
