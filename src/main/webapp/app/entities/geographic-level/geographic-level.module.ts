import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrmisSharedModule } from 'app/shared/shared.module';
import { GeographicLevelComponent } from './geographic-level.component';
import { GeographicLevelDetailComponent } from './geographic-level-detail.component';
import { GeographicLevelUpdateComponent } from './geographic-level-update.component';
import { GeographicLevelDeleteDialogComponent } from './geographic-level-delete-dialog.component';
import { geographicLevelRoute } from './geographic-level.route';

@NgModule({
  imports: [HrmisSharedModule, RouterModule.forChild(geographicLevelRoute)],
  declarations: [
    GeographicLevelComponent,
    GeographicLevelDetailComponent,
    GeographicLevelUpdateComponent,
    GeographicLevelDeleteDialogComponent,
  ],
  entryComponents: [GeographicLevelDeleteDialogComponent],
})
export class HrmisGeographicLevelModule {}
