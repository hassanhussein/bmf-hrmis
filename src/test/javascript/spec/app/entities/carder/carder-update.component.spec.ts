import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrmisTestModule } from '../../../test.module';
import { CarderUpdateComponent } from 'app/entities/carder/carder-update.component';
import { CarderService } from 'app/entities/carder/carder.service';
import { Carder } from 'app/shared/model/carder.model';

describe('Component Tests', () => {
  describe('Carder Management Update Component', () => {
    let comp: CarderUpdateComponent;
    let fixture: ComponentFixture<CarderUpdateComponent>;
    let service: CarderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrmisTestModule],
        declarations: [CarderUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CarderUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CarderUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CarderService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Carder(123);
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
        const entity = new Carder();
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
