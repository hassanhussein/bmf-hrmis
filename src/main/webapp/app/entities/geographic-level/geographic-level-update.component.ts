import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGeographicLevel, GeographicLevel } from 'app/shared/model/geographic-level.model';
import { GeographicLevelService } from './geographic-level.service';

@Component({
  selector: 'bmf-geographic-level-update',
  templateUrl: './geographic-level-update.component.html',
})
export class GeographicLevelUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [null, []],
    name: [null, [Validators.required]],
    levelNumber: [null, []],
    active: [],
  });

  constructor(
    protected geographicLevelService: GeographicLevelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ geographicLevel }) => {
      this.updateForm(geographicLevel);
    });
  }

  updateForm(geographicLevel: IGeographicLevel): void {
    this.editForm.patchValue({
      id: geographicLevel.id,
      code: geographicLevel.code,
      name: geographicLevel.name,
      levelNumber: geographicLevel.levelNumber,
      active: geographicLevel.active,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const geographicLevel = this.createFromForm();
    if (geographicLevel.id !== undefined) {
      this.subscribeToSaveResponse(this.geographicLevelService.update(geographicLevel));
    } else {
      this.subscribeToSaveResponse(this.geographicLevelService.create(geographicLevel));
    }
  }

  private createFromForm(): IGeographicLevel {
    return {
      ...new GeographicLevel(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      name: this.editForm.get(['name'])!.value,
      levelNumber: this.editForm.get(['levelNumber'])!.value,
      active: this.editForm.get(['active'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGeographicLevel>>): void {
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
