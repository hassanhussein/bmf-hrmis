import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmploymentCategory, EmploymentCategory } from 'app/shared/model/employment-category.model';
import { EmploymentCategoryService } from './employment-category.service';
import { EmploymentCategoryComponent } from './employment-category.component';
import { EmploymentCategoryDetailComponent } from './employment-category-detail.component';
import { EmploymentCategoryUpdateComponent } from './employment-category-update.component';

@Injectable({ providedIn: 'root' })
export class EmploymentCategoryResolve implements Resolve<IEmploymentCategory> {
  constructor(private service: EmploymentCategoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmploymentCategory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((employmentCategory: HttpResponse<EmploymentCategory>) => {
          if (employmentCategory.body) {
            return of(employmentCategory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EmploymentCategory());
  }
}

export const employmentCategoryRoute: Routes = [
  {
    path: '',
    component: EmploymentCategoryComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrmisApp.employmentCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmploymentCategoryDetailComponent,
    resolve: {
      employmentCategory: EmploymentCategoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrmisApp.employmentCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmploymentCategoryUpdateComponent,
    resolve: {
      employmentCategory: EmploymentCategoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrmisApp.employmentCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmploymentCategoryUpdateComponent,
    resolve: {
      employmentCategory: EmploymentCategoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrmisApp.employmentCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
