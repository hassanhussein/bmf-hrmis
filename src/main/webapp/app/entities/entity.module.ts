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
      {
        path: 'facility-type',
        loadChildren: () => import('./facility-type/facility-type.module').then(m => m.HrmisFacilityTypeModule),
      },
      {
        path: 'geographic-level',
        loadChildren: () => import('./geographic-level/geographic-level.module').then(m => m.HrmisGeographicLevelModule),
      },
      {
        path: 'geographic-zone',
        loadChildren: () => import('./geographic-zone/geographic-zone.module').then(m => m.HrmisGeographicZoneModule),
      },
      {
        path: 'facility',
        loadChildren: () => import('./facility/facility.module').then(m => m.HrmisFacilityModule),
      },
      {
        path: 'attachment-type',
        loadChildren: () => import('./attachment-type/attachment-type.module').then(m => m.HrmisAttachmentTypeModule),
      },
      {
        path: 'employee-record',
        loadChildren: () => import('./employee-record/employee-record.module').then(m => m.HrmisEmployeeRecordModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class HrmisEntityModule {}
