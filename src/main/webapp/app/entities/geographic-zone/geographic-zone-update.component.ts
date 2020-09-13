import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGeographicZone, GeographicZone } from 'app/shared/model/geographic-zone.model';
import { GeographicZoneService } from './geographic-zone.service';
import { IGeographicLevel } from 'app/shared/model/geographic-level.model';
import { GeographicLevelService } from 'app/entities/geographic-level/geographic-level.service';

type SelectableEntity = IGeographicZone | IGeographicLevel;

@Component({
  selector: 'bmf-geographic-zone-update',
  templateUrl: './geographic-zone-update.component.html',
})
export class GeographicZoneUpdateComponent implements OnInit {
  isSaving = false;
  geographiczones: IGeographicZone[] = [];
  geographiclevels: IGeographicLevel[] = [];

  editForm = this.fb.group({
    id: [],
    code: [null, []],
    name: [null, [Validators.required]],
    latitude: [],
    longitude: [],
    parentId: [],
    levelId: [null, Validators.required],
  });

  constructor(
    protected geographicZoneService: GeographicZoneService,
    protected geographicLevelService: GeographicLevelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ geographicZone }) => {
      this.updateForm(geographicZone);

      this.geographicZoneService.query().subscribe((res: HttpResponse<IGeographicZone[]>) => (this.geographiczones = res.body || []));

      this.geographicLevelService.query().subscribe((res: HttpResponse<IGeographicLevel[]>) => (this.geographiclevels = res.body || []));
    });
  }

  updateForm(geographicZone: IGeographicZone): void {
    this.editForm.patchValue({
      id: geographicZone.id,
      code: geographicZone.code,
      name: geographicZone.name,
      latitude: geographicZone.latitude,
      longitude: geographicZone.longitude,
      parentId: geographicZone.parentId,
      levelId: geographicZone.levelId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const geographicZone = this.createFromForm();
    if (geographicZone.id !== undefined) {
      this.subscribeToSaveResponse(this.geographicZoneService.update(geographicZone));
    } else {
      this.subscribeToSaveResponse(this.geographicZoneService.create(geographicZone));
    }
  }

  private createFromForm(): IGeographicZone {
    return {
      ...new GeographicZone(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      name: this.editForm.get(['name'])!.value,
      latitude: this.editForm.get(['latitude'])!.value,
      longitude: this.editForm.get(['longitude'])!.value,
      parentId: this.editForm.get(['parentId'])!.value,
      levelId: this.editForm.get(['levelId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGeographicZone>>): void {
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
