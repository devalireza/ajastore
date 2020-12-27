import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Store13SharedModule } from 'app/shared/shared.module';
import { ItemCategoryComponent } from './item-category.component';
import { ItemCategoryDetailComponent } from './item-category-detail.component';
import { ItemCategoryUpdateComponent } from './item-category-update.component';
import { ItemCategoryDeleteDialogComponent } from './item-category-delete-dialog.component';
import { itemCategoryRoute } from './item-category.route';

@NgModule({
  imports: [Store13SharedModule, RouterModule.forChild(itemCategoryRoute)],
  declarations: [ItemCategoryComponent, ItemCategoryDetailComponent, ItemCategoryUpdateComponent, ItemCategoryDeleteDialogComponent],
  entryComponents: [ItemCategoryDeleteDialogComponent],
})
export class Store13ItemCategoryModule {}
