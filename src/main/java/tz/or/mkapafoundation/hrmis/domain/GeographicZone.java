package tz.or.mkapafoundation.hrmis.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A GeographicZone.
 */
@Entity
@Table(name = "geographic_zones")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GeographicZone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Column(name = "code", unique = true)
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "longitude")
    private Float longitude;

    @ManyToOne
    @JsonIgnoreProperties(value = "geographicZones", allowSetters = true)
    private GeographicZone parent;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "geographicZones", allowSetters = true)
    private GeographicLevel level;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public GeographicZone code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public GeographicZone name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getLatitude() {
        return latitude;
    }

    public GeographicZone latitude(Float latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public GeographicZone longitude(Float longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public GeographicZone getParent() {
        return parent;
    }

    public GeographicZone parent(GeographicZone geographicZone) {
        this.parent = geographicZone;
        return this;
    }

    public void setParent(GeographicZone geographicZone) {
        this.parent = geographicZone;
    }

    public GeographicLevel getLevel() {
        return level;
    }

    public GeographicZone level(GeographicLevel geographicLevel) {
        this.level = geographicLevel;
        return this;
    }

    public void setLevel(GeographicLevel geographicLevel) {
        this.level = geographicLevel;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GeographicZone)) {
            return false;
        }
        return id != null && id.equals(((GeographicZone) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GeographicZone{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            "}";
    }
}
