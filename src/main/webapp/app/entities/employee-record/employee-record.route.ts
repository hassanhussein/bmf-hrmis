import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmployeeRecord, EmployeeRecord } from 'app/shared/model/employee-record.model';
import { EmployeeRecordService } from './employee-record.service';
import { EmployeeRecordComponent } from './employee-record.component';
import { EmployeeRecordDetailComponent } from './employee-record-detail.component';
import { EmployeeRecordUpdateComponent } from './employee-record-update.component';

@Injectable({ providedIn: 'root' })
export class EmployeeRecordResolve implements Resolve<IEmployeeRecord> {
  constructor(private service: EmployeeRecordService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmployeeRecord> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((employeeRecord: HttpResponse<EmployeeRecord>) => {
          if (employeeRecord.body) {
            return of(employeeRecord.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EmployeeRecord());
  }
}

export const employeeRecordRoute: Routes = [
  {
    path: '',
    component: EmployeeRecordComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrmisApp.employeeRecord.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmployeeRecordDetailComponent,
    resolve: {
      employeeRecord: EmployeeRecordResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrmisApp.employeeRecord.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmployeeRecordUpdateComponent,
    resolve: {
      employeeRecord: EmployeeRecordResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrmisApp.employeeRecord.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmployeeRecordUpdateComponent,
    resolve: {
      employeeRecord: EmployeeRecordResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrmisApp.employeeRecord.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
