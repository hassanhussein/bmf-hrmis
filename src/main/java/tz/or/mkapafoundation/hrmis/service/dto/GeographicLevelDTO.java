package tz.or.mkapafoundation.hrmis.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link tz.or.mkapafoundation.hrmis.domain.GeographicLevel} entity.
 */
public class GeographicLevelDTO implements Serializable {
    
    private Long id;

    
    private String code;

    @NotNull
    private String name;

    
    private Integer levelNumber;

    private Boolean active;

    
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

    public Integer getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(Integer levelNumber) {
        this.levelNumber = levelNumber;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GeographicLevelDTO)) {
            return false;
        }

        return id != null && id.equals(((GeographicLevelDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GeographicLevelDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", levelNumber=" + getLevelNumber() +
            ", active='" + isActive() + "'" +
            "}";
    }
}
