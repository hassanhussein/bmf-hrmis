package tz.or.mkapafoundation.hrmis.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link tz.or.mkapafoundation.hrmis.domain.Facility} entity.
 */
public class FacilityDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Boolean active;

    
    private String code;

    @NotNull
    private String name;

    private String postalAddress;

    private String ward;

    private String village;

    private String comment;

    private String description;

    private LocalDate startDate;

    private String operatedby;


    private Long districtId;

    private String districtName;

    private Long typeId;

    private String typeName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getOperatedby() {
        return operatedby;
    }

    public void setOperatedby(String operatedby) {
        this.operatedby = operatedby;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long geographicZoneId) {
        this.districtId = geographicZoneId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String geographicZoneName) {
        this.districtName = geographicZoneName;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long facilityTypeId) {
        this.typeId = facilityTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String facilityTypeName) {
        this.typeName = facilityTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FacilityDTO)) {
            return false;
        }

        return id != null && id.equals(((FacilityDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FacilityDTO{" +
            "id=" + getId() +
            ", active='" + isActive() + "'" +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", postalAddress='" + getPostalAddress() + "'" +
            ", ward='" + getWard() + "'" +
            ", village='" + getVillage() + "'" +
            ", comment='" + getComment() + "'" +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", operatedby='" + getOperatedby() + "'" +
            ", districtId=" + getDistrictId() +
            ", districtName='" + getDistrictName() + "'" +
            ", typeId=" + getTypeId() +
            ", typeName='" + getTypeName() + "'" +
            "}";
    }
}
