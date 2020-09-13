export interface IFacilityType {
  id?: number;
  code?: string;
  name?: string;
  description?: string;
  displayOrder?: number;
  active?: boolean;
}

export class FacilityType implements IFacilityType {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public description?: string,
    public displayOrder?: number,
    public active?: boolean
  ) {
    this.active = this.active || false;
  }
}
