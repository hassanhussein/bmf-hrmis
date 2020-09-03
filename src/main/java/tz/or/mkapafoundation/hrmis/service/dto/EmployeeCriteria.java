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

    private StringFilter firstName;

    private StringFilter middleName;

    private StringFilter lastName;

    private StringFilter gender;

    private LocalDateFilter birthDate;

    private StringFilter email;

    private StringFilter cellPhone;

    private StringFilter maritalStatus;

    private BooleanFilter active;

    private LocalDateFilter contractStartDate;

    private LocalDateFilter contractEndDate;

    private StringFilter bankName;

    private StringFilter branchName;

    private StringFilter bankAccount;

    private StringFilter insuranceRegistrationNumber;

    private LongFilter districtId;

    private LongFilter facilityId;

    private LongFilter categoryId;

    private LongFilter trainingId;

    private LongFilter carderId;

    private LongFilter departMentCode;

    private LongFilter attachmentId;

    private LongFilter confirmationId;

    private LongFilter projectId;

    private LongFilter departmentIdId;

    public EmployeeCriteria() {
    }

    public EmployeeCriteria(EmployeeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.employeeNumber = other.employeeNumber == null ? null : other.employeeNumber.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.middleName = other.middleName == null ? null : other.middleName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.birthDate = other.birthDate == null ? null : other.birthDate.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.cellPhone = other.cellPhone == null ? null : other.cellPhone.copy();
        this.maritalStatus = other.maritalStatus == null ? null : other.maritalStatus.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.contractStartDate = other.contractStartDate == null ? null : other.contractStartDate.copy();
        this.contractEndDate = other.contractEndDate == null ? null : other.contractEndDate.copy();
        this.bankName = other.bankName == null ? null : other.bankName.copy();
        this.branchName = other.branchName == null ? null : other.branchName.copy();
        this.bankAccount = other.bankAccount == null ? null : other.bankAccount.copy();
        this.insuranceRegistrationNumber = other.insuranceRegistrationNumber == null ? null : other.insuranceRegistrationNumber.copy();
        this.districtId = other.districtId == null ? null : other.districtId.copy();
        this.facilityId = other.facilityId == null ? null : other.facilityId.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.trainingId = other.trainingId == null ? null : other.trainingId.copy();
        this.carderId = other.carderId == null ? null : other.carderId.copy();
        this.departMentCode = other.departMentCode == null ? null : other.departMentCode.copy();
        this.attachmentId = other.attachmentId == null ? null : other.attachmentId.copy();
        this.confirmationId = other.confirmationId == null ? null : other.confirmationId.copy();
        this.projectId = other.projectId == null ? null : other.projectId.copy();
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

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getMiddleName() {
        return middleName;
    }

    public void setMiddleName(StringFilter middleName) {
        this.middleName = middleName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getGender() {
        return gender;
    }

    public void setGender(StringFilter gender) {
        this.gender = gender;
    }

    public LocalDateFilter getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateFilter birthDate) {
        this.birthDate = birthDate;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(StringFilter cellPhone) {
        this.cellPhone = cellPhone;
    }

    public StringFilter getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(StringFilter maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
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

    public LongFilter getDepartMentCode() {
        return departMentCode;
    }

    public void setDepartMentCode(LongFilter departMentCode) {
        this.departMentCode = departMentCode;
    }

    public LongFilter getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(LongFilter attachmentId) {
        this.attachmentId = attachmentId;
    }

    public LongFilter getConfirmationId() {
        return confirmationId;
    }

    public void setConfirmationId(LongFilter confirmationId) {
        this.confirmationId = confirmationId;
    }

    public LongFilter getProjectId() {
        return projectId;
    }

    public void setProjectId(LongFilter projectId) {
        this.projectId = projectId;
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
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(middleName, that.middleName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(birthDate, that.birthDate) &&
            Objects.equals(email, that.email) &&
            Objects.equals(cellPhone, that.cellPhone) &&
            Objects.equals(maritalStatus, that.maritalStatus) &&
            Objects.equals(active, that.active) &&
            Objects.equals(contractStartDate, that.contractStartDate) &&
            Objects.equals(contractEndDate, that.contractEndDate) &&
            Objects.equals(bankName, that.bankName) &&
            Objects.equals(branchName, that.branchName) &&
            Objects.equals(bankAccount, that.bankAccount) &&
            Objects.equals(insuranceRegistrationNumber, that.insuranceRegistrationNumber) &&
            Objects.equals(districtId, that.districtId) &&
            Objects.equals(facilityId, that.facilityId) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(trainingId, that.trainingId) &&
            Objects.equals(carderId, that.carderId) &&
            Objects.equals(departMentCode, that.departMentCode) &&
            Objects.equals(attachmentId, that.attachmentId) &&
            Objects.equals(confirmationId, that.confirmationId) &&
            Objects.equals(projectId, that.projectId) &&
            Objects.equals(departmentIdId, that.departmentIdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        employeeNumber,
        firstName,
        middleName,
        lastName,
        gender,
        birthDate,
        email,
        cellPhone,
        maritalStatus,
        active,
        contractStartDate,
        contractEndDate,
        bankName,
        branchName,
        bankAccount,
        insuranceRegistrationNumber,
        districtId,
        facilityId,
        categoryId,
        trainingId,
        carderId,
        departMentCode,
        attachmentId,
        confirmationId,
        projectId,
        departmentIdId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (employeeNumber != null ? "employeeNumber=" + employeeNumber + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (middleName != null ? "middleName=" + middleName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (gender != null ? "gender=" + gender + ", " : "") +
                (birthDate != null ? "birthDate=" + birthDate + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (cellPhone != null ? "cellPhone=" + cellPhone + ", " : "") +
                (maritalStatus != null ? "maritalStatus=" + maritalStatus + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (contractStartDate != null ? "contractStartDate=" + contractStartDate + ", " : "") +
                (contractEndDate != null ? "contractEndDate=" + contractEndDate + ", " : "") +
                (bankName != null ? "bankName=" + bankName + ", " : "") +
                (branchName != null ? "branchName=" + branchName + ", " : "") +
                (bankAccount != null ? "bankAccount=" + bankAccount + ", " : "") +
                (insuranceRegistrationNumber != null ? "insuranceRegistrationNumber=" + insuranceRegistrationNumber + ", " : "") +
                (districtId != null ? "districtId=" + districtId + ", " : "") +
                (facilityId != null ? "facilityId=" + facilityId + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
                (trainingId != null ? "trainingId=" + trainingId + ", " : "") +
                (carderId != null ? "carderId=" + carderId + ", " : "") +
                (departMentCode != null ? "departMentCode=" + departMentCode + ", " : "") +
                (attachmentId != null ? "attachmentId=" + attachmentId + ", " : "") +
                (confirmationId != null ? "confirmationId=" + confirmationId + ", " : "") +
                (projectId != null ? "projectId=" + projectId + ", " : "") +
                (departmentIdId != null ? "departmentIdId=" + departmentIdId + ", " : "") +
            "}";
    }

}
