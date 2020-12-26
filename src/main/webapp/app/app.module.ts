import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { Store13SharedModule } from 'app/shared/shared.module';
import { Store13CoreModule } from 'app/core/core.module';
import { Store13AppRoutingModule } from './app-routing.module';
import { Store13HomeModule } from './home/home.module';
import { Store13EntityModule } from './entities/entity.module';
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
    Store13SharedModule,
    Store13CoreModule,
    Store13HomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    Store13EntityModule,
    Store13AppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class Store13AppModule {}
