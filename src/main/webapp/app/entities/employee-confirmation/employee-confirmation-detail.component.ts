import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmployeeConfirmation } from 'app/shared/model/employee-confirmation.model';

@Component({
  selector: 'bmf-employee-confirmation-detail',
  templateUrl: './employee-confirmation-detail.component.html',
})
export class EmployeeConfirmationDetailComponent implements OnInit {
  employeeConfirmation: IEmployeeConfirmation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employeeConfirmation }) => (this.employeeConfirmation = employeeConfirmation));
  }

  previousState(): void {
    window.history.back();
  }
}
