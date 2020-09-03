import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrmisTestModule } from '../../../test.module';
import { EmploymentCategoryDetailComponent } from 'app/entities/employment-category/employment-category-detail.component';
import { EmploymentCategory } from 'app/shared/model/employment-category.model';

describe('Component Tests', () => {
  describe('EmploymentCategory Management Detail Component', () => {
    let comp: EmploymentCategoryDetailComponent;
    let fixture: ComponentFixture<EmploymentCategoryDetailComponent>;
    const route = ({ data: of({ employmentCategory: new EmploymentCategory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrmisTestModule],
        declarations: [EmploymentCategoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmploymentCategoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmploymentCategoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load employmentCategory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.employmentCategory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
