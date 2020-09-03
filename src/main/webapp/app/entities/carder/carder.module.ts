import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrmisSharedModule } from 'app/shared/shared.module';
import { CarderComponent } from './carder.component';
import { CarderDetailComponent } from './carder-detail.component';
import { CarderUpdateComponent } from './carder-update.component';
import { CarderDeleteDialogComponent } from './carder-delete-dialog.component';
import { carderRoute } from './carder.route';

@NgModule({
  imports: [HrmisSharedModule, RouterModule.forChild(carderRoute)],
  declarations: [CarderComponent, CarderDetailComponent, CarderUpdateComponent, CarderDeleteDialogComponent],
  entryComponents: [CarderDeleteDialogComponent],
})
export class HrmisCarderModule {}
