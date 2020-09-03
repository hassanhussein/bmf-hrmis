import { Moment } from 'moment';

export interface IProject {
  id?: number;
  code?: string;
  name?: string;
  startDate?: Moment;
  endDate?: Moment;
}

export class Project implements IProject {
  constructor(public id?: number, public code?: string, public name?: string, public startDate?: Moment, public endDate?: Moment) {}
}
