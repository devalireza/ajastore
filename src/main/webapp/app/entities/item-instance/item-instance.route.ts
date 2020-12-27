import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IItemInstance, ItemInstance } from 'app/shared/model/item-instance.model';
import { ItemInstanceService } from './item-instance.service';
import { ItemInstanceComponent } from './item-instance.component';
import { ItemInstanceDetailComponent } from './item-instance-detail.component';
import { ItemInstanceUpdateComponent } from './item-instance-update.component';

@Injectable({ providedIn: 'root' })
export class ItemInstanceResolve implements Resolve<IItemInstance> {
  constructor(private service: ItemInstanceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IItemInstance> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((itemInstance: HttpResponse<ItemInstance>) => {
          if (itemInstance.body) {
            return of(itemInstance.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ItemInstance());
  }
}

export const itemInstanceRoute: Routes = [
  {
    path: '',
    component: ItemInstanceComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'store13App.itemInstance.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ItemInstanceDetailComponent,
    resolve: {
      itemInstance: ItemInstanceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'store13App.itemInstance.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ItemInstanceUpdateComponent,
    resolve: {
      itemInstance: ItemInstanceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'store13App.itemInstance.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ItemInstanceUpdateComponent,
    resolve: {
      itemInstance: ItemInstanceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'store13App.itemInstance.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
