import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmploymentCategory } from 'app/shared/model/employment-category.model';
import { EmploymentCategoryService } from './employment-category.service';

@Component({
  templateUrl: './employment-category-delete-dialog.component.html',
})
export class EmploymentCategoryDeleteDialogComponent {
  employmentCategory?: IEmploymentCategory;

  constructor(
    protected employmentCategoryService: EmploymentCategoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.employmentCategoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('employmentCategoryListModification');
      this.activeModal.close();
    });
  }
}
