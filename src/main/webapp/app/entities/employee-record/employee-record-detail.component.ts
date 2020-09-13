import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IEmployeeRecord } from 'app/shared/model/employee-record.model';

@Component({
  selector: 'bmf-employee-record-detail',
  templateUrl: './employee-record-detail.component.html',
})
export class EmployeeRecordDetailComponent implements OnInit {
  employeeRecord: IEmployeeRecord | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employeeRecord }) => (this.employeeRecord = employeeRecord));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
