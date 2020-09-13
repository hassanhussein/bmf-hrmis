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
 * Criteria class for the {@link tz.or.mkapafoundation.hrmis.domain.EmployeeRecord} entity. This class is used
 * in {@link tz.or.mkapafoundation.hrmis.web.rest.EmployeeRecordResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-records?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeeRecordCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter firstName;

    private StringFilter middleName;

    private StringFilter lastName;

    private StringFilter address;

    private StringFilter gender;

    private LocalDateFilter birthDate;

    private StringFilter email;

    private StringFilter cellPhone;

    private StringFilter telephone;

    private StringFilter maritalStatus;

    private StringFilter employeeNumber;

    private BooleanFilter active;

    private LocalDateFilter dateJoining;

    private DoubleFilter salary;

    private LocalDateFilter contractStartDate;

    private LocalDateFilter contractEndDate;

    private StringFilter bankName;

    private StringFilter branchName;

    private StringFilter bankAccount;

    private StringFilter insuranceRegistrationNumber;

    private LongFilter attachmentsId;

    private LongFilter departmentId;

    private LongFilter employeeTypeId;

    private LongFilter designationId;

    private LongFilter facilityId;

    private LongFilter projectId;

    public EmployeeRecordCriteria() {
    }

    public EmployeeRecordCriteria(EmployeeRecordCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.middleName = other.middleName == null ? null : other.middleName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.birthDate = other.birthDate == null ? null : other.birthDate.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.cellPhone = other.cellPhone == null ? null : other.cellPhone.copy();
        this.telephone = other.telephone == null ? null : other.telephone.copy();
        this.maritalStatus = other.maritalStatus == null ? null : other.maritalStatus.copy();
        this.employeeNumber = other.employeeNumber == null ? null : other.employeeNumber.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.dateJoining = other.dateJoining == null ? null : other.dateJoining.copy();
        this.salary = other.salary == null ? null : other.salary.copy();
        this.contractStartDate = other.contractStartDate == null ? null : other.contractStartDate.copy();
        this.contractEndDate = other.contractEndDate == null ? null : other.contractEndDate.copy();
        this.bankName = other.bankName == null ? null : other.bankName.copy();
        this.branchName = other.branchName == null ? null : other.branchName.copy();
        this.bankAccount = other.bankAccount == null ? null : other.bankAccount.copy();
        this.insuranceRegistrationNumber = other.insuranceRegistrationNumber == null ? null : other.insuranceRegistrationNumber.copy();
        this.attachmentsId = other.attachmentsId == null ? null : other.attachmentsId.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
        this.employeeTypeId = other.employeeTypeId == null ? null : other.employeeTypeId.copy();
        this.designationId = other.designationId == null ? null : other.designationId.copy();
        this.facilityId = other.facilityId == null ? null : other.facilityId.copy();
        this.projectId = other.projectId == null ? null : other.projectId.copy();
    }

    @Override
    public EmployeeRecordCriteria copy() {
        return new EmployeeRecordCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
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

    public StringFilter getTelephone() {
        return telephone;
    }

    public void setTelephone(StringFilter telephone) {
        this.telephone = telephone;
    }

    public StringFilter getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(StringFilter maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public StringFilter getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(StringFilter employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public LocalDateFilter getDateJoining() {
        return dateJoining;
    }

    public void setDateJoining(LocalDateFilter dateJoining) {
        this.dateJoining = dateJoining;
    }

    public DoubleFilter getSalary() {
        return salary;
    }

    public void setSalary(DoubleFilter salary) {
        this.salary = salary;
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

    public LongFilter getAttachmentsId() {
        return attachmentsId;
    }

    public void setAttachmentsId(LongFilter attachmentsId) {
        this.attachmentsId = attachmentsId;
    }

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
    }

    public LongFilter getEmployeeTypeId() {
        return employeeTypeId;
    }

    public void setEmployeeTypeId(LongFilter employeeTypeId) {
        this.employeeTypeId = employeeTypeId;
    }

    public LongFilter getDesignationId() {
        return designationId;
    }

    public void setDesignationId(LongFilter designationId) {
        this.designationId = designationId;
    }

    public LongFilter getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(LongFilter facilityId) {
        this.facilityId = facilityId;
    }

    public LongFilter getProjectId() {
        return projectId;
    }

    public void setProjectId(LongFilter projectId) {
        this.projectId = projectId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmployeeRecordCriteria that = (EmployeeRecordCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(middleName, that.middleName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(address, that.address) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(birthDate, that.birthDate) &&
            Objects.equals(email, that.email) &&
            Objects.equals(cellPhone, that.cellPhone) &&
            Objects.equals(telephone, that.telephone) &&
            Objects.equals(maritalStatus, that.maritalStatus) &&
            Objects.equals(employeeNumber, that.employeeNumber) &&
            Objects.equals(active, that.active) &&
            Objects.equals(dateJoining, that.dateJoining) &&
            Objects.equals(salary, that.salary) &&
            Objects.equals(contractStartDate, that.contractStartDate) &&
            Objects.equals(contractEndDate, that.contractEndDate) &&
            Objects.equals(bankName, that.bankName) &&
            Objects.equals(branchName, that.branchName) &&
            Objects.equals(bankAccount, that.bankAccount) &&
            Objects.equals(insuranceRegistrationNumber, that.insuranceRegistrationNumber) &&
            Objects.equals(attachmentsId, that.attachmentsId) &&
            Objects.equals(departmentId, that.departmentId) &&
            Objects.equals(employeeTypeId, that.employeeTypeId) &&
            Objects.equals(designationId, that.designationId) &&
            Objects.equals(facilityId, that.facilityId) &&
            Objects.equals(projectId, that.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        firstName,
        middleName,
        lastName,
        address,
        gender,
        birthDate,
        email,
        cellPhone,
        telephone,
        maritalStatus,
        employeeNumber,
        active,
        dateJoining,
        salary,
        contractStartDate,
        contractEndDate,
        bankName,
        branchName,
        bankAccount,
        insuranceRegistrationNumber,
        attachmentsId,
        departmentId,
        employeeTypeId,
        designationId,
        facilityId,
        projectId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeRecordCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (middleName != null ? "middleName=" + middleName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (gender != null ? "gender=" + gender + ", " : "") +
                (birthDate != null ? "birthDate=" + birthDate + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (cellPhone != null ? "cellPhone=" + cellPhone + ", " : "") +
                (telephone != null ? "telephone=" + telephone + ", " : "") +
                (maritalStatus != null ? "maritalStatus=" + maritalStatus + ", " : "") +
                (employeeNumber != null ? "employeeNumber=" + employeeNumber + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (dateJoining != null ? "dateJoining=" + dateJoining + ", " : "") +
                (salary != null ? "salary=" + salary + ", " : "") +
                (contractStartDate != null ? "contractStartDate=" + contractStartDate + ", " : "") +
                (contractEndDate != null ? "contractEndDate=" + contractEndDate + ", " : "") +
                (bankName != null ? "bankName=" + bankName + ", " : "") +
                (branchName != null ? "branchName=" + branchName + ", " : "") +
                (bankAccount != null ? "bankAccount=" + bankAccount + ", " : "") +
                (insuranceRegistrationNumber != null ? "insuranceRegistrationNumber=" + insuranceRegistrationNumber + ", " : "") +
                (attachmentsId != null ? "attachmentsId=" + attachmentsId + ", " : "") +
                (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
                (employeeTypeId != null ? "employeeTypeId=" + employeeTypeId + ", " : "") +
                (designationId != null ? "designationId=" + designationId + ", " : "") +
                (facilityId != null ? "facilityId=" + facilityId + ", " : "") +
                (projectId != null ? "projectId=" + projectId + ", " : "") +
            "}";
    }

}
