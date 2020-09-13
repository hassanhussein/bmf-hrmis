import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGeographicLevel } from 'app/shared/model/geographic-level.model';

@Component({
  selector: 'bmf-geographic-level-detail',
  templateUrl: './geographic-level-detail.component.html',
})
export class GeographicLevelDetailComponent implements OnInit {
  geographicLevel: IGeographicLevel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ geographicLevel }) => (this.geographicLevel = geographicLevel));
  }

  previousState(): void {
    window.history.back();
  }
}
