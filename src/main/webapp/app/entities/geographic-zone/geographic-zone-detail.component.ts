import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGeographicZone } from 'app/shared/model/geographic-zone.model';

@Component({
  selector: 'bmf-geographic-zone-detail',
  templateUrl: './geographic-zone-detail.component.html',
})
export class GeographicZoneDetailComponent implements OnInit {
  geographicZone: IGeographicZone | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ geographicZone }) => (this.geographicZone = geographicZone));
  }

  previousState(): void {
    window.history.back();
  }
}
