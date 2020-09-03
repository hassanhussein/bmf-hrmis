import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrmisTestModule } from '../../../test.module';
import { EmploymentCategoryComponent } from 'app/entities/employment-category/employment-category.component';
import { EmploymentCategoryService } from 'app/entities/employment-category/employment-category.service';
import { EmploymentCategory } from 'app/shared/model/employment-category.model';

describe('Component Tests', () => {
  describe('EmploymentCategory Management Component', () => {
    let comp: EmploymentCategoryComponent;
    let fixture: ComponentFixture<EmploymentCategoryComponent>;
    let service: EmploymentCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrmisTestModule],
        declarations: [EmploymentCategoryComponent],
      })
        .overrideTemplate(EmploymentCategoryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmploymentCategoryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmploymentCategoryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EmploymentCategory(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.employmentCategories && comp.employmentCategories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
