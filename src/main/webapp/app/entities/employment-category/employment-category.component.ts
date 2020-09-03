import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmploymentCategory } from 'app/shared/model/employment-category.model';
import { EmploymentCategoryService } from './employment-category.service';
import { EmploymentCategoryDeleteDialogComponent } from './employment-category-delete-dialog.component';

@Component({
  selector: 'bmf-employment-category',
  templateUrl: './employment-category.component.html',
})
export class EmploymentCategoryComponent implements OnInit, OnDestroy {
  employmentCategories?: IEmploymentCategory[];
  eventSubscriber?: Subscription;

  constructor(
    protected employmentCategoryService: EmploymentCategoryService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.employmentCategoryService
      .query()
      .subscribe((res: HttpResponse<IEmploymentCategory[]>) => (this.employmentCategories = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEmploymentCategories();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEmploymentCategory): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEmploymentCategories(): void {
    this.eventSubscriber = this.eventManager.subscribe('employmentCategoryListModification', () => this.loadAll());
  }

  delete(employmentCategory: IEmploymentCategory): void {
    const modalRef = this.modalService.open(EmploymentCategoryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.employmentCategory = employmentCategory;
  }
}
