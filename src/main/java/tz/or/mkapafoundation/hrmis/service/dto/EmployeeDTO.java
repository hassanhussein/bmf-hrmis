package tz.or.mkapafoundation.hrmis.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link tz.or.mkapafoundation.hrmis.domain.Employee} entity.
 */
public class EmployeeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String employeeNumber;

    private String firstName;

    private String middleName;

    private String lastName;

    private String gender;

    private LocalDate birthDate;

    
    private String email;

    private String cellPhone;

    private String maritalStatus;

    private Boolean active;

    private LocalDate contractStartDate;

    private LocalDate contractEndDate;

    private String bankName;

    private String branchName;

    private String bankAccount;

    private String insuranceRegistrationNumber;

    private Long districtId;

    private Long facilityId;

    private Long categoryId;

    private Long trainingId;

    private Long carderId;

    @Lob
    private byte[] picture;

    private String pictureContentType;
    private Long departMentCode;

    private Long attachmentId;

    private Long confirmationId;

    private Long projectId;


    private Long departmentIdId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(LocalDate contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public LocalDate getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(LocalDate contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getInsuranceRegistrationNumber() {
        return insuranceRegistrationNumber;
    }

    public void setInsuranceRegistrationNumber(String insuranceRegistrationNumber) {
        this.insuranceRegistrationNumber = insuranceRegistrationNumber;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Long getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Long trainingId) {
        this.trainingId = trainingId;
    }

    public Long getCarderId() {
        return carderId;
    }

    public void setCarderId(Long carderId) {
        this.carderId = carderId;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return pictureContentType;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public Long getDepartMentCode() {
        return departMentCode;
    }

    public void setDepartMentCode(Long departMentCode) {
        this.departMentCode = departMentCode;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Long getConfirmationId() {
        return confirmationId;
    }

    public void setConfirmationId(Long confirmationId) {
        this.confirmationId = confirmationId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getDepartmentIdId() {
        return departmentIdId;
    }

    public void setDepartmentIdId(Long departmentId) {
        this.departmentIdId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeDTO)) {
            return false;
        }

        return id != null && id.equals(((EmployeeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeDTO{" +
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
            ", departMentCode=" + getDepartMentCode() +
            ", attachmentId=" + getAttachmentId() +
            ", confirmationId=" + getConfirmationId() +
            ", projectId=" + getProjectId() +
            ", departmentIdId=" + getDepartmentIdId() +
            "}";
    }
}
