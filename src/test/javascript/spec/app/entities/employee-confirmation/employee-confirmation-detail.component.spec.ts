import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrmisTestModule } from '../../../test.module';
import { EmployeeConfirmationDetailComponent } from 'app/entities/employee-confirmation/employee-confirmation-detail.component';
import { EmployeeConfirmation } from 'app/shared/model/employee-confirmation.model';

describe('Component Tests', () => {
  describe('EmployeeConfirmation Management Detail Component', () => {
    let comp: EmployeeConfirmationDetailComponent;
    let fixture: ComponentFixture<EmployeeConfirmationDetailComponent>;
    const route = ({ data: of({ employeeConfirmation: new EmployeeConfirmation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrmisTestModule],
        declarations: [EmployeeConfirmationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmployeeConfirmationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmployeeConfirmationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load employeeConfirmation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.employeeConfirmation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
