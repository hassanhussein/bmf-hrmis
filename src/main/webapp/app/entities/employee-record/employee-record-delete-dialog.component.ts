import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmployeeRecord } from 'app/shared/model/employee-record.model';
import { EmployeeRecordService } from './employee-record.service';

@Component({
  templateUrl: './employee-record-delete-dialog.component.html',
})
export class EmployeeRecordDeleteDialogComponent {
  employeeRecord?: IEmployeeRecord;

  constructor(
    protected employeeRecordService: EmployeeRecordService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.employeeRecordService.delete(id).subscribe(() => {
      this.eventManager.broadcast('employeeRecordListModification');
      this.activeModal.close();
    });
  }
}
