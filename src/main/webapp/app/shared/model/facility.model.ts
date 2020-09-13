import { Moment } from 'moment';

export interface IFacility {
  id?: number;
  active?: boolean;
  code?: string;
  name?: string;
  postalAddress?: string;
  ward?: string;
  village?: string;
  comment?: string;
  description?: string;
  startDate?: Moment;
  operatedby?: string;
  districtName?: string;
  districtId?: number;
  typeName?: string;
  typeId?: number;
}

export class Facility implements IFacility {
  constructor(
    public id?: number,
    public active?: boolean,
    public code?: string,
    public name?: string,
    public postalAddress?: string,
    public ward?: string,
    public village?: string,
    public comment?: string,
    public description?: string,
    public startDate?: Moment,
    public operatedby?: string,
    public districtName?: string,
    public districtId?: number,
    public typeName?: string,
    public typeId?: number
  ) {
    this.active = this.active || false;
  }
}
