import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGeographicZone, GeographicZone } from 'app/shared/model/geographic-zone.model';
import { GeographicZoneService } from './geographic-zone.service';
import { GeographicZoneComponent } from './geographic-zone.component';
import { GeographicZoneDetailComponent } from './geographic-zone-detail.component';
import { GeographicZoneUpdateComponent } from './geographic-zone-update.component';

@Injectable({ providedIn: 'root' })
export class GeographicZoneResolve implements Resolve<IGeographicZone> {
  constructor(private service: GeographicZoneService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGeographicZone> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((geographicZone: HttpResponse<GeographicZone>) => {
          if (geographicZone.body) {
            return of(geographicZone.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GeographicZone());
  }
}

export const geographicZoneRoute: Routes = [
  {
    path: '',
    component: GeographicZoneComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrmisApp.geographicZone.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GeographicZoneDetailComponent,
    resolve: {
      geographicZone: GeographicZoneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrmisApp.geographicZone.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GeographicZoneUpdateComponent,
    resolve: {
      geographicZone: GeographicZoneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrmisApp.geographicZone.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GeographicZoneUpdateComponent,
    resolve: {
      geographicZone: GeographicZoneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrmisApp.geographicZone.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
