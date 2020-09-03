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
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0,
        0,
        0,
        'image/png',
        'AAAAAAA',
        0,
        0,
        0,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            birthDate: currentDate.format(DATE_FORMAT),
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

      it('should create a Employee', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            birthDate: currentDate.format(DATE_FORMAT),
            contractStartDate: currentDate.format(DATE_FORMAT),
            contractEndDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthDate: currentDate,
            contractStartDate: currentDate,
            contractEndDate: currentDate,
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
            firstName: 'BBBBBB',
            middleName: 'BBBBBB',
            lastName: 'BBBBBB',
            gender: 'BBBBBB',
            birthDate: currentDate.format(DATE_FORMAT),
            email: 'BBBBBB',
            cellPhone: 'BBBBBB',
            maritalStatus: 'BBBBBB',
            active: true,
            contractStartDate: currentDate.format(DATE_FORMAT),
            contractEndDate: currentDate.format(DATE_FORMAT),
            bankName: 'BBBBBB',
            branchName: 'BBBBBB',
            bankAccount: 'BBBBBB',
            insuranceRegistrationNumber: 'BBBBBB',
            districtId: 1,
            facilityId: 1,
            categoryId: 1,
            trainingId: 1,
            carderId: 1,
            picture: 'BBBBBB',
            departMentCode: 1,
            attachmentId: 1,
            confirmationId: 1,
            projectId: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthDate: currentDate,
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

      it('should return a list of Employee', () => {
        const returnedFromService = Object.assign(
          {
            employeeNumber: 'BBBBBB',
            firstName: 'BBBBBB',
            middleName: 'BBBBBB',
            lastName: 'BBBBBB',
            gender: 'BBBBBB',
            birthDate: currentDate.format(DATE_FORMAT),
            email: 'BBBBBB',
            cellPhone: 'BBBBBB',
            maritalStatus: 'BBBBBB',
            active: true,
            contractStartDate: currentDate.format(DATE_FORMAT),
            contractEndDate: currentDate.format(DATE_FORMAT),
            bankName: 'BBBBBB',
            branchName: 'BBBBBB',
            bankAccount: 'BBBBBB',
            insuranceRegistrationNumber: 'BBBBBB',
            districtId: 1,
            facilityId: 1,
            categoryId: 1,
            trainingId: 1,
            carderId: 1,
            picture: 'BBBBBB',
            departMentCode: 1,
            attachmentId: 1,
            confirmationId: 1,
            projectId: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthDate: currentDate,
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
