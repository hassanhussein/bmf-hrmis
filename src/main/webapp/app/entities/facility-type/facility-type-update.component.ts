import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFacilityType, FacilityType } from 'app/shared/model/facility-type.model';
import { FacilityTypeService } from './facility-type.service';

@Component({
  selector: 'bmf-facility-type-update',
  templateUrl: './facility-type-update.component.html',
})
export class FacilityTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [null, []],
    name: [null, [Validators.required]],
    description: [],
    displayOrder: [],
    active: [],
  });

  constructor(protected facilityTypeService: FacilityTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ facilityType }) => {
      this.updateForm(facilityType);
    });
  }

  updateForm(facilityType: IFacilityType): void {
    this.editForm.patchValue({
      id: facilityType.id,
      code: facilityType.code,
      name: facilityType.name,
      description: facilityType.description,
      displayOrder: facilityType.displayOrder,
      active: facilityType.active,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const facilityType = this.createFromForm();
    if (facilityType.id !== undefined) {
      this.subscribeToSaveResponse(this.facilityTypeService.update(facilityType));
    } else {
      this.subscribeToSaveResponse(this.facilityTypeService.create(facilityType));
    }
  }

  private createFromForm(): IFacilityType {
    return {
      ...new FacilityType(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      displayOrder: this.editForm.get(['displayOrder'])!.value,
      active: this.editForm.get(['active'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFacilityType>>): void {
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
