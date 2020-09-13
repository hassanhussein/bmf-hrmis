export interface IGeographicZone {
  id?: number;
  code?: string;
  name?: string;
  latitude?: number;
  longitude?: number;
  parentName?: string;
  parentId?: number;
  levelName?: string;
  levelId?: number;
}

export class GeographicZone implements IGeographicZone {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public latitude?: number,
    public longitude?: number,
    public parentName?: string,
    public parentId?: number,
    public levelName?: string,
    public levelId?: number
  ) {}
}
