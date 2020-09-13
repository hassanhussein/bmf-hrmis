package tz.or.mkapafoundation.hrmis.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Employee.
 */
@Entity
@Table(name = "employees")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "employee_number", nullable = false)
    private String employeeNumber;

    @Column(name = "contract_start_date")
    private LocalDate contractStartDate;

    @Column(name = "contract_end_date")
    private LocalDate contractEndDate;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "bank_account")
    private String bankAccount;

    @Column(name = "insurance_registration_number")
    private String insuranceRegistrationNumber;

    @Column(name = "date_joining")
    private LocalDate dateJoining;

    @Column(name = "designation")
    private String designation;

    @NotNull
    @Column(name = "district_id", nullable = false)
    private Long districtId;

    @Column(name = "facility_id")
    private Long facilityId;

    @NotNull
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "training_id")
    private Long trainingId;

    @Column(name = "carder_id")
    private Long carderId;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "confirmation_id")
    private Long confirmationId;

    @Column(name = "is_confirmed")
    private Boolean isConfirmed;

    @Column(name = "confirmation_letter_name")
    private String confirmationLetterName;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "employee")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Attachment> attachments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "employees", allowSetters = true)
    private Department departmentId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public Employee employeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
        return this;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public LocalDate getContractStartDate() {
        return contractStartDate;
    }

    public Employee contractStartDate(LocalDate contractStartDate) {
        this.contractStartDate = contractStartDate;
        return this;
    }

    public void setContractStartDate(LocalDate contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public LocalDate getContractEndDate() {
        return contractEndDate;
    }

    public Employee contractEndDate(LocalDate contractEndDate) {
        this.contractEndDate = contractEndDate;
        return this;
    }

    public void setContractEndDate(LocalDate contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public String getBankName() {
        return bankName;
    }

    public Employee bankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public Employee branchName(String branchName) {
        this.branchName = branchName;
        return this;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public Employee bankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
        return this;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getInsuranceRegistrationNumber() {
        return insuranceRegistrationNumber;
    }

    public Employee insuranceRegistrationNumber(String insuranceRegistrationNumber) {
        this.insuranceRegistrationNumber = insuranceRegistrationNumber;
        return this;
    }

    public void setInsuranceRegistrationNumber(String insuranceRegistrationNumber) {
        this.insuranceRegistrationNumber = insuranceRegistrationNumber;
    }

    public LocalDate getDateJoining() {
        return dateJoining;
    }

    public Employee dateJoining(LocalDate dateJoining) {
        this.dateJoining = dateJoining;
        return this;
    }

    public void setDateJoining(LocalDate dateJoining) {
        this.dateJoining = dateJoining;
    }

    public String getDesignation() {
        return designation;
    }

    public Employee designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public Employee districtId(Long districtId) {
        this.districtId = districtId;
        return this;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Long getFacilityId() {
        return facilityId;
    }

    public Employee facilityId(Long facilityId) {
        this.facilityId = facilityId;
        return this;
    }

    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Employee categoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getTrainingId() {
        return trainingId;
    }

    public Employee trainingId(Long trainingId) {
        this.trainingId = trainingId;
        return this;
    }

    public void setTrainingId(Long trainingId) {
        this.trainingId = trainingId;
    }

    public Long getCarderId() {
        return carderId;
    }

    public Employee carderId(Long carderId) {
        this.carderId = carderId;
        return this;
    }

    public void setCarderId(Long carderId) {
        this.carderId = carderId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public Employee departmentName(String departmentName) {
        this.departmentName = departmentName;
        return this;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getConfirmationId() {
        return confirmationId;
    }

    public Employee confirmationId(Long confirmationId) {
        this.confirmationId = confirmationId;
        return this;
    }

    public void setConfirmationId(Long confirmationId) {
        this.confirmationId = confirmationId;
    }

    public Boolean isIsConfirmed() {
        return isConfirmed;
    }

    public Employee isConfirmed(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
        return this;
    }

    public void setIsConfirmed(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public String getConfirmationLetterName() {
        return confirmationLetterName;
    }

    public Employee confirmationLetterName(String confirmationLetterName) {
        this.confirmationLetterName = confirmationLetterName;
        return this;
    }

    public void setConfirmationLetterName(String confirmationLetterName) {
        this.confirmationLetterName = confirmationLetterName;
    }

    public String getProjectName() {
        return projectName;
    }

    public Employee projectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Boolean isActive() {
        return active;
    }

    public Employee active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public Employee attachments(Set<Attachment> attachments) {
        this.attachments = attachments;
        return this;
    }

    public Employee addAttachments(Attachment attachment) {
        this.attachments.add(attachment);
        //attachment.setEmployee(this);
        return this;
    }

    public Employee removeAttachments(Attachment attachment) {
        this.attachments.remove(attachment);
        attachment.setEmployee(null);
        return this;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Department getDepartmentId() {
        return departmentId;
    }

    public Employee departmentId(Department department) {
        this.departmentId = department;
        return this;
    }

    public void setDepartmentId(Department department) {
        this.departmentId = department;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", employeeNumber='" + getEmployeeNumber() + "'" +
            ", contractStartDate='" + getContractStartDate() + "'" +
            ", contractEndDate='" + getContractEndDate() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", branchName='" + getBranchName() + "'" +
            ", bankAccount='" + getBankAccount() + "'" +
            ", insuranceRegistrationNumber='" + getInsuranceRegistrationNumber() + "'" +
            ", dateJoining='" + getDateJoining() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", districtId=" + getDistrictId() +
            ", facilityId=" + getFacilityId() +
            ", categoryId=" + getCategoryId() +
            ", trainingId=" + getTrainingId() +
            ", carderId=" + getCarderId() +
            ", departmentName='" + getDepartmentName() + "'" +
            ", confirmationId=" + getConfirmationId() +
            ", isConfirmed='" + isIsConfirmed() + "'" +
            ", confirmationLetterName='" + getConfirmationLetterName() + "'" +
            ", projectName='" + getProjectName() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
