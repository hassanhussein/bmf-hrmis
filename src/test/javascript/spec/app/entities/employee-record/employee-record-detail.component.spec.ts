import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { HrmisTestModule } from '../../../test.module';
import { EmployeeRecordDetailComponent } from 'app/entities/employee-record/employee-record-detail.component';
import { EmployeeRecord } from 'app/shared/model/employee-record.model';

describe('Component Tests', () => {
  describe('EmployeeRecord Management Detail Component', () => {
    let comp: EmployeeRecordDetailComponent;
    let fixture: ComponentFixture<EmployeeRecordDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ employeeRecord: new EmployeeRecord(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrmisTestModule],
        declarations: [EmployeeRecordDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmployeeRecordDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmployeeRecordDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load employeeRecord on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.employeeRecord).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
