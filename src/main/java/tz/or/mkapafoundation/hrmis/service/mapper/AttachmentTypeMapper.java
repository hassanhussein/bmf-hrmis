package tz.or.mkapafoundation.hrmis.service.mapper;


import tz.or.mkapafoundation.hrmis.domain.*;
import tz.or.mkapafoundation.hrmis.service.dto.AttachmentTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttachmentType} and its DTO {@link AttachmentTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AttachmentTypeMapper extends EntityMapper<AttachmentTypeDTO, AttachmentType> {



    default AttachmentType fromId(Long id) {
        if (id == null) {
            return null;
        }
        AttachmentType attachmentType = new AttachmentType();
        attachmentType.setId(id);
        return attachmentType;
    }
}
