import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrmisTestModule } from '../../../test.module';
import { GeographicLevelDetailComponent } from 'app/entities/geographic-level/geographic-level-detail.component';
import { GeographicLevel } from 'app/shared/model/geographic-level.model';

describe('Component Tests', () => {
  describe('GeographicLevel Management Detail Component', () => {
    let comp: GeographicLevelDetailComponent;
    let fixture: ComponentFixture<GeographicLevelDetailComponent>;
    const route = ({ data: of({ geographicLevel: new GeographicLevel(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrmisTestModule],
        declarations: [GeographicLevelDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GeographicLevelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GeographicLevelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load geographicLevel on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.geographicLevel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
