import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrmisTestModule } from '../../../test.module';
import { EmployeeRecordUpdateComponent } from 'app/entities/employee-record/employee-record-update.component';
import { EmployeeRecordService } from 'app/entities/employee-record/employee-record.service';
import { EmployeeRecord } from 'app/shared/model/employee-record.model';

describe('Component Tests', () => {
  describe('EmployeeRecord Management Update Component', () => {
    let comp: EmployeeRecordUpdateComponent;
    let fixture: ComponentFixture<EmployeeRecordUpdateComponent>;
    let service: EmployeeRecordService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrmisTestModule],
        declarations: [EmployeeRecordUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EmployeeRecordUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmployeeRecordUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmployeeRecordService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmployeeRecord(123);
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
        const entity = new EmployeeRecord();
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
