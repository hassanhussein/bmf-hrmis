export interface ICarder {
  id?: number;
  code?: string;
  name?: string;
}

export class Carder implements ICarder {
  constructor(public id?: number, public code?: string, public name?: string) {}
}
