import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGeographicLevel } from 'app/shared/model/geographic-level.model';
import { GeographicLevelService } from './geographic-level.service';
import { GeographicLevelDeleteDialogComponent } from './geographic-level-delete-dialog.component';

@Component({
  selector: 'bmf-geographic-level',
  templateUrl: './geographic-level.component.html',
})
export class GeographicLevelComponent implements OnInit, OnDestroy {
  geographicLevels?: IGeographicLevel[];
  eventSubscriber?: Subscription;

  constructor(
    protected geographicLevelService: GeographicLevelService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.geographicLevelService.query().subscribe((res: HttpResponse<IGeographicLevel[]>) => (this.geographicLevels = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGeographicLevels();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGeographicLevel): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGeographicLevels(): void {
    this.eventSubscriber = this.eventManager.subscribe('geographicLevelListModification', () => this.loadAll());
  }

  delete(geographicLevel: IGeographicLevel): void {
    const modalRef = this.modalService.open(GeographicLevelDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.geographicLevel = geographicLevel;
  }
}
