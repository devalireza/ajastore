import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Store13SharedModule } from 'app/shared/shared.module';
import { ItemInstanceTransactComponent } from './item-instance-transact.component';
import { ItemInstanceTransactDetailComponent } from './item-instance-transact-detail.component';
import { ItemInstanceTransactUpdateComponent } from './item-instance-transact-update.component';
import { ItemInstanceTransactDeleteDialogComponent } from './item-instance-transact-delete-dialog.component';
import { itemInstanceTransactRoute } from './item-instance-transact.route';

@NgModule({
  imports: [Store13SharedModule, RouterModule.forChild(itemInstanceTransactRoute)],
  declarations: [
    ItemInstanceTransactComponent,
    ItemInstanceTransactDetailComponent,
    ItemInstanceTransactUpdateComponent,
    ItemInstanceTransactDeleteDialogComponent,
  ],
  entryComponents: [ItemInstanceTransactDeleteDialogComponent],
})
export class Store13ItemInstanceTransactModule {}
