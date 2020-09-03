import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmploymentCategory } from 'app/shared/model/employment-category.model';

@Component({
  selector: 'bmf-employment-category-detail',
  templateUrl: './employment-category-detail.component.html',
})
export class EmploymentCategoryDetailComponent implements OnInit {
  employmentCategory: IEmploymentCategory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employmentCategory }) => (this.employmentCategory = employmentCategory));
  }

  previousState(): void {
    window.history.back();
  }
}
