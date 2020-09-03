import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEmployeeConfirmation, EmployeeConfirmation } from 'app/shared/model/employee-confirmation.model';
import { EmployeeConfirmationService } from './employee-confirmation.service';

@Component({
  selector: 'bmf-employee-confirmation-update',
  templateUrl: './employee-confirmation-update.component.html',
})
export class EmployeeConfirmationUpdateComponent implements OnInit {
  isSaving = false;
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    isConfirmed: [],
    confirmationLetter: [],
    date: [],
  });

  constructor(
    protected employeeConfirmationService: EmployeeConfirmationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employeeConfirmation }) => {
      this.updateForm(employeeConfirmation);
    });
  }

  updateForm(employeeConfirmation: IEmployeeConfirmation): void {
    this.editForm.patchValue({
      id: employeeConfirmation.id,
      isConfirmed: employeeConfirmation.isConfirmed,
      confirmationLetter: employeeConfirmation.confirmationLetter,
      date: employeeConfirmation.date,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employeeConfirmation = this.createFromForm();
    if (employeeConfirmation.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeConfirmationService.update(employeeConfirmation));
    } else {
      this.subscribeToSaveResponse(this.employeeConfirmationService.create(employeeConfirmation));
    }
  }

  private createFromForm(): IEmployeeConfirmation {
    return {
      ...new EmployeeConfirmation(),
      id: this.editForm.get(['id'])!.value,
      isConfirmed: this.editForm.get(['isConfirmed'])!.value,
      confirmationLetter: this.editForm.get(['confirmationLetter'])!.value,
      date: this.editForm.get(['date'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployeeConfirmation>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
