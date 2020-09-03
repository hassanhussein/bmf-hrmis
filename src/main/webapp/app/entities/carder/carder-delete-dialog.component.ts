import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICarder } from 'app/shared/model/carder.model';
import { CarderService } from './carder.service';

@Component({
  templateUrl: './carder-delete-dialog.component.html',
})
export class CarderDeleteDialogComponent {
  carder?: ICarder;

  constructor(protected carderService: CarderService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.carderService.delete(id).subscribe(() => {
      this.eventManager.broadcast('carderListModification');
      this.activeModal.close();
    });
  }
}
