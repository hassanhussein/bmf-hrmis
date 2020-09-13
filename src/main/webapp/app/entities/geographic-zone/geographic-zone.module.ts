import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrmisSharedModule } from 'app/shared/shared.module';
import { GeographicZoneComponent } from './geographic-zone.component';
import { GeographicZoneDetailComponent } from './geographic-zone-detail.component';
import { GeographicZoneUpdateComponent } from './geographic-zone-update.component';
import { GeographicZoneDeleteDialogComponent } from './geographic-zone-delete-dialog.component';
import { geographicZoneRoute } from './geographic-zone.route';

@NgModule({
  imports: [HrmisSharedModule, RouterModule.forChild(geographicZoneRoute)],
  declarations: [
    GeographicZoneComponent,
    GeographicZoneDetailComponent,
    GeographicZoneUpdateComponent,
    GeographicZoneDeleteDialogComponent,
  ],
  entryComponents: [GeographicZoneDeleteDialogComponent],
})
export class HrmisGeographicZoneModule {}
