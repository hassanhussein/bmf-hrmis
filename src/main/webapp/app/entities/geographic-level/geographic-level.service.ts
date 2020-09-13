import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGeographicLevel } from 'app/shared/model/geographic-level.model';

type EntityResponseType = HttpResponse<IGeographicLevel>;
type EntityArrayResponseType = HttpResponse<IGeographicLevel[]>;

@Injectable({ providedIn: 'root' })
export class GeographicLevelService {
  public resourceUrl = SERVER_API_URL + 'api/geographic-levels';

  constructor(protected http: HttpClient) {}

  create(geographicLevel: IGeographicLevel): Observable<EntityResponseType> {
    return this.http.post<IGeographicLevel>(this.resourceUrl, geographicLevel, { observe: 'response' });
  }

  update(geographicLevel: IGeographicLevel): Observable<EntityResponseType> {
    return this.http.put<IGeographicLevel>(this.resourceUrl, geographicLevel, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGeographicLevel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGeographicLevel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
