import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmploymentCategory } from 'app/shared/model/employment-category.model';

type EntityResponseType = HttpResponse<IEmploymentCategory>;
type EntityArrayResponseType = HttpResponse<IEmploymentCategory[]>;

@Injectable({ providedIn: 'root' })
export class EmploymentCategoryService {
  public resourceUrl = SERVER_API_URL + 'api/employment-categories';

  constructor(protected http: HttpClient) {}

  create(employmentCategory: IEmploymentCategory): Observable<EntityResponseType> {
    return this.http.post<IEmploymentCategory>(this.resourceUrl, employmentCategory, { observe: 'response' });
  }

  update(employmentCategory: IEmploymentCategory): Observable<EntityResponseType> {
    return this.http.put<IEmploymentCategory>(this.resourceUrl, employmentCategory, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmploymentCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmploymentCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
