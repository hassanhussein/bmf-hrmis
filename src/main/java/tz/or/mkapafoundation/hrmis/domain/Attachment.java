package tz.or.mkapafoundation.hrmis.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Attachment.
 */
@Entity
@Table(name = "attachments")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "content_id")
    private String contentId;

    @Column(name = "content_length")
    private Long contentLength;

    @Column(name = "mime_type")
    private String mimeType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "attachments", allowSetters = true)
    private AttachmentType type;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "attachments", allowSetters = true)
    private EmployeeRecord employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Attachment name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentId() {
        return contentId;
    }

    public Attachment contentId(String contentId) {
        this.contentId = contentId;
        return this;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public Long getContentLength() {
        return contentLength;
    }

    public Attachment contentLength(Long contentLength) {
        this.contentLength = contentLength;
        return this;
    }

    public void setContentLength(Long contentLength) {
        this.contentLength = contentLength;
    }

    public String getMimeType() {
        return mimeType;
    }

    public Attachment mimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public AttachmentType getType() {
        return type;
    }

    public Attachment type(AttachmentType attachmentType) {
        this.type = attachmentType;
        return this;
    }

    public void setType(AttachmentType attachmentType) {
        this.type = attachmentType;
    }

    public EmployeeRecord getEmployee() {
        return employee;
    }

    public Attachment employee(EmployeeRecord employeeRecord) {
        this.employee = employeeRecord;
        return this;
    }

    public void setEmployee(EmployeeRecord employeeRecord) {
        this.employee = employeeRecord;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attachment)) {
            return false;
        }
        return id != null && id.equals(((Attachment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attachment{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", contentId='" + getContentId() + "'" +
            ", contentLength=" + getContentLength() +
            ", mimeType='" + getMimeType() + "'" +
            "}";
    }
}
