import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGeographicZone } from 'app/shared/model/geographic-zone.model';

type EntityResponseType = HttpResponse<IGeographicZone>;
type EntityArrayResponseType = HttpResponse<IGeographicZone[]>;

@Injectable({ providedIn: 'root' })
export class GeographicZoneService {
  public resourceUrl = SERVER_API_URL + 'api/geographic-zones';

  constructor(protected http: HttpClient) {}

  create(geographicZone: IGeographicZone): Observable<EntityResponseType> {
    return this.http.post<IGeographicZone>(this.resourceUrl, geographicZone, { observe: 'response' });
  }

  update(geographicZone: IGeographicZone): Observable<EntityResponseType> {
    return this.http.put<IGeographicZone>(this.resourceUrl, geographicZone, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGeographicZone>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGeographicZone[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
