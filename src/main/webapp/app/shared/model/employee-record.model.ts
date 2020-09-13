import { Moment } from 'moment';
import { IAttachment } from 'app/shared/model/attachment.model';

export interface IEmployeeRecord {
  id?: number;
  firstName?: string;
  middleName?: string;
  lastName?: string;
  address?: string;
  gender?: string;
  birthDate?: Moment;
  email?: string;
  cellPhone?: string;
  telephone?: string;
  maritalStatus?: string;
  pictureContentType?: string;
  picture?: any;
  employeeNumber?: string;
  active?: boolean;
  dateJoining?: Moment;
  salary?: number;
  contractStartDate?: Moment;
  contractEndDate?: Moment;
  bankName?: string;
  branchName?: string;
  bankAccount?: string;
  insuranceRegistrationNumber?: string;
  attachments?: IAttachment[];
  departmentName?: string;
  departmentId?: number;
  employeeTypeName?: string;
  employeeTypeId?: number;
  designationName?: string;
  designationId?: number;
  facilityName?: string;
  facilityId?: number;
  projectName?: string;
  projectId?: number;
}

export class EmployeeRecord implements IEmployeeRecord {
  constructor(
    public id?: number,
    public firstName?: string,
    public middleName?: string,
    public lastName?: string,
    public address?: string,
    public gender?: string,
    public birthDate?: Moment,
    public email?: string,
    public cellPhone?: string,
    public telephone?: string,
    public maritalStatus?: string,
    public pictureContentType?: string,
    public picture?: any,
    public employeeNumber?: string,
    public active?: boolean,
    public dateJoining?: Moment,
    public salary?: number,
    public contractStartDate?: Moment,
    public contractEndDate?: Moment,
    public bankName?: string,
    public branchName?: string,
    public bankAccount?: string,
    public insuranceRegistrationNumber?: string,
    public attachments?: IAttachment[],
    public departmentName?: string,
    public departmentId?: number,
    public employeeTypeName?: string,
    public employeeTypeId?: number,
    public designationName?: string,
    public designationId?: number,
    public facilityName?: string,
    public facilityId?: number,
    public projectName?: string,
    public projectId?: number
  ) {
    this.active = this.active || false;
  }
}
