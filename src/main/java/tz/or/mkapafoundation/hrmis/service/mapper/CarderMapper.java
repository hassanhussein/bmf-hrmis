package tz.or.mkapafoundation.hrmis.service.mapper;


import tz.or.mkapafoundation.hrmis.domain.*;
import tz.or.mkapafoundation.hrmis.service.dto.CarderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Carder} and its DTO {@link CarderDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CarderMapper extends EntityMapper<CarderDTO, Carder> {



    default Carder fromId(Long id) {
        if (id == null) {
            return null;
        }
        Carder carder = new Carder();
        carder.setId(id);
        return carder;
    }
}
