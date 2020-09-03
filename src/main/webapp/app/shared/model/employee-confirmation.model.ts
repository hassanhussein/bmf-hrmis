import { Moment } from 'moment';

export interface IEmployeeConfirmation {
  id?: number;
  isConfirmed?: boolean;
  confirmationLetter?: string;
  date?: Moment;
}

export class EmployeeConfirmation implements IEmployeeConfirmation {
  constructor(public id?: number, public isConfirmed?: boolean, public confirmationLetter?: string, public date?: Moment) {
    this.isConfirmed = this.isConfirmed || false;
  }
}
