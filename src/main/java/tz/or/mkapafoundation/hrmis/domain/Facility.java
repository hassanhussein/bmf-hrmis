package tz.or.mkapafoundation.hrmis.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Facility.
 */
@Entity
@Table(name = "facilities")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Facility implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    
    @Column(name = "code", unique = true)
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "postal_address")
    private String postalAddress;

    @Column(name = "ward")
    private String ward;

    @Column(name = "village")
    private String village;

    @Column(name = "comment")
    private String comment;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "operatedby")
    private String operatedby;

    @ManyToOne
    @JsonIgnoreProperties(value = "facilities", allowSetters = true)
    private GeographicZone district;

    @ManyToOne
    @JsonIgnoreProperties(value = "facilities", allowSetters = true)
    private FacilityType type;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isActive() {
        return active;
    }

    public Facility active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCode() {
        return code;
    }

    public Facility code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Facility name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public Facility postalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
        return this;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getWard() {
        return ward;
    }

    public Facility ward(String ward) {
        this.ward = ward;
        return this;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getVillage() {
        return village;
    }

    public Facility village(String village) {
        this.village = village;
        return this;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getComment() {
        return comment;
    }

    public Facility comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDescription() {
        return description;
    }

    public Facility description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Facility startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getOperatedby() {
        return operatedby;
    }

    public Facility operatedby(String operatedby) {
        this.operatedby = operatedby;
        return this;
    }

    public void setOperatedby(String operatedby) {
        this.operatedby = operatedby;
    }

    public GeographicZone getDistrict() {
        return district;
    }

    public Facility district(GeographicZone geographicZone) {
        this.district = geographicZone;
        return this;
    }

    public void setDistrict(GeographicZone geographicZone) {
        this.district = geographicZone;
    }

    public FacilityType getType() {
        return type;
    }

    public Facility type(FacilityType facilityType) {
        this.type = facilityType;
        return this;
    }

    public void setType(FacilityType facilityType) {
        this.type = facilityType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Facility)) {
            return false;
        }
        return id != null && id.equals(((Facility) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Facility{" +
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
            "}";
    }
}
