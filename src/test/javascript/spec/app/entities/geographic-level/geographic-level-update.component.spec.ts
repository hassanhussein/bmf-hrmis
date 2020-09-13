import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrmisTestModule } from '../../../test.module';
import { GeographicLevelUpdateComponent } from 'app/entities/geographic-level/geographic-level-update.component';
import { GeographicLevelService } from 'app/entities/geographic-level/geographic-level.service';
import { GeographicLevel } from 'app/shared/model/geographic-level.model';

describe('Component Tests', () => {
  describe('GeographicLevel Management Update Component', () => {
    let comp: GeographicLevelUpdateComponent;
    let fixture: ComponentFixture<GeographicLevelUpdateComponent>;
    let service: GeographicLevelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrmisTestModule],
        declarations: [GeographicLevelUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GeographicLevelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GeographicLevelUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GeographicLevelService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GeographicLevel(123);
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
        const entity = new GeographicLevel();
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
