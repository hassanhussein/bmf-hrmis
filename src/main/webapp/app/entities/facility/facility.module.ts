import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrmisSharedModule } from 'app/shared/shared.module';
import { FacilityComponent } from './facility.component';
import { FacilityDetailComponent } from './facility-detail.component';
import { FacilityUpdateComponent } from './facility-update.component';
import { FacilityDeleteDialogComponent } from './facility-delete-dialog.component';
import { facilityRoute } from './facility.route';

@NgModule({
  imports: [HrmisSharedModule, RouterModule.forChild(facilityRoute)],
  declarations: [FacilityComponent, FacilityDetailComponent, FacilityUpdateComponent, FacilityDeleteDialogComponent],
  entryComponents: [FacilityDeleteDialogComponent],
})
export class HrmisFacilityModule {}
