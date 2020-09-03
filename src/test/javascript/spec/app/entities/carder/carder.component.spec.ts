import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrmisTestModule } from '../../../test.module';
import { CarderComponent } from 'app/entities/carder/carder.component';
import { CarderService } from 'app/entities/carder/carder.service';
import { Carder } from 'app/shared/model/carder.model';

describe('Component Tests', () => {
  describe('Carder Management Component', () => {
    let comp: CarderComponent;
    let fixture: ComponentFixture<CarderComponent>;
    let service: CarderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrmisTestModule],
        declarations: [CarderComponent],
      })
        .overrideTemplate(CarderComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CarderComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CarderService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Carder(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.carders && comp.carders[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
