import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEmployee, Employee } from 'app/shared/model/employee.model';
import { EmployeeService } from './employee.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';

@Component({
  selector: 'bmf-employee-update',
  templateUrl: './employee-update.component.html',
})
export class EmployeeUpdateComponent implements OnInit {
  isSaving = false;
  departments: IDepartment[] = [];
  contractStartDateDp: any;
  contractEndDateDp: any;
  dateJoiningDp: any;
     step = 0;

  editForm = this.fb.group({
    id: [],
    employeeNumber: [null, [Validators.required]],
    contractStartDate: [],
    contractEndDate: [],
    bankName: [],
    branchName: [],
    bankAccount: [],
    insuranceRegistrationNumber: [],
    dateJoining: [],
    designation: [],
    districtId: [null, [Validators.required]],
    facilityId: [],
    categoryId: [null, [Validators.required]],
    trainingId: [],
    carderId: [],
    departmentName: [],
    confirmationId: [],
    isConfirmed: [],
    confirmationLetterName: [],
    projectName: [],
    active: [],
    departmentIdId: [],
  });

  constructor(
    protected employeeService: EmployeeService,
    protected departmentService: DepartmentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employee }) => {
      this.updateForm(employee);

      this.departmentService.query().subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));
    });
  }

  updateForm(employee: IEmployee): void {
    this.editForm.patchValue({
      id: employee.id,
      employeeNumber: employee.employeeNumber,
      contractStartDate: employee.contractStartDate,
      contractEndDate: employee.contractEndDate,
      bankName: employee.bankName,
      branchName: employee.branchName,
      bankAccount: employee.bankAccount,
      insuranceRegistrationNumber: employee.insuranceRegistrationNumber,
      dateJoining: employee.dateJoining,
      designation: employee.designation,
      districtId: employee.districtId,
      facilityId: employee.facilityId,
      categoryId: employee.categoryId,
      trainingId: employee.trainingId,
      carderId: employee.carderId,
      departmentName: employee.departmentName,
      confirmationId: employee.confirmationId,
      isConfirmed: employee.isConfirmed,
      confirmationLetterName: employee.confirmationLetterName,
      projectName: employee.projectName,
      active: employee.active,
      departmentIdId: employee.departmentIdId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employee = this.createFromForm();
    if (employee.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeService.update(employee));
    } else {
      this.subscribeToSaveResponse(this.employeeService.create(employee));
    }
  }

  private createFromForm(): IEmployee {
    return {
      ...new Employee(),
      id: this.editForm.get(['id'])!.value,
      employeeNumber: this.editForm.get(['employeeNumber'])!.value,
      contractStartDate: this.editForm.get(['contractStartDate'])!.value,
      contractEndDate: this.editForm.get(['contractEndDate'])!.value,
      bankName: this.editForm.get(['bankName'])!.value,
      branchName: this.editForm.get(['branchName'])!.value,
      bankAccount: this.editForm.get(['bankAccount'])!.value,
      insuranceRegistrationNumber: this.editForm.get(['insuranceRegistrationNumber'])!.value,
      dateJoining: this.editForm.get(['dateJoining'])!.value,
      designation: this.editForm.get(['designation'])!.value,
      districtId: this.editForm.get(['districtId'])!.value,
      facilityId: this.editForm.get(['facilityId'])!.value,
      categoryId: this.editForm.get(['categoryId'])!.value,
      trainingId: this.editForm.get(['trainingId'])!.value,
      carderId: this.editForm.get(['carderId'])!.value,
      departmentName: this.editForm.get(['departmentName'])!.value,
      confirmationId: this.editForm.get(['confirmationId'])!.value,
      isConfirmed: this.editForm.get(['isConfirmed'])!.value,
      confirmationLetterName: this.editForm.get(['confirmationLetterName'])!.value,
      projectName: this.editForm.get(['projectName'])!.value,
      active: this.editForm.get(['active'])!.value,
      departmentIdId: this.editForm.get(['departmentIdId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployee>>): void {
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

  trackById(index: number, item: IDepartment): any {
    return item.id;
  }


     setStep(index: number):void {
        this.step = index;
      }

      nextStep() : void{
        this.step++;
      }

      prevStep() : void {
        this.step--;
      }
}
