import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGeographicLevel } from 'app/shared/model/geographic-level.model';
import { GeographicLevelService } from './geographic-level.service';

@Component({
  templateUrl: './geographic-level-delete-dialog.component.html',
})
export class GeographicLevelDeleteDialogComponent {
  geographicLevel?: IGeographicLevel;

  constructor(
    protected geographicLevelService: GeographicLevelService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.geographicLevelService.delete(id).subscribe(() => {
      this.eventManager.broadcast('geographicLevelListModification');
      this.activeModal.close();
    });
  }
}
