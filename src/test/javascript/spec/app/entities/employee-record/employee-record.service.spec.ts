import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { EmployeeRecordService } from 'app/entities/employee-record/employee-record.service';
import { IEmployeeRecord, EmployeeRecord } from 'app/shared/model/employee-record.model';

describe('Service Tests', () => {
  describe('EmployeeRecord Service', () => {
    let injector: TestBed;
    let service: EmployeeRecordService;
    let httpMock: HttpTestingController;
    let elemDefault: IEmployeeRecord;
    let expectedResult: IEmployeeRecord | IEmployeeRecord[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EmployeeRecordService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new EmployeeRecord(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        false,
        currentDate,
        0,
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            birthDate: currentDate.format(DATE_FORMAT),
            dateJoining: currentDate.format(DATE_FORMAT),
            contractStartDate: currentDate.format(DATE_FORMAT),
            contractEndDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EmployeeRecord', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            birthDate: currentDate.format(DATE_FORMAT),
            dateJoining: currentDate.format(DATE_FORMAT),
            contractStartDate: currentDate.format(DATE_FORMAT),
            contractEndDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthDate: currentDate,
            dateJoining: currentDate,
            contractStartDate: currentDate,
            contractEndDate: currentDate,
          },
          returnedFromService
        );

        service.create(new EmployeeRecord()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EmployeeRecord', () => {
        const returnedFromService = Object.assign(
          {
            firstName: 'BBBBBB',
            middleName: 'BBBBBB',
            lastName: 'BBBBBB',
            address: 'BBBBBB',
            gender: 'BBBBBB',
            birthDate: currentDate.format(DATE_FORMAT),
            email: 'BBBBBB',
            cellPhone: 'BBBBBB',
            telephone: 'BBBBBB',
            maritalStatus: 'BBBBBB',
            picture: 'BBBBBB',
            employeeNumber: 'BBBBBB',
            active: true,
            dateJoining: currentDate.format(DATE_FORMAT),
            salary: 1,
            contractStartDate: currentDate.format(DATE_FORMAT),
            contractEndDate: currentDate.format(DATE_FORMAT),
            bankName: 'BBBBBB',
            branchName: 'BBBBBB',
            bankAccount: 'BBBBBB',
            insuranceRegistrationNumber: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthDate: currentDate,
            dateJoining: currentDate,
            contractStartDate: currentDate,
            contractEndDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EmployeeRecord', () => {
        const returnedFromService = Object.assign(
          {
            firstName: 'BBBBBB',
            middleName: 'BBBBBB',
            lastName: 'BBBBBB',
            address: 'BBBBBB',
            gender: 'BBBBBB',
            birthDate: currentDate.format(DATE_FORMAT),
            email: 'BBBBBB',
            cellPhone: 'BBBBBB',
            telephone: 'BBBBBB',
            maritalStatus: 'BBBBBB',
            picture: 'BBBBBB',
            employeeNumber: 'BBBBBB',
            active: true,
            dateJoining: currentDate.format(DATE_FORMAT),
            salary: 1,
            contractStartDate: currentDate.format(DATE_FORMAT),
            contractEndDate: currentDate.format(DATE_FORMAT),
            bankName: 'BBBBBB',
            branchName: 'BBBBBB',
            bankAccount: 'BBBBBB',
            insuranceRegistrationNumber: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthDate: currentDate,
            dateJoining: currentDate,
            contractStartDate: currentDate,
            contractEndDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a EmployeeRecord', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
