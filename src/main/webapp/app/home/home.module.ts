import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Store13SharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [Store13SharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class Store13HomeModule {}
