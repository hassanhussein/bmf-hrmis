import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFacility, Facility } from 'app/shared/model/facility.model';
import { FacilityService } from './facility.service';
import { IGeographicZone } from 'app/shared/model/geographic-zone.model';
import { GeographicZoneService } from 'app/entities/geographic-zone/geographic-zone.service';
import { IFacilityType } from 'app/shared/model/facility-type.model';
import { FacilityTypeService } from 'app/entities/facility-type/facility-type.service';

type SelectableEntity = IGeographicZone | IFacilityType;

@Component({
  selector: 'bmf-facility-update',
  templateUrl: './facility-update.component.html',
})
export class FacilityUpdateComponent implements OnInit {
  isSaving = false;
  geographiczones: IGeographicZone[] = [];
  facilitytypes: IFacilityType[] = [];
  startDateDp: any;

  editForm = this.fb.group({
    id: [],
    active: [null, [Validators.required]],
    code: [null, []],
    name: [null, [Validators.required]],
    postalAddress: [],
    ward: [],
    village: [],
    comment: [],
    description: [],
    startDate: [],
    operatedby: [],
    districtId: [],
    typeId: [],
  });

  constructor(
    protected facilityService: FacilityService,
    protected geographicZoneService: GeographicZoneService,
    protected facilityTypeService: FacilityTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ facility }) => {
      this.updateForm(facility);

      this.geographicZoneService.query().subscribe((res: HttpResponse<IGeographicZone[]>) => (this.geographiczones = res.body || []));

      this.facilityTypeService.query().subscribe((res: HttpResponse<IFacilityType[]>) => (this.facilitytypes = res.body || []));
    });
  }

  updateForm(facility: IFacility): void {
    this.editForm.patchValue({
      id: facility.id,
      active: facility.active,
      code: facility.code,
      name: facility.name,
      postalAddress: facility.postalAddress,
      ward: facility.ward,
      village: facility.village,
      comment: facility.comment,
      description: facility.description,
      startDate: facility.startDate,
      operatedby: facility.operatedby,
      districtId: facility.districtId,
      typeId: facility.typeId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const facility = this.createFromForm();
    if (facility.id !== undefined) {
      this.subscribeToSaveResponse(this.facilityService.update(facility));
    } else {
      this.subscribeToSaveResponse(this.facilityService.create(facility));
    }
  }

  private createFromForm(): IFacility {
    return {
      ...new Facility(),
      id: this.editForm.get(['id'])!.value,
      active: this.editForm.get(['active'])!.value,
      code: this.editForm.get(['code'])!.value,
      name: this.editForm.get(['name'])!.value,
      postalAddress: this.editForm.get(['postalAddress'])!.value,
      ward: this.editForm.get(['ward'])!.value,
      village: this.editForm.get(['village'])!.value,
      comment: this.editForm.get(['comment'])!.value,
      description: this.editForm.get(['description'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      operatedby: this.editForm.get(['operatedby'])!.value,
      districtId: this.editForm.get(['districtId'])!.value,
      typeId: this.editForm.get(['typeId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFacility>>): void {
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
}
