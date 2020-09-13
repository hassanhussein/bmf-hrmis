package tz.or.mkapafoundation.hrmis.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link tz.or.mkapafoundation.hrmis.domain.Attachment} entity.
 */
public class AttachmentDTO implements Serializable {
    
    private Long id;

    private String name;

    private String contentId;

    private Long contentLength;

    private String mimeType;


    private Long typeId;

    private String typeName;

    private Long employeeId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public Long getContentLength() {
        return contentLength;
    }

    public void setContentLength(Long contentLength) {
        this.contentLength = contentLength;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long attachmentTypeId) {
        this.typeId = attachmentTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String attachmentTypeName) {
        this.typeName = attachmentTypeName;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeRecordId) {
        this.employeeId = employeeRecordId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttachmentDTO)) {
            return false;
        }

        return id != null && id.equals(((AttachmentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttachmentDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", contentId='" + getContentId() + "'" +
            ", contentLength=" + getContentLength() +
            ", mimeType='" + getMimeType() + "'" +
            ", typeId=" + getTypeId() +
            ", typeName='" + getTypeName() + "'" +
            ", employeeId=" + getEmployeeId() +
            "}";
    }
}
