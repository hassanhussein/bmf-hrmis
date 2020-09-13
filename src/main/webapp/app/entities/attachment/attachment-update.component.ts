import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAttachment, Attachment } from 'app/shared/model/attachment.model';
import { AttachmentService } from './attachment.service';
import { IAttachmentType } from 'app/shared/model/attachment-type.model';
import { AttachmentTypeService } from 'app/entities/attachment-type/attachment-type.service';
import { IEmployeeRecord } from 'app/shared/model/employee-record.model';
import { EmployeeRecordService } from 'app/entities/employee-record/employee-record.service';

type SelectableEntity = IAttachmentType | IEmployeeRecord;

@Component({
  selector: 'bmf-attachment-update',
  templateUrl: './attachment-update.component.html',
})
export class AttachmentUpdateComponent implements OnInit {
  isSaving = false;
  attachmenttypes: IAttachmentType[] = [];
  employeerecords: IEmployeeRecord[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    contentId: [],
    contentLength: [],
    mimeType: [],
    typeId: [null, Validators.required],
    employeeId: [null, Validators.required],
  });

  constructor(
    protected attachmentService: AttachmentService,
    protected attachmentTypeService: AttachmentTypeService,
    protected employeeRecordService: EmployeeRecordService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attachment }) => {
      this.updateForm(attachment);

      this.attachmentTypeService.query().subscribe((res: HttpResponse<IAttachmentType[]>) => (this.attachmenttypes = res.body || []));

      this.employeeRecordService.query().subscribe((res: HttpResponse<IEmployeeRecord[]>) => (this.employeerecords = res.body || []));
    });
  }

  updateForm(attachment: IAttachment): void {
    this.editForm.patchValue({
      id: attachment.id,
      name: attachment.name,
      contentId: attachment.contentId,
      contentLength: attachment.contentLength,
      mimeType: attachment.mimeType,
      typeId: attachment.typeId,
      employeeId: attachment.employeeId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const attachment = this.createFromForm();
    if (attachment.id !== undefined) {
      this.subscribeToSaveResponse(this.attachmentService.update(attachment));
    } else {
      this.subscribeToSaveResponse(this.attachmentService.create(attachment));
    }
  }

  private createFromForm(): IAttachment {
    return {
      ...new Attachment(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      contentId: this.editForm.get(['contentId'])!.value,
      contentLength: this.editForm.get(['contentLength'])!.value,
      mimeType: this.editForm.get(['mimeType'])!.value,
      typeId: this.editForm.get(['typeId'])!.value,
      employeeId: this.editForm.get(['employeeId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttachment>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
