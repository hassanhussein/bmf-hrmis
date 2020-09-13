export interface IGeographicLevel {
  id?: number;
  code?: string;
  name?: string;
  levelNumber?: number;
  active?: boolean;
}

export class GeographicLevel implements IGeographicLevel {
  constructor(public id?: number, public code?: string, public name?: string, public levelNumber?: number, public active?: boolean) {
    this.active = this.active || false;
  }
}
