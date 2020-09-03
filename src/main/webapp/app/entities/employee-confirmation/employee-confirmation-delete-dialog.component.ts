import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmployeeConfirmation } from 'app/shared/model/employee-confirmation.model';
import { EmployeeConfirmationService } from './employee-confirmation.service';

@Component({
  templateUrl: './employee-confirmation-delete-dialog.component.html',
})
export class EmployeeConfirmationDeleteDialogComponent {
  employeeConfirmation?: IEmployeeConfirmation;

  constructor(
    protected employeeConfirmationService: EmployeeConfirmationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.employeeConfirmationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('employeeConfirmationListModification');
      this.activeModal.close();
    });
  }
}
