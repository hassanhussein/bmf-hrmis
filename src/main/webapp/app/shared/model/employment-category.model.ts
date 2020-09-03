export interface IEmploymentCategory {
  id?: number;
  code?: string;
  name?: string;
}

export class EmploymentCategory implements IEmploymentCategory {
  constructor(public id?: number, public code?: string, public name?: string) {}
}
