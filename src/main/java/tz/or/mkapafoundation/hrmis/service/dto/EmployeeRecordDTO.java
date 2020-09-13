package tz.or.mkapafoundation.hrmis.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link tz.or.mkapafoundation.hrmis.domain.EmployeeRecord} entity.
 */
public class EmployeeRecordDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String firstName;

    private String middleName;

    @NotNull
    private String lastName;

    private String address;

    private String gender;

    @NotNull
    private LocalDate birthDate;

    
    private String email;

    private String cellPhone;

    private String telephone;

    private String maritalStatus;

    @Lob
    private byte[] picture;

    private String pictureContentType;
    @NotNull
    private String employeeNumber;

    private Boolean active;

    private LocalDate dateJoining;

    private Double salary;

    private LocalDate contractStartDate;

    private LocalDate contractEndDate;

    private String bankName;

    private String branchName;

    private String bankAccount;

    private String insuranceRegistrationNumber;


    private Long departmentId;

    private String departmentName;

    private Long employeeTypeId;

    private String employeeTypeName;

    private Long designationId;

    private String designationName;

    private Long facilityId;

    private String facilityName;

    private Long projectId;

    private String projectName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
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

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getDateJoining() {
        return dateJoining;
    }

    public void setDateJoining(LocalDate dateJoining) {
        this.dateJoining = dateJoining;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
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

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getEmployeeTypeId() {
        return employeeTypeId;
    }

    public void setEmployeeTypeId(Long employmentCategoryId) {
        this.employeeTypeId = employmentCategoryId;
    }

    public String getEmployeeTypeName() {
        return employeeTypeName;
    }

    public void setEmployeeTypeName(String employmentCategoryName) {
        this.employeeTypeName = employmentCategoryName;
    }

    public Long getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Long carderId) {
        this.designationId = carderId;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String carderName) {
        this.designationName = carderName;
    }

    public Long getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeRecordDTO)) {
            return false;
        }

        return id != null && id.equals(((EmployeeRecordDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeRecordDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", address='" + getAddress() + "'" +
            ", gender='" + getGender() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", email='" + getEmail() + "'" +
            ", cellPhone='" + getCellPhone() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", picture='" + getPicture() + "'" +
            ", employeeNumber='" + getEmployeeNumber() + "'" +
            ", active='" + isActive() + "'" +
            ", dateJoining='" + getDateJoining() + "'" +
            ", salary=" + getSalary() +
            ", contractStartDate='" + getContractStartDate() + "'" +
            ", contractEndDate='" + getContractEndDate() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", branchName='" + getBranchName() + "'" +
            ", bankAccount='" + getBankAccount() + "'" +
            ", insuranceRegistrationNumber='" + getInsuranceRegistrationNumber() + "'" +
            ", departmentId=" + getDepartmentId() +
            ", departmentName='" + getDepartmentName() + "'" +
            ", employeeTypeId=" + getEmployeeTypeId() +
            ", employeeTypeName='" + getEmployeeTypeName() + "'" +
            ", designationId=" + getDesignationId() +
            ", designationName='" + getDesignationName() + "'" +
            ", facilityId=" + getFacilityId() +
            ", facilityName='" + getFacilityName() + "'" +
            ", projectId=" + getProjectId() +
            ", projectName='" + getProjectName() + "'" +
            "}";
    }
}
