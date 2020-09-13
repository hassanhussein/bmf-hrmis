import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICarder, Carder } from 'app/shared/model/carder.model';
import { CarderService } from './carder.service';

@Component({
  selector: 'bmf-carder-update',
  templateUrl: './carder-update.component.html',
})
export class CarderUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [null, []],
    name: [null, [Validators.required]],
  });

  constructor(protected carderService: CarderService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ carder }) => {
      this.updateForm(carder);
    });
  }

  updateForm(carder: ICarder): void {
    this.editForm.patchValue({
      id: carder.id,
      code: carder.code,
      name: carder.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const carder = this.createFromForm();
    if (carder.id !== undefined) {
      this.subscribeToSaveResponse(this.carderService.update(carder));
    } else {
      this.subscribeToSaveResponse(this.carderService.create(carder));
    }
  }

  private createFromForm(): ICarder {
    return {
      ...new Carder(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICarder>>): void {
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
