import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { HrmisSharedModule } from 'app/shared/shared.module';
import { HrmisCoreModule } from 'app/core/core.module';
import { HrmisAppRoutingModule } from './app-routing.module';
import { HrmisHomeModule } from './home/home.module';
import { HrmisEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    HrmisSharedModule,
    HrmisCoreModule,
    HrmisHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    HrmisEntityModule,
    HrmisAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class HrmisAppModule {}
