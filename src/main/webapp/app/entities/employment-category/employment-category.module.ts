import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrmisSharedModule } from 'app/shared/shared.module';
import { EmploymentCategoryComponent } from './employment-category.component';
import { EmploymentCategoryDetailComponent } from './employment-category-detail.component';
import { EmploymentCategoryUpdateComponent } from './employment-category-update.component';
import { EmploymentCategoryDeleteDialogComponent } from './employment-category-delete-dialog.component';
import { employmentCategoryRoute } from './employment-category.route';

@NgModule({
  imports: [HrmisSharedModule, RouterModule.forChild(employmentCategoryRoute)],
  declarations: [
    EmploymentCategoryComponent,
    EmploymentCategoryDetailComponent,
    EmploymentCategoryUpdateComponent,
    EmploymentCategoryDeleteDialogComponent,
  ],
  entryComponents: [EmploymentCategoryDeleteDialogComponent],
})
export class HrmisEmploymentCategoryModule {}
