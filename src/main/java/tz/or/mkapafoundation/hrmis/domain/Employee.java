package tz.or.mkapafoundation.hrmis.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

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

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "cell_phone")
    private String cellPhone;

    @Column(name = "marital_status")
    private String maritalStatus;

    @Column(name = "active")
    private Boolean active;

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

    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "facility_id")
    private Long facilityId;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "training_id")
    private Long trainingId;

    @Column(name = "carder_id")
    private Long carderId;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "picture_content_type")
    private String pictureContentType;

    @Column(name = "depart_ment_code")
    private Long departMentCode;

    @Column(name = "attachment_id")
    private Long attachmentId;

    @Column(name = "confirmation_id")
    private Long confirmationId;

    @Column(name = "project_id")
    private Long projectId;

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

    public String getFirstName() {
        return firstName;
    }

    public Employee firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Employee middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public Employee lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public Employee gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Employee birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public Employee email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public Employee cellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
        return this;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public Employee maritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
        return this;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
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

    public byte[] getPicture() {
        return picture;
    }

    public Employee picture(byte[] picture) {
        this.picture = picture;
        return this;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return pictureContentType;
    }

    public Employee pictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
        return this;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public Long getDepartMentCode() {
        return departMentCode;
    }

    public Employee departMentCode(Long departMentCode) {
        this.departMentCode = departMentCode;
        return this;
    }

    public void setDepartMentCode(Long departMentCode) {
        this.departMentCode = departMentCode;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public Employee attachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
        return this;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
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

    public Long getProjectId() {
        return projectId;
    }

    public Employee projectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", gender='" + getGender() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", email='" + getEmail() + "'" +
            ", cellPhone='" + getCellPhone() + "'" +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", active='" + isActive() + "'" +
            ", contractStartDate='" + getContractStartDate() + "'" +
            ", contractEndDate='" + getContractEndDate() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", branchName='" + getBranchName() + "'" +
            ", bankAccount='" + getBankAccount() + "'" +
            ", insuranceRegistrationNumber='" + getInsuranceRegistrationNumber() + "'" +
            ", districtId=" + getDistrictId() +
            ", facilityId=" + getFacilityId() +
            ", categoryId=" + getCategoryId() +
            ", trainingId=" + getTrainingId() +
            ", carderId=" + getCarderId() +
            ", picture='" + getPicture() + "'" +
            ", pictureContentType='" + getPictureContentType() + "'" +
            ", departMentCode=" + getDepartMentCode() +
            ", attachmentId=" + getAttachmentId() +
            ", confirmationId=" + getConfirmationId() +
            ", projectId=" + getProjectId() +
            "}";
    }
}
