import { Moment } from 'moment';

export interface IEmployee {
  id?: number;
  employeeNumber?: string;
  firstName?: string;
  middleName?: string;
  lastName?: string;
  gender?: string;
  birthDate?: Moment;
  email?: string;
  cellPhone?: string;
  maritalStatus?: string;
  active?: boolean;
  contractStartDate?: Moment;
  contractEndDate?: Moment;
  bankName?: string;
  branchName?: string;
  bankAccount?: string;
  insuranceRegistrationNumber?: string;
  districtId?: number;
  facilityId?: number;
  categoryId?: number;
  trainingId?: number;
  carderId?: number;
  pictureContentType?: string;
  picture?: any;
  departMentCode?: number;
  attachmentId?: number;
  confirmationId?: number;
  projectId?: number;
  departmentIdId?: number;
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public employeeNumber?: string,
    public firstName?: string,
    public middleName?: string,
    public lastName?: string,
    public gender?: string,
    public birthDate?: Moment,
    public email?: string,
    public cellPhone?: string,
    public maritalStatus?: string,
    public active?: boolean,
    public contractStartDate?: Moment,
    public contractEndDate?: Moment,
    public bankName?: string,
    public branchName?: string,
    public bankAccount?: string,
    public insuranceRegistrationNumber?: string,
    public districtId?: number,
    public facilityId?: number,
    public categoryId?: number,
    public trainingId?: number,
    public carderId?: number,
    public pictureContentType?: string,
    public picture?: any,
    public departMentCode?: number,
    public attachmentId?: number,
    public confirmationId?: number,
    public projectId?: number,
    public departmentIdId?: number
  ) {
    this.active = this.active || false;
  }
}
