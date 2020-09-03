import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICarder } from 'app/shared/model/carder.model';
import { CarderService } from './carder.service';
import { CarderDeleteDialogComponent } from './carder-delete-dialog.component';

@Component({
  selector: 'bmf-carder',
  templateUrl: './carder.component.html',
})
export class CarderComponent implements OnInit, OnDestroy {
  carders?: ICarder[];
  eventSubscriber?: Subscription;

  constructor(protected carderService: CarderService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.carderService.query().subscribe((res: HttpResponse<ICarder[]>) => (this.carders = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCarders();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICarder): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCarders(): void {
    this.eventSubscriber = this.eventManager.subscribe('carderListModification', () => this.loadAll());
  }

  delete(carder: ICarder): void {
    const modalRef = this.modalService.open(CarderDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.carder = carder;
  }
}
