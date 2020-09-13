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
 * A EmployeeRecord.
 */
@Entity
@Table(name = "employee_records")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmployeeRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "gender")
    private String gender;

    @NotNull
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "cell_phone")
    private String cellPhone;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "marital_status")
    private String maritalStatus;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "picture_content_type")
    private String pictureContentType;

    @NotNull
    @Column(name = "employee_number", nullable = false)
    private String employeeNumber;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "date_joining")
    private LocalDate dateJoining;

    @Column(name = "salary")
    private Double salary;

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

    @OneToMany(mappedBy = "employee",cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Attachment> attachments = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "employeeRecords", allowSetters = true)
    private Department department;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "employeeRecords", allowSetters = true)
    private EmploymentCategory employeeType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "employeeRecords", allowSetters = true)
    private Carder designation;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "employeeRecords", allowSetters = true)
    private Facility facility;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "employeeRecords", allowSetters = true)
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public EmployeeRecord firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public EmployeeRecord middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public EmployeeRecord lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public EmployeeRecord address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public EmployeeRecord gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public EmployeeRecord birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public EmployeeRecord email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public EmployeeRecord cellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
        return this;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getTelephone() {
        return telephone;
    }

    public EmployeeRecord telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public EmployeeRecord maritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
        return this;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public byte[] getPicture() {
        return picture;
    }

    public EmployeeRecord picture(byte[] picture) {
        this.picture = picture;
        return this;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return pictureContentType;
    }

    public EmployeeRecord pictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
        return this;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public EmployeeRecord employeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
        return this;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public Boolean isActive() {
        return active;
    }

    public EmployeeRecord active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getDateJoining() {
        return dateJoining;
    }

    public EmployeeRecord dateJoining(LocalDate dateJoining) {
        this.dateJoining = dateJoining;
        return this;
    }

    public void setDateJoining(LocalDate dateJoining) {
        this.dateJoining = dateJoining;
    }

    public Double getSalary() {
        return salary;
    }

    public EmployeeRecord salary(Double salary) {
        this.salary = salary;
        return this;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public LocalDate getContractStartDate() {
        return contractStartDate;
    }

    public EmployeeRecord contractStartDate(LocalDate contractStartDate) {
        this.contractStartDate = contractStartDate;
        return this;
    }

    public void setContractStartDate(LocalDate contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public LocalDate getContractEndDate() {
        return contractEndDate;
    }

    public EmployeeRecord contractEndDate(LocalDate contractEndDate) {
        this.contractEndDate = contractEndDate;
        return this;
    }

    public void setContractEndDate(LocalDate contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public String getBankName() {
        return bankName;
    }

    public EmployeeRecord bankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public EmployeeRecord branchName(String branchName) {
        this.branchName = branchName;
        return this;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public EmployeeRecord bankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
        return this;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getInsuranceRegistrationNumber() {
        return insuranceRegistrationNumber;
    }

    public EmployeeRecord insuranceRegistrationNumber(String insuranceRegistrationNumber) {
        this.insuranceRegistrationNumber = insuranceRegistrationNumber;
        return this;
    }

    public void setInsuranceRegistrationNumber(String insuranceRegistrationNumber) {
        this.insuranceRegistrationNumber = insuranceRegistrationNumber;
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public EmployeeRecord attachments(Set<Attachment> attachments) {
        this.attachments = attachments;
        return this;
    }

    public EmployeeRecord addAttachments(Attachment attachment) {
        this.attachments.add(attachment);
        attachment.setEmployee(this);
        return this;
    }

    public EmployeeRecord removeAttachments(Attachment attachment) {
        this.attachments.remove(attachment);
        attachment.setEmployee(null);
        return this;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Department getDepartment() {
        return department;
    }

    public EmployeeRecord department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public EmploymentCategory getEmployeeType() {
        return employeeType;
    }

    public EmployeeRecord employeeType(EmploymentCategory employmentCategory) {
        this.employeeType = employmentCategory;
        return this;
    }

    public void setEmployeeType(EmploymentCategory employmentCategory) {
        this.employeeType = employmentCategory;
    }

    public Carder getDesignation() {
        return designation;
    }

    public EmployeeRecord designation(Carder carder) {
        this.designation = carder;
        return this;
    }

    public void setDesignation(Carder carder) {
        this.designation = carder;
    }

    public Facility getFacility() {
        return facility;
    }

    public EmployeeRecord facility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Project getProject() {
        return project;
    }

    public EmployeeRecord project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeRecord)) {
            return false;
        }
        return id != null && id.equals(((EmployeeRecord) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeRecord{" +
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
            ", pictureContentType='" + getPictureContentType() + "'" +
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
            "}";
    }
}
