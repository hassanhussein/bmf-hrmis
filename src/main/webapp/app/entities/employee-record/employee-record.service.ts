import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmployeeRecord } from 'app/shared/model/employee-record.model';

type EntityResponseType = HttpResponse<IEmployeeRecord>;
type EntityArrayResponseType = HttpResponse<IEmployeeRecord[]>;

@Injectable({ providedIn: 'root' })
export class EmployeeRecordService {
  public resourceUrl = SERVER_API_URL + 'api/employee-records';

  constructor(protected http: HttpClient) {}

  create(employeeRecord: IEmployeeRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employeeRecord);
    return this.http
      .post<IEmployeeRecord>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(employeeRecord: IEmployeeRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employeeRecord);
    return this.http
      .put<IEmployeeRecord>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmployeeRecord>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmployeeRecord[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(employeeRecord: IEmployeeRecord): IEmployeeRecord {
    const copy: IEmployeeRecord = Object.assign({}, employeeRecord, {
      birthDate: employeeRecord.birthDate && employeeRecord.birthDate.isValid() ? employeeRecord.birthDate.format(DATE_FORMAT) : undefined,
      dateJoining:
        employeeRecord.dateJoining && employeeRecord.dateJoining.isValid() ? employeeRecord.dateJoining.format(DATE_FORMAT) : undefined,
      contractStartDate:
        employeeRecord.contractStartDate && employeeRecord.contractStartDate.isValid()
          ? employeeRecord.contractStartDate.format(DATE_FORMAT)
          : undefined,
      contractEndDate:
        employeeRecord.contractEndDate && employeeRecord.contractEndDate.isValid()
          ? employeeRecord.contractEndDate.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.birthDate = res.body.birthDate ? moment(res.body.birthDate) : undefined;
      res.body.dateJoining = res.body.dateJoining ? moment(res.body.dateJoining) : undefined;
      res.body.contractStartDate = res.body.contractStartDate ? moment(res.body.contractStartDate) : undefined;
      res.body.contractEndDate = res.body.contractEndDate ? moment(res.body.contractEndDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((employeeRecord: IEmployeeRecord) => {
        employeeRecord.birthDate = employeeRecord.birthDate ? moment(employeeRecord.birthDate) : undefined;
        employeeRecord.dateJoining = employeeRecord.dateJoining ? moment(employeeRecord.dateJoining) : undefined;
        employeeRecord.contractStartDate = employeeRecord.contractStartDate ? moment(employeeRecord.contractStartDate) : undefined;
        employeeRecord.contractEndDate = employeeRecord.contractEndDate ? moment(employeeRecord.contractEndDate) : undefined;
      });
    }
    return res;
  }
}
