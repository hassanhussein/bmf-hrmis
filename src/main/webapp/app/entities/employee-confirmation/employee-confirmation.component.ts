import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmployeeConfirmation } from 'app/shared/model/employee-confirmation.model';
import { EmployeeConfirmationService } from './employee-confirmation.service';
import { EmployeeConfirmationDeleteDialogComponent } from './employee-confirmation-delete-dialog.component';

@Component({
  selector: 'bmf-employee-confirmation',
  templateUrl: './employee-confirmation.component.html',
})
export class EmployeeConfirmationComponent implements OnInit, OnDestroy {
  employeeConfirmations?: IEmployeeConfirmation[];
  eventSubscriber?: Subscription;

  constructor(
    protected employeeConfirmationService: EmployeeConfirmationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.employeeConfirmationService
      .query()
      .subscribe((res: HttpResponse<IEmployeeConfirmation[]>) => (this.employeeConfirmations = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEmployeeConfirmations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEmployeeConfirmation): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEmployeeConfirmations(): void {
    this.eventSubscriber = this.eventManager.subscribe('employeeConfirmationListModification', () => this.loadAll());
  }

  delete(employeeConfirmation: IEmployeeConfirmation): void {
    const modalRef = this.modalService.open(EmployeeConfirmationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.employeeConfirmation = employeeConfirmation;
  }
}
