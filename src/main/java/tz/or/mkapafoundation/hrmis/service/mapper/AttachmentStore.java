package tz.or.mkapafoundation.hrmis.service.mapper;

import org.springframework.content.commons.repository.ContentStore;
import org.springframework.stereotype.Component;

import tz.or.mkapafoundation.hrmis.domain.Attachment;

@Component
public interface AttachmentStore extends ContentStore<Attachment,String> {

}
