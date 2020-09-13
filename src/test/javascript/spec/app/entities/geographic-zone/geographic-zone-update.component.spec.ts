import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrmisTestModule } from '../../../test.module';
import { GeographicZoneUpdateComponent } from 'app/entities/geographic-zone/geographic-zone-update.component';
import { GeographicZoneService } from 'app/entities/geographic-zone/geographic-zone.service';
import { GeographicZone } from 'app/shared/model/geographic-zone.model';

describe('Component Tests', () => {
  describe('GeographicZone Management Update Component', () => {
    let comp: GeographicZoneUpdateComponent;
    let fixture: ComponentFixture<GeographicZoneUpdateComponent>;
    let service: GeographicZoneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrmisTestModule],
        declarations: [GeographicZoneUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GeographicZoneUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GeographicZoneUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GeographicZoneService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GeographicZone(123);
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
        const entity = new GeographicZone();
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
