import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGeographicZone } from 'app/shared/model/geographic-zone.model';
import { GeographicZoneService } from './geographic-zone.service';

@Component({
  templateUrl: './geographic-zone-delete-dialog.component.html',
})
export class GeographicZoneDeleteDialogComponent {
  geographicZone?: IGeographicZone;

  constructor(
    protected geographicZoneService: GeographicZoneService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.geographicZoneService.delete(id).subscribe(() => {
      this.eventManager.broadcast('geographicZoneListModification');
      this.activeModal.close();
    });
  }
}
