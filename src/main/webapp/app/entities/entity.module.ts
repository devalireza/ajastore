import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'item',
        loadChildren: () => import('./item/item.module').then(m => m.Store13ItemModule),
      },
      {
        path: 'item-instance',
        loadChildren: () => import('./item-instance/item-instance.module').then(m => m.Store13ItemInstanceModule),
      },
      {
        path: 'department',
        loadChildren: () => import('./department/department.module').then(m => m.Store13DepartmentModule),
      },
      {
        path: 'item-category',
        loadChildren: () => import('./item-category/item-category.module').then(m => m.Store13ItemCategoryModule),
      },
      {
        path: 'store',
        loadChildren: () => import('./store/store.module').then(m => m.Store13StoreModule),
      },
      {
        path: 'item-instance-transact',
        loadChildren: () => import('./item-instance-transact/item-instance-transact.module').then(m => m.Store13ItemInstanceTransactModule),
      },
      {
        path: 'menu-item',
        loadChildren: () => import('./menu-item/menu-item.module').then(m => m.Store13MenuItemModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class Store13EntityModule {}
