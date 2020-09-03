import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrmisTestModule } from '../../../test.module';
import { EmploymentCategoryUpdateComponent } from 'app/entities/employment-category/employment-category-update.component';
import { EmploymentCategoryService } from 'app/entities/employment-category/employment-category.service';
import { EmploymentCategory } from 'app/shared/model/employment-category.model';

describe('Component Tests', () => {
  describe('EmploymentCategory Management Update Component', () => {
    let comp: EmploymentCategoryUpdateComponent;
    let fixture: ComponentFixture<EmploymentCategoryUpdateComponent>;
    let service: EmploymentCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrmisTestModule],
        declarations: [EmploymentCategoryUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EmploymentCategoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmploymentCategoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmploymentCategoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmploymentCategory(123);
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
        const entity = new EmploymentCategory();
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
