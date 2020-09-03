import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmployeeConfirmation, EmployeeConfirmation } from 'app/shared/model/employee-confirmation.model';
import { EmployeeConfirmationService } from './employee-confirmation.service';
import { EmployeeConfirmationComponent } from './employee-confirmation.component';
import { EmployeeConfirmationDetailComponent } from './employee-confirmation-detail.component';
import { EmployeeConfirmationUpdateComponent } from './employee-confirmation-update.component';

@Injectable({ providedIn: 'root' })
export class EmployeeConfirmationResolve implements Resolve<IEmployeeConfirmation> {
  constructor(private service: EmployeeConfirmationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmployeeConfirmation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((employeeConfirmation: HttpResponse<EmployeeConfirmation>) => {
          if (employeeConfirmation.body) {
            return of(employeeConfirmation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EmployeeConfirmation());
  }
}

export const employeeConfirmationRoute: Routes = [
  {
    path: '',
    component: EmployeeConfirmationComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrmisApp.employeeConfirmation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmployeeConfirmationDetailComponent,
    resolve: {
      employeeConfirmation: EmployeeConfirmationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrmisApp.employeeConfirmation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmployeeConfirmationUpdateComponent,
    resolve: {
      employeeConfirmation: EmployeeConfirmationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrmisApp.employeeConfirmation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmployeeConfirmationUpdateComponent,
    resolve: {
      employeeConfirmation: EmployeeConfirmationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrmisApp.employeeConfirmation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
