import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IEmployee, Employee } from 'app/shared/model/employee.model';
import { EmployeeService } from './employee.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';

@Component({
  selector: 'bmf-employee-update',
  templateUrl: './employee-update.component.html',
})
export class EmployeeUpdateComponent implements OnInit {
  isSaving = false;
  departments: IDepartment[] = [];
  birthDateDp: any;
  contractStartDateDp: any;
  contractEndDateDp: any;

  editForm = this.fb.group({
    id: [],
    employeeNumber: [null, [Validators.required]],
    firstName: [],
    middleName: [],
    lastName: [],
    gender: [],
    birthDate: [],
    email: [null, []],
    cellPhone: [],
    maritalStatus: [],
    active: [],
    contractStartDate: [],
    contractEndDate: [],
    bankName: [],
    branchName: [],
    bankAccount: [],
    insuranceRegistrationNumber: [],
    districtId: [],
    facilityId: [],
    categoryId: [],
    trainingId: [],
    carderId: [],
    picture: [],
    pictureContentType: [],
    departMentCode: [],
    attachmentId: [],
    confirmationId: [],
    projectId: [],
    departmentIdId: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected employeeService: EmployeeService,
    protected departmentService: DepartmentService,
    protected elementRef: ElementRef,
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
      firstName: employee.firstName,
      middleName: employee.middleName,
      lastName: employee.lastName,
      gender: employee.gender,
      birthDate: employee.birthDate,
      email: employee.email,
      cellPhone: employee.cellPhone,
      maritalStatus: employee.maritalStatus,
      active: employee.active,
      contractStartDate: employee.contractStartDate,
      contractEndDate: employee.contractEndDate,
      bankName: employee.bankName,
      branchName: employee.branchName,
      bankAccount: employee.bankAccount,
      insuranceRegistrationNumber: employee.insuranceRegistrationNumber,
      districtId: employee.districtId,
      facilityId: employee.facilityId,
      categoryId: employee.categoryId,
      trainingId: employee.trainingId,
      carderId: employee.carderId,
      picture: employee.picture,
      pictureContentType: employee.pictureContentType,
      departMentCode: employee.departMentCode,
      attachmentId: employee.attachmentId,
      confirmationId: employee.confirmationId,
      projectId: employee.projectId,
      departmentIdId: employee.departmentIdId,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('hrmisApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
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
      firstName: this.editForm.get(['firstName'])!.value,
      middleName: this.editForm.get(['middleName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      birthDate: this.editForm.get(['birthDate'])!.value,
      email: this.editForm.get(['email'])!.value,
      cellPhone: this.editForm.get(['cellPhone'])!.value,
      maritalStatus: this.editForm.get(['maritalStatus'])!.value,
      active: this.editForm.get(['active'])!.value,
      contractStartDate: this.editForm.get(['contractStartDate'])!.value,
      contractEndDate: this.editForm.get(['contractEndDate'])!.value,
      bankName: this.editForm.get(['bankName'])!.value,
      branchName: this.editForm.get(['branchName'])!.value,
      bankAccount: this.editForm.get(['bankAccount'])!.value,
      insuranceRegistrationNumber: this.editForm.get(['insuranceRegistrationNumber'])!.value,
      districtId: this.editForm.get(['districtId'])!.value,
      facilityId: this.editForm.get(['facilityId'])!.value,
      categoryId: this.editForm.get(['categoryId'])!.value,
      trainingId: this.editForm.get(['trainingId'])!.value,
      carderId: this.editForm.get(['carderId'])!.value,
      pictureContentType: this.editForm.get(['pictureContentType'])!.value,
      picture: this.editForm.get(['picture'])!.value,
      departMentCode: this.editForm.get(['departMentCode'])!.value,
      attachmentId: this.editForm.get(['attachmentId'])!.value,
      confirmationId: this.editForm.get(['confirmationId'])!.value,
      projectId: this.editForm.get(['projectId'])!.value,
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
}
