package tz.or.mkapafoundation.hrmis.service.dto;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link tz.or.mkapafoundation.hrmis.domain.EmployeeConfirmation} entity.
 */
public class EmployeeConfirmationDTO implements Serializable {
    
    private Long id;

    private Boolean isConfirmed;

    private String confirmationLetter;

    private LocalDate date;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public String getConfirmationLetter() {
        return confirmationLetter;
    }

    public void setConfirmationLetter(String confirmationLetter) {
        this.confirmationLetter = confirmationLetter;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeConfirmationDTO)) {
            return false;
        }

        return id != null && id.equals(((EmployeeConfirmationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeConfirmationDTO{" +
            "id=" + getId() +
            ", isConfirmed='" + isIsConfirmed() + "'" +
            ", confirmationLetter='" + getConfirmationLetter() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
