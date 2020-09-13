package tz.or.mkapafoundation.hrmis.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link tz.or.mkapafoundation.hrmis.domain.GeographicZone} entity.
 */
public class GeographicZoneDTO implements Serializable {
    
    private Long id;

    
    private String code;

    @NotNull
    private String name;

    private Float latitude;

    private Float longitude;


    private Long parentId;

    private String parentName;

    private Long levelId;

    private String levelName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long geographicZoneId) {
        this.parentId = geographicZoneId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String geographicZoneName) {
        this.parentName = geographicZoneName;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long geographicLevelId) {
        this.levelId = geographicLevelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String geographicLevelName) {
        this.levelName = geographicLevelName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GeographicZoneDTO)) {
            return false;
        }

        return id != null && id.equals(((GeographicZoneDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GeographicZoneDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", parentId=" + getParentId() +
            ", parentName='" + getParentName() + "'" +
            ", levelId=" + getLevelId() +
            ", levelName='" + getLevelName() + "'" +
            "}";
    }
}
