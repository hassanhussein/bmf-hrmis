import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrmisSharedModule } from 'app/shared/shared.module';
import { EmployeeConfirmationComponent } from './employee-confirmation.component';
import { EmployeeConfirmationDetailComponent } from './employee-confirmation-detail.component';
import { EmployeeConfirmationUpdateComponent } from './employee-confirmation-update.component';
import { EmployeeConfirmationDeleteDialogComponent } from './employee-confirmation-delete-dialog.component';
import { employeeConfirmationRoute } from './employee-confirmation.route';

@NgModule({
  imports: [HrmisSharedModule, RouterModule.forChild(employeeConfirmationRoute)],
  declarations: [
    EmployeeConfirmationComponent,
    EmployeeConfirmationDetailComponent,
    EmployeeConfirmationUpdateComponent,
    EmployeeConfirmationDeleteDialogComponent,
  ],
  entryComponents: [EmployeeConfirmationDeleteDialogComponent],
})
export class HrmisEmployeeConfirmationModule {}
