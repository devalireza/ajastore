import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IItemCategory, ItemCategory } from 'app/shared/model/item-category.model';
import { ItemCategoryService } from './item-category.service';
import { ItemCategoryComponent } from './item-category.component';
import { ItemCategoryDetailComponent } from './item-category-detail.component';
import { ItemCategoryUpdateComponent } from './item-category-update.component';

@Injectable({ providedIn: 'root' })
export class ItemCategoryResolve implements Resolve<IItemCategory> {
  constructor(private service: ItemCategoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IItemCategory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((itemCategory: HttpResponse<ItemCategory>) => {
          if (itemCategory.body) {
            return of(itemCategory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ItemCategory());
  }
}

export const itemCategoryRoute: Routes = [
  {
    path: '',
    component: ItemCategoryComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'store13App.itemCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ItemCategoryDetailComponent,
    resolve: {
      itemCategory: ItemCategoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'store13App.itemCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ItemCategoryUpdateComponent,
    resolve: {
      itemCategory: ItemCategoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'store13App.itemCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ItemCategoryUpdateComponent,
    resolve: {
      itemCategory: ItemCategoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'store13App.itemCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
