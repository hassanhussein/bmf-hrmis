import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { IEmployee, Employee } from 'app/shared/model/employee.model';

describe('Service Tests', () => {
  describe('Employee Service', () => {
    let injector: TestBed;
    let service: EmployeeService;
    let httpMock: HttpTestingController;
    let elemDefault: IEmployee;
    let expectedResult: IEmployee | IEmployee[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EmployeeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Employee(
        0,
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        0,
        0,
        0,
        0,
        0,
        'AAAAAAA',
        0,
        false,
        'AAAAAAA',
        'AAAAAAA',
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            contractStartDate: currentDate.format(DATE_FORMAT),
            contractEndDate: currentDate.format(DATE_FORMAT),
            dateJoining: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Employee', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            contractStartDate: currentDate.format(DATE_FORMAT),
            contractEndDate: currentDate.format(DATE_FORMAT),
            dateJoining: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            contractStartDate: currentDate,
            contractEndDate: currentDate,
            dateJoining: currentDate,
          },
          returnedFromService
        );

        service.create(new Employee()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Employee', () => {
        const returnedFromService = Object.assign(
          {
            employeeNumber: 'BBBBBB',
            contractStartDate: currentDate.format(DATE_FORMAT),
            contractEndDate: currentDate.format(DATE_FORMAT),
            bankName: 'BBBBBB',
            branchName: 'BBBBBB',
            bankAccount: 'BBBBBB',
            insuranceRegistrationNumber: 'BBBBBB',
            dateJoining: currentDate.format(DATE_FORMAT),
            designation: 'BBBBBB',
            districtId: 1,
            facilityId: 1,
            categoryId: 1,
            trainingId: 1,
            carderId: 1,
            departmentName: 'BBBBBB',
            confirmationId: 1,
            isConfirmed: true,
            confirmationLetterName: 'BBBBBB',
            projectName: 'BBBBBB',
            active: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            contractStartDate: currentDate,
            contractEndDate: currentDate,
            dateJoining: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Employee', () => {
        const returnedFromService = Object.assign(
          {
            employeeNumber: 'BBBBBB',
            contractStartDate: currentDate.format(DATE_FORMAT),
            contractEndDate: currentDate.format(DATE_FORMAT),
            bankName: 'BBBBBB',
            branchName: 'BBBBBB',
            bankAccount: 'BBBBBB',
            insuranceRegistrationNumber: 'BBBBBB',
            dateJoining: currentDate.format(DATE_FORMAT),
            designation: 'BBBBBB',
            districtId: 1,
            facilityId: 1,
            categoryId: 1,
            trainingId: 1,
            carderId: 1,
            departmentName: 'BBBBBB',
            confirmationId: 1,
            isConfirmed: true,
            confirmationLetterName: 'BBBBBB',
            projectName: 'BBBBBB',
            active: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            contractStartDate: currentDate,
            contractEndDate: currentDate,
            dateJoining: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Employee', () => {
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
