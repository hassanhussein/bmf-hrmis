import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrmisSharedModule } from 'app/shared/shared.module';
import { EmployeeRecordComponent } from './employee-record.component';
import { EmployeeRecordDetailComponent } from './employee-record-detail.component';
import { EmployeeRecordUpdateComponent } from './employee-record-update.component';
import { EmployeeRecordDeleteDialogComponent } from './employee-record-delete-dialog.component';
import { employeeRecordRoute } from './employee-record.route';

@NgModule({
  imports: [HrmisSharedModule, RouterModule.forChild(employeeRecordRoute)],
  declarations: [
    EmployeeRecordComponent,
    EmployeeRecordDetailComponent,
    EmployeeRecordUpdateComponent,
    EmployeeRecordDeleteDialogComponent,
  ],
  entryComponents: [EmployeeRecordDeleteDialogComponent],
})
export class HrmisEmployeeRecordModule {}
