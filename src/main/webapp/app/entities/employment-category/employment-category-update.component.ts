import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEmploymentCategory, EmploymentCategory } from 'app/shared/model/employment-category.model';
import { EmploymentCategoryService } from './employment-category.service';

@Component({
  selector: 'bmf-employment-category-update',
  templateUrl: './employment-category-update.component.html',
})
export class EmploymentCategoryUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [null, []],
    name: [null, [Validators.required]],
  });

  constructor(
    protected employmentCategoryService: EmploymentCategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employmentCategory }) => {
      this.updateForm(employmentCategory);
    });
  }

  updateForm(employmentCategory: IEmploymentCategory): void {
    this.editForm.patchValue({
      id: employmentCategory.id,
      code: employmentCategory.code,
      name: employmentCategory.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employmentCategory = this.createFromForm();
    if (employmentCategory.id !== undefined) {
      this.subscribeToSaveResponse(this.employmentCategoryService.update(employmentCategory));
    } else {
      this.subscribeToSaveResponse(this.employmentCategoryService.create(employmentCategory));
    }
  }

  private createFromForm(): IEmploymentCategory {
    return {
      ...new EmploymentCategory(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmploymentCategory>>): void {
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
