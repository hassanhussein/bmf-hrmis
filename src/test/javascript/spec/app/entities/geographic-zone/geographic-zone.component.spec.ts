import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { HrmisTestModule } from '../../../test.module';
import { GeographicZoneComponent } from 'app/entities/geographic-zone/geographic-zone.component';
import { GeographicZoneService } from 'app/entities/geographic-zone/geographic-zone.service';
import { GeographicZone } from 'app/shared/model/geographic-zone.model';

describe('Component Tests', () => {
  describe('GeographicZone Management Component', () => {
    let comp: GeographicZoneComponent;
    let fixture: ComponentFixture<GeographicZoneComponent>;
    let service: GeographicZoneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrmisTestModule],
        declarations: [GeographicZoneComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(GeographicZoneComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GeographicZoneComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GeographicZoneService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GeographicZone(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.geographicZones && comp.geographicZones[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GeographicZone(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.geographicZones && comp.geographicZones[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
