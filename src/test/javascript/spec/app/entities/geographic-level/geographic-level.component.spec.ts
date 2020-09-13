import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrmisTestModule } from '../../../test.module';
import { GeographicLevelComponent } from 'app/entities/geographic-level/geographic-level.component';
import { GeographicLevelService } from 'app/entities/geographic-level/geographic-level.service';
import { GeographicLevel } from 'app/shared/model/geographic-level.model';

describe('Component Tests', () => {
  describe('GeographicLevel Management Component', () => {
    let comp: GeographicLevelComponent;
    let fixture: ComponentFixture<GeographicLevelComponent>;
    let service: GeographicLevelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrmisTestModule],
        declarations: [GeographicLevelComponent],
      })
        .overrideTemplate(GeographicLevelComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GeographicLevelComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GeographicLevelService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GeographicLevel(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.geographicLevels && comp.geographicLevels[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
