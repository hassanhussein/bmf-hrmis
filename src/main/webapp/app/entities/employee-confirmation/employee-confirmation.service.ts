import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmployeeConfirmation } from 'app/shared/model/employee-confirmation.model';

type EntityResponseType = HttpResponse<IEmployeeConfirmation>;
type EntityArrayResponseType = HttpResponse<IEmployeeConfirmation[]>;

@Injectable({ providedIn: 'root' })
export class EmployeeConfirmationService {
  public resourceUrl = SERVER_API_URL + 'api/employee-confirmations';

  constructor(protected http: HttpClient) {}

  create(employeeConfirmation: IEmployeeConfirmation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employeeConfirmation);
    return this.http
      .post<IEmployeeConfirmation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(employeeConfirmation: IEmployeeConfirmation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employeeConfirmation);
    return this.http
      .put<IEmployeeConfirmation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmployeeConfirmation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmployeeConfirmation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(employeeConfirmation: IEmployeeConfirmation): IEmployeeConfirmation {
    const copy: IEmployeeConfirmation = Object.assign({}, employeeConfirmation, {
      date: employeeConfirmation.date && employeeConfirmation.date.isValid() ? employeeConfirmation.date.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((employeeConfirmation: IEmployeeConfirmation) => {
        employeeConfirmation.date = employeeConfirmation.date ? moment(employeeConfirmation.date) : undefined;
      });
    }
    return res;
  }
}
