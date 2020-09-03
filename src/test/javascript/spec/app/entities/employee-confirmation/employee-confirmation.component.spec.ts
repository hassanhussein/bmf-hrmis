import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrmisTestModule } from '../../../test.module';
import { EmployeeConfirmationComponent } from 'app/entities/employee-confirmation/employee-confirmation.component';
import { EmployeeConfirmationService } from 'app/entities/employee-confirmation/employee-confirmation.service';
import { EmployeeConfirmation } from 'app/shared/model/employee-confirmation.model';

describe('Component Tests', () => {
  describe('EmployeeConfirmation Management Component', () => {
    let comp: EmployeeConfirmationComponent;
    let fixture: ComponentFixture<EmployeeConfirmationComponent>;
    let service: EmployeeConfirmationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrmisTestModule],
        declarations: [EmployeeConfirmationComponent],
      })
        .overrideTemplate(EmployeeConfirmationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmployeeConfirmationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmployeeConfirmationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EmployeeConfirmation(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.employeeConfirmations && comp.employeeConfirmations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
