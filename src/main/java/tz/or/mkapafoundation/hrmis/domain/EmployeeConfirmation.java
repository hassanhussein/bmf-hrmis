package tz.or.mkapafoundation.hrmis.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A EmployeeConfirmation.
 */
@Entity
@Table(name = "employee_confirmations")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmployeeConfirmation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "is_confirmed")
    private Boolean isConfirmed;

    @Column(name = "confirmation_letter")
    private String confirmationLetter;

    @Column(name = "date")
    private LocalDate date;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsConfirmed() {
        return isConfirmed;
    }

    public EmployeeConfirmation isConfirmed(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
        return this;
    }

    public void setIsConfirmed(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public String getConfirmationLetter() {
        return confirmationLetter;
    }

    public EmployeeConfirmation confirmationLetter(String confirmationLetter) {
        this.confirmationLetter = confirmationLetter;
        return this;
    }

    public void setConfirmationLetter(String confirmationLetter) {
        this.confirmationLetter = confirmationLetter;
    }

    public LocalDate getDate() {
        return date;
    }

    public EmployeeConfirmation date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeConfirmation)) {
            return false;
        }
        return id != null && id.equals(((EmployeeConfirmation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeConfirmation{" +
            "id=" + getId() +
            ", isConfirmed='" + isIsConfirmed() + "'" +
            ", confirmationLetter='" + getConfirmationLetter() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
