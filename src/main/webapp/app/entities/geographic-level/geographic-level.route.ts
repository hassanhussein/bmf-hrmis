import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGeographicLevel, GeographicLevel } from 'app/shared/model/geographic-level.model';
import { GeographicLevelService } from './geographic-level.service';
import { GeographicLevelComponent } from './geographic-level.component';
import { GeographicLevelDetailComponent } from './geographic-level-detail.component';
import { GeographicLevelUpdateComponent } from './geographic-level-update.component';

@Injectable({ providedIn: 'root' })
export class GeographicLevelResolve implements Resolve<IGeographicLevel> {
  constructor(private service: GeographicLevelService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGeographicLevel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((geographicLevel: HttpResponse<GeographicLevel>) => {
          if (geographicLevel.body) {
            return of(geographicLevel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GeographicLevel());
  }
}

export const geographicLevelRoute: Routes = [
  {
    path: '',
    component: GeographicLevelComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrmisApp.geographicLevel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GeographicLevelDetailComponent,
    resolve: {
      geographicLevel: GeographicLevelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrmisApp.geographicLevel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GeographicLevelUpdateComponent,
    resolve: {
      geographicLevel: GeographicLevelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrmisApp.geographicLevel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GeographicLevelUpdateComponent,
    resolve: {
      geographicLevel: GeographicLevelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrmisApp.geographicLevel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
