import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Store13SharedModule } from 'app/shared/shared.module';
import { ItemInstanceComponent } from './item-instance.component';
import { ItemInstanceDetailComponent } from './item-instance-detail.component';
import { ItemInstanceUpdateComponent } from './item-instance-update.component';
import { ItemInstanceDeleteDialogComponent } from './item-instance-delete-dialog.component';
import { itemInstanceRoute } from './item-instance.route';

@NgModule({
  imports: [Store13SharedModule, RouterModule.forChild(itemInstanceRoute)],
  declarations: [ItemInstanceComponent, ItemInstanceDetailComponent, ItemInstanceUpdateComponent, ItemInstanceDeleteDialogComponent],
  entryComponents: [ItemInstanceDeleteDialogComponent],
})
export class Store13ItemInstanceModule {}
