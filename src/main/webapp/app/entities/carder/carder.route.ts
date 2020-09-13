import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICarder, Carder } from 'app/shared/model/carder.model';
import { CarderService } from './carder.service';
import { CarderComponent } from './carder.component';
import { CarderDetailComponent } from './carder-detail.component';
import { CarderUpdateComponent } from './carder-update.component';

@Injectable({ providedIn: 'root' })
export class CarderResolve implements Resolve<ICarder> {
  constructor(private service: CarderService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICarder> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((carder: HttpResponse<Carder>) => {
          if (carder.body) {
            return of(carder.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Carder());
  }
}

export const carderRoute: Routes = [
  {
    path: '',
    component: CarderComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrmisApp.carder.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CarderDetailComponent,
    resolve: {
      carder: CarderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrmisApp.carder.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CarderUpdateComponent,
    resolve: {
      carder: CarderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrmisApp.carder.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CarderUpdateComponent,
    resolve: {
      carder: CarderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrmisApp.carder.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
