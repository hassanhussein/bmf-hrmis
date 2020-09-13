export interface IDepartment {
  id?: number;
  code?: string;
  name?: string;
}

export class Department implements IDepartment {
  constructor(public id?: number, public code?: string, public name?: string) {}
}
