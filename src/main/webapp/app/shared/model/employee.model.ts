import { Moment } from 'moment';
import { IAttachment } from 'app/shared/model/attachment.model';

export interface IEmployee {
  id?: number;
  employeeNumber?: string;
  contractStartDate?: Moment;
  contractEndDate?: Moment;
  bankName?: string;
  branchName?: string;
  bankAccount?: string;
  insuranceRegistrationNumber?: string;
  dateJoining?: Moment;
  designation?: string;
  districtId?: number;
  facilityId?: number;
  categoryId?: number;
  trainingId?: number;
  carderId?: number;
  departmentName?: string;
  confirmationId?: number;
  isConfirmed?: boolean;
  confirmationLetterName?: string;
  projectName?: string;
  active?: boolean;
  attachments?: IAttachment[];
  departmentIdId?: number;
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public employeeNumber?: string,
    public contractStartDate?: Moment,
    public contractEndDate?: Moment,
    public bankName?: string,
    public branchName?: string,
    public bankAccount?: string,
    public insuranceRegistrationNumber?: string,
    public dateJoining?: Moment,
    public designation?: string,
    public districtId?: number,
    public facilityId?: number,
    public categoryId?: number,
    public trainingId?: number,
    public carderId?: number,
    public departmentName?: string,
    public confirmationId?: number,
    public isConfirmed?: boolean,
    public confirmationLetterName?: string,
    public projectName?: string,
    public active?: boolean,
    public attachments?: IAttachment[],
    public departmentIdId?: number
  ) {
    this.isConfirmed = this.isConfirmed || false;
    this.active = this.active || false;
  }
}
