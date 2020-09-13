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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link tz.or.mkapafoundation.hrmis.domain.Employee} entity. This class is used
 * in {@link tz.or.mkapafoundation.hrmis.web.rest.EmployeeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter employeeNumber;

    private LocalDateFilter contractStartDate;

    private LocalDateFilter contractEndDate;

    private StringFilter bankName;

    private StringFilter branchName;

    private StringFilter bankAccount;

    private StringFilter insuranceRegistrationNumber;

    private LocalDateFilter dateJoining;

    private StringFilter designation;

    private LongFilter districtId;

    private LongFilter facilityId;

    private LongFilter categoryId;

    private LongFilter trainingId;

    private LongFilter carderId;

    private StringFilter departmentName;

    private LongFilter confirmationId;

    private BooleanFilter isConfirmed;

    private StringFilter confirmationLetterName;

    private StringFilter projectName;

    private BooleanFilter active;

    private LongFilter attachmentsId;

    private LongFilter departmentIdId;

    public EmployeeCriteria() {
    }

    public EmployeeCriteria(EmployeeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.employeeNumber = other.employeeNumber == null ? null : other.employeeNumber.copy();
        this.contractStartDate = other.contractStartDate == null ? null : other.contractStartDate.copy();
        this.contractEndDate = other.contractEndDate == null ? null : other.contractEndDate.copy();
        this.bankName = other.bankName == null ? null : other.bankName.copy();
        this.branchName = other.branchName == null ? null : other.branchName.copy();
        this.bankAccount = other.bankAccount == null ? null : other.bankAccount.copy();
        this.insuranceRegistrationNumber = other.insuranceRegistrationNumber == null ? null : other.insuranceRegistrationNumber.copy();
        this.dateJoining = other.dateJoining == null ? null : other.dateJoining.copy();
        this.designation = other.designation == null ? null : other.designation.copy();
        this.districtId = other.districtId == null ? null : other.districtId.copy();
        this.facilityId = other.facilityId == null ? null : other.facilityId.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.trainingId = other.trainingId == null ? null : other.trainingId.copy();
        this.carderId = other.carderId == null ? null : other.carderId.copy();
        this.departmentName = other.departmentName == null ? null : other.departmentName.copy();
        this.confirmationId = other.confirmationId == null ? null : other.confirmationId.copy();
        this.isConfirmed = other.isConfirmed == null ? null : other.isConfirmed.copy();
        this.confirmationLetterName = other.confirmationLetterName == null ? null : other.confirmationLetterName.copy();
        this.projectName = other.projectName == null ? null : other.projectName.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.attachmentsId = other.attachmentsId == null ? null : other.attachmentsId.copy();
        this.departmentIdId = other.departmentIdId == null ? null : other.departmentIdId.copy();
    }

    @Override
    public EmployeeCriteria copy() {
        return new EmployeeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(StringFilter employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public LocalDateFilter getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(LocalDateFilter contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public LocalDateFilter getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(LocalDateFilter contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public StringFilter getBankName() {
        return bankName;
    }

    public void setBankName(StringFilter bankName) {
        this.bankName = bankName;
    }

    public StringFilter getBranchName() {
        return branchName;
    }

    public void setBranchName(StringFilter branchName) {
        this.branchName = branchName;
    }

    public StringFilter getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(StringFilter bankAccount) {
        this.bankAccount = bankAccount;
    }

    public StringFilter getInsuranceRegistrationNumber() {
        return insuranceRegistrationNumber;
    }

    public void setInsuranceRegistrationNumber(StringFilter insuranceRegistrationNumber) {
        this.insuranceRegistrationNumber = insuranceRegistrationNumber;
    }

    public LocalDateFilter getDateJoining() {
        return dateJoining;
    }

    public void setDateJoining(LocalDateFilter dateJoining) {
        this.dateJoining = dateJoining;
    }

    public StringFilter getDesignation() {
        return designation;
    }

    public void setDesignation(StringFilter designation) {
        this.designation = designation;
    }

    public LongFilter getDistrictId() {
        return districtId;
    }

    public void setDistrictId(LongFilter districtId) {
        this.districtId = districtId;
    }

    public LongFilter getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(LongFilter facilityId) {
        this.facilityId = facilityId;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    public LongFilter getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(LongFilter trainingId) {
        this.trainingId = trainingId;
    }

    public LongFilter getCarderId() {
        return carderId;
    }

    public void setCarderId(LongFilter carderId) {
        this.carderId = carderId;
    }

    public StringFilter getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(StringFilter departmentName) {
        this.departmentName = departmentName;
    }

    public LongFilter getConfirmationId() {
        return confirmationId;
    }

    public void setConfirmationId(LongFilter confirmationId) {
        this.confirmationId = confirmationId;
    }

    public BooleanFilter getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(BooleanFilter isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public StringFilter getConfirmationLetterName() {
        return confirmationLetterName;
    }

    public void setConfirmationLetterName(StringFilter confirmationLetterName) {
        this.confirmationLetterName = confirmationLetterName;
    }

    public StringFilter getProjectName() {
        return projectName;
    }

    public void setProjectName(StringFilter projectName) {
        this.projectName = projectName;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public LongFilter getAttachmentsId() {
        return attachmentsId;
    }

    public void setAttachmentsId(LongFilter attachmentsId) {
        this.attachmentsId = attachmentsId;
    }

    public LongFilter getDepartmentIdId() {
        return departmentIdId;
    }

    public void setDepartmentIdId(LongFilter departmentIdId) {
        this.departmentIdId = departmentIdId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmployeeCriteria that = (EmployeeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(employeeNumber, that.employeeNumber) &&
            Objects.equals(contractStartDate, that.contractStartDate) &&
            Objects.equals(contractEndDate, that.contractEndDate) &&
            Objects.equals(bankName, that.bankName) &&
            Objects.equals(branchName, that.branchName) &&
            Objects.equals(bankAccount, that.bankAccount) &&
            Objects.equals(insuranceRegistrationNumber, that.insuranceRegistrationNumber) &&
            Objects.equals(dateJoining, that.dateJoining) &&
            Objects.equals(designation, that.designation) &&
            Objects.equals(districtId, that.districtId) &&
            Objects.equals(facilityId, that.facilityId) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(trainingId, that.trainingId) &&
            Objects.equals(carderId, that.carderId) &&
            Objects.equals(departmentName, that.departmentName) &&
            Objects.equals(confirmationId, that.confirmationId) &&
            Objects.equals(isConfirmed, that.isConfirmed) &&
            Objects.equals(confirmationLetterName, that.confirmationLetterName) &&
            Objects.equals(projectName, that.projectName) &&
            Objects.equals(active, that.active) &&
            Objects.equals(attachmentsId, that.attachmentsId) &&
            Objects.equals(departmentIdId, that.departmentIdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        employeeNumber,
        contractStartDate,
        contractEndDate,
        bankName,
        branchName,
        bankAccount,
        insuranceRegistrationNumber,
        dateJoining,
        designation,
        districtId,
        facilityId,
        categoryId,
        trainingId,
        carderId,
        departmentName,
        confirmationId,
        isConfirmed,
        confirmationLetterName,
        projectName,
        active,
        attachmentsId,
        departmentIdId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (employeeNumber != null ? "employeeNumber=" + employeeNumber + ", " : "") +
                (contractStartDate != null ? "contractStartDate=" + contractStartDate + ", " : "") +
                (contractEndDate != null ? "contractEndDate=" + contractEndDate + ", " : "") +
                (bankName != null ? "bankName=" + bankName + ", " : "") +
                (branchName != null ? "branchName=" + branchName + ", " : "") +
                (bankAccount != null ? "bankAccount=" + bankAccount + ", " : "") +
                (insuranceRegistrationNumber != null ? "insuranceRegistrationNumber=" + insuranceRegistrationNumber + ", " : "") +
                (dateJoining != null ? "dateJoining=" + dateJoining + ", " : "") +
                (designation != null ? "designation=" + designation + ", " : "") +
                (districtId != null ? "districtId=" + districtId + ", " : "") +
                (facilityId != null ? "facilityId=" + facilityId + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
                (trainingId != null ? "trainingId=" + trainingId + ", " : "") +
                (carderId != null ? "carderId=" + carderId + ", " : "") +
                (departmentName != null ? "departmentName=" + departmentName + ", " : "") +
                (confirmationId != null ? "confirmationId=" + confirmationId + ", " : "") +
                (isConfirmed != null ? "isConfirmed=" + isConfirmed + ", " : "") +
                (confirmationLetterName != null ? "confirmationLetterName=" + confirmationLetterName + ", " : "") +
                (projectName != null ? "projectName=" + projectName + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (attachmentsId != null ? "attachmentsId=" + attachmentsId + ", " : "") +
                (departmentIdId != null ? "departmentIdId=" + departmentIdId + ", " : "") +
            "}";
    }

}
