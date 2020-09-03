import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICarder } from 'app/shared/model/carder.model';

@Component({
  selector: 'bmf-carder-detail',
  templateUrl: './carder-detail.component.html',
})
export class CarderDetailComponent implements OnInit {
  carder: ICarder | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ carder }) => (this.carder = carder));
  }

  previousState(): void {
    window.history.back();
  }
}
