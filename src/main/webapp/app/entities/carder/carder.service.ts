import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICarder } from 'app/shared/model/carder.model';

type EntityResponseType = HttpResponse<ICarder>;
type EntityArrayResponseType = HttpResponse<ICarder[]>;

@Injectable({ providedIn: 'root' })
export class CarderService {
  public resourceUrl = SERVER_API_URL + 'api/carders';

  constructor(protected http: HttpClient) {}

  create(carder: ICarder): Observable<EntityResponseType> {
    return this.http.post<ICarder>(this.resourceUrl, carder, { observe: 'response' });
  }

  update(carder: ICarder): Observable<EntityResponseType> {
    return this.http.put<ICarder>(this.resourceUrl, carder, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICarder>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICarder[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
