package tz.or.mkapafoundation.hrmis.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link tz.or.mkapafoundation.hrmis.domain.Employee} entity.
 */
public class EmployeeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String employeeNumber;

    private LocalDate contractStartDate;

    private LocalDate contractEndDate;

    private String bankName;

    private String branchName;

    private String bankAccount;

    private String insuranceRegistrationNumber;

    private LocalDate dateJoining;

    private String designation;

    @NotNull
    private Long districtId;

    private Long facilityId;

    @NotNull
    private Long categoryId;

    private Long trainingId;

    private Long carderId;

    private String departmentName;

    private Long confirmationId;

    private Boolean isConfirmed;

    private String confirmationLetterName;

    private String projectName;

    private Boolean active;


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

    public LocalDate getDateJoining() {
        return dateJoining;
    }

    public void setDateJoining(LocalDate dateJoining) {
        this.dateJoining = dateJoining;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getConfirmationId() {
        return confirmationId;
    }

    public void setConfirmationId(Long confirmationId) {
        this.confirmationId = confirmationId;
    }

    public Boolean isIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public String getConfirmationLetterName() {
        return confirmationLetterName;
    }

    public void setConfirmationLetterName(String confirmationLetterName) {
        this.confirmationLetterName = confirmationLetterName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
            ", departmentIdId=" + getDepartmentIdId() +
            "}";
    }
}
