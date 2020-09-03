import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrmisTestModule } from '../../../test.module';
import { EmployeeConfirmationUpdateComponent } from 'app/entities/employee-confirmation/employee-confirmation-update.component';
import { EmployeeConfirmationService } from 'app/entities/employee-confirmation/employee-confirmation.service';
import { EmployeeConfirmation } from 'app/shared/model/employee-confirmation.model';

describe('Component Tests', () => {
  describe('EmployeeConfirmation Management Update Component', () => {
    let comp: EmployeeConfirmationUpdateComponent;
    let fixture: ComponentFixture<EmployeeConfirmationUpdateComponent>;
    let service: EmployeeConfirmationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrmisTestModule],
        declarations: [EmployeeConfirmationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EmployeeConfirmationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmployeeConfirmationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmployeeConfirmationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmployeeConfirmation(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmployeeConfirmation();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
