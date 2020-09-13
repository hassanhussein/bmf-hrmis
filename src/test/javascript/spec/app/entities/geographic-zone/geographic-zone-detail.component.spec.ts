import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrmisTestModule } from '../../../test.module';
import { GeographicZoneDetailComponent } from 'app/entities/geographic-zone/geographic-zone-detail.component';
import { GeographicZone } from 'app/shared/model/geographic-zone.model';

describe('Component Tests', () => {
  describe('GeographicZone Management Detail Component', () => {
    let comp: GeographicZoneDetailComponent;
    let fixture: ComponentFixture<GeographicZoneDetailComponent>;
    const route = ({ data: of({ geographicZone: new GeographicZone(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrmisTestModule],
        declarations: [GeographicZoneDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GeographicZoneDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GeographicZoneDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load geographicZone on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.geographicZone).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
