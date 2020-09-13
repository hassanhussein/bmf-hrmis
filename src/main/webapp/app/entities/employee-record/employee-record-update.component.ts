import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IEmployeeRecord, EmployeeRecord } from 'app/shared/model/employee-record.model';
import { EmployeeRecordService } from './employee-record.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';
import { IEmploymentCategory } from 'app/shared/model/employment-category.model';
import { EmploymentCategoryService } from 'app/entities/employment-category/employment-category.service';
import { ICarder } from 'app/shared/model/carder.model';
import { CarderService } from 'app/entities/carder/carder.service';
import { IFacility } from 'app/shared/model/facility.model';
import { FacilityService } from 'app/entities/facility/facility.service';
import { IProject } from 'app/shared/model/project.model';
import { ProjectService } from 'app/entities/project/project.service';

type SelectableEntity = IDepartment | IEmploymentCategory | ICarder | IFacility | IProject;

@Component({
  selector: 'bmf-employee-record-update',
  templateUrl: './employee-record-update.component.html',
})
export class EmployeeRecordUpdateComponent implements OnInit {
  isSaving = false;
  departments: IDepartment[] = [];
  employmentcategories: IEmploymentCategory[] = [];
  carders: ICarder[] = [];
  facilities: IFacility[] = [];
  projects: IProject[] = [];
  birthDateDp: any;
  dateJoiningDp: any;
  contractStartDateDp: any;
  contractEndDateDp: any;

   step = 0;



  editForm = this.fb.group({
    id: [],
    firstName: [null, [Validators.required]],
    middleName: [],
    lastName: [null, [Validators.required]],
    address: [],
    gender: [],
    birthDate: [null, [Validators.required]],
    email: [null, []],
    cellPhone: [],
    telephone: [],
    maritalStatus: [],
    picture: [],
    pictureContentType: [],
    employeeNumber: [null, [Validators.required]],
    active: [],
    dateJoining: [],
    salary: [],
    contractStartDate: [],
    contractEndDate: [],
    bankName: [],
    branchName: [],
    bankAccount: [],
    insuranceRegistrationNumber: [],
    departmentId: [null, Validators.required],
    employeeTypeId: [null, Validators.required],
    designationId: [null, Validators.required],
    facilityId: [null, Validators.required],
    projectId: [null, Validators.required],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected employeeRecordService: EmployeeRecordService,
    protected departmentService: DepartmentService,
    protected employmentCategoryService: EmploymentCategoryService,
    protected carderService: CarderService,
    protected facilityService: FacilityService,
    protected projectService: ProjectService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employeeRecord }) => {
      this.updateForm(employeeRecord);

      this.departmentService.query().subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));

      this.employmentCategoryService
        .query()
        .subscribe((res: HttpResponse<IEmploymentCategory[]>) => (this.employmentcategories = res.body || []));

      this.carderService.query().subscribe((res: HttpResponse<ICarder[]>) => (this.carders = res.body || []));

      this.facilityService.query().subscribe((res: HttpResponse<IFacility[]>) => (this.facilities = res.body || []));

      this.projectService.query().subscribe((res: HttpResponse<IProject[]>) => (this.projects = res.body || []));
    });
  }

  updateForm(employeeRecord: IEmployeeRecord): void {
    this.editForm.patchValue({
      id: employeeRecord.id,
      firstName: employeeRecord.firstName,
      middleName: employeeRecord.middleName,
      lastName: employeeRecord.lastName,
      address: employeeRecord.address,
      gender: employeeRecord.gender,
      birthDate: employeeRecord.birthDate,
      email: employeeRecord.email,
      cellPhone: employeeRecord.cellPhone,
      telephone: employeeRecord.telephone,
      maritalStatus: employeeRecord.maritalStatus,
      picture: employeeRecord.picture,
      pictureContentType: employeeRecord.pictureContentType,
      employeeNumber: employeeRecord.employeeNumber,
      active: employeeRecord.active,
      dateJoining: employeeRecord.dateJoining,
      salary: employeeRecord.salary,
      contractStartDate: employeeRecord.contractStartDate,
      contractEndDate: employeeRecord.contractEndDate,
      bankName: employeeRecord.bankName,
      branchName: employeeRecord.branchName,
      bankAccount: employeeRecord.bankAccount,
      insuranceRegistrationNumber: employeeRecord.insuranceRegistrationNumber,
      departmentId: employeeRecord.departmentId,
      employeeTypeId: employeeRecord.employeeTypeId,
      designationId: employeeRecord.designationId,
      facilityId: employeeRecord.facilityId,
      projectId: employeeRecord.projectId,
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
    const employeeRecord = this.createFromForm();
    if (employeeRecord.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeRecordService.update(employeeRecord));
    } else {
      this.subscribeToSaveResponse(this.employeeRecordService.create(employeeRecord));
    }
  }

  private createFromForm(): IEmployeeRecord {
    return {
      ...new EmployeeRecord(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      middleName: this.editForm.get(['middleName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      address: this.editForm.get(['address'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      birthDate: this.editForm.get(['birthDate'])!.value,
      email: this.editForm.get(['email'])!.value,
      cellPhone: this.editForm.get(['cellPhone'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      maritalStatus: this.editForm.get(['maritalStatus'])!.value,
      pictureContentType: this.editForm.get(['pictureContentType'])!.value,
      picture: this.editForm.get(['picture'])!.value,
      employeeNumber: this.editForm.get(['employeeNumber'])!.value,
      active: this.editForm.get(['active'])!.value,
      dateJoining: this.editForm.get(['dateJoining'])!.value,
      salary: this.editForm.get(['salary'])!.value,
      contractStartDate: this.editForm.get(['contractStartDate'])!.value,
      contractEndDate: this.editForm.get(['contractEndDate'])!.value,
      bankName: this.editForm.get(['bankName'])!.value,
      branchName: this.editForm.get(['branchName'])!.value,
      bankAccount: this.editForm.get(['bankAccount'])!.value,
      insuranceRegistrationNumber: this.editForm.get(['insuranceRegistrationNumber'])!.value,
      departmentId: this.editForm.get(['departmentId'])!.value,
      employeeTypeId: this.editForm.get(['employeeTypeId'])!.value,
      designationId: this.editForm.get(['designationId'])!.value,
      facilityId: this.editForm.get(['facilityId'])!.value,
      projectId: this.editForm.get(['projectId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployeeRecord>>): void {
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

  trackById(index: number, item: SelectableEntity): any {

    return item.id;
  }

  setStep(index: number):void {
      this.step = index;
  }

  nextStep() : void {
     this.step++;
  }

  prevStep() : void {
     this.step--;
  }
}
