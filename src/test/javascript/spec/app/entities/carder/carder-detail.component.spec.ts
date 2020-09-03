import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrmisTestModule } from '../../../test.module';
import { CarderDetailComponent } from 'app/entities/carder/carder-detail.component';
import { Carder } from 'app/shared/model/carder.model';

describe('Component Tests', () => {
  describe('Carder Management Detail Component', () => {
    let comp: CarderDetailComponent;
    let fixture: ComponentFixture<CarderDetailComponent>;
    const route = ({ data: of({ carder: new Carder(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrmisTestModule],
        declarations: [CarderDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CarderDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CarderDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load carder on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.carder).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
