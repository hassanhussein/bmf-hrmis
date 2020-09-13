package tz.or.mkapafoundation.hrmis.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link tz.or.mkapafoundation.hrmis.domain.GeographicLevel} entity. This class is used
 * in {@link tz.or.mkapafoundation.hrmis.web.rest.GeographicLevelResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /geographic-levels?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GeographicLevelCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter name;

    private IntegerFilter levelNumber;

    private BooleanFilter active;

    public GeographicLevelCriteria() {
    }

    public GeographicLevelCriteria(GeographicLevelCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.levelNumber = other.levelNumber == null ? null : other.levelNumber.copy();
        this.active = other.active == null ? null : other.active.copy();
    }

    @Override
    public GeographicLevelCriteria copy() {
        return new GeographicLevelCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public IntegerFilter getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(IntegerFilter levelNumber) {
        this.levelNumber = levelNumber;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GeographicLevelCriteria that = (GeographicLevelCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(levelNumber, that.levelNumber) &&
            Objects.equals(active, that.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        name,
        levelNumber,
        active
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GeographicLevelCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (levelNumber != null ? "levelNumber=" + levelNumber + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
            "}";
    }

}
