import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'employee',
        loadChildren: () => import('./employee/employee.module').then(m => m.HrmisEmployeeModule),
      },
      {
        path: 'employee-confirmation',
        loadChildren: () => import('./employee-confirmation/employee-confirmation.module').then(m => m.HrmisEmployeeConfirmationModule),
      },
      {
        path: 'employment-category',
        loadChildren: () => import('./employment-category/employment-category.module').then(m => m.HrmisEmploymentCategoryModule),
      },
      {
        path: 'project',
        loadChildren: () => import('./project/project.module').then(m => m.HrmisProjectModule),
      },
      {
        path: 'department',
        loadChildren: () => import('./department/department.module').then(m => m.HrmisDepartmentModule),
      },
      {
        path: 'carder',
        loadChildren: () => import('./carder/carder.module').then(m => m.HrmisCarderModule),
      },
      {
        path: 'attachment',
        loadChildren: () => import('./attachment/attachment.module').then(m => m.HrmisAttachmentModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class HrmisEntityModule {}
