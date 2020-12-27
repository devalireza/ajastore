import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Store13SharedModule } from 'app/shared/shared.module';
import { MenuItemComponent } from './menu-item.component';
import { MenuItemDetailComponent } from './menu-item-detail.component';
import { MenuItemUpdateComponent } from './menu-item-update.component';
import { MenuItemDeleteDialogComponent } from './menu-item-delete-dialog.component';
import { menuItemRoute } from './menu-item.route';

@NgModule({
  imports: [Store13SharedModule, RouterModule.forChild(menuItemRoute)],
  declarations: [MenuItemComponent, MenuItemDetailComponent, MenuItemUpdateComponent, MenuItemDeleteDialogComponent],
  entryComponents: [MenuItemDeleteDialogComponent],
})
export class Store13MenuItemModule {}
