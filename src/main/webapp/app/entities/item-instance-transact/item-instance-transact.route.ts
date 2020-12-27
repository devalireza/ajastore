import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IItemInstanceTransact, ItemInstanceTransact } from 'app/shared/model/item-instance-transact.model';
import { ItemInstanceTransactService } from './item-instance-transact.service';
import { ItemInstanceTransactComponent } from './item-instance-transact.component';
import { ItemInstanceTransactDetailComponent } from './item-instance-transact-detail.component';
import { ItemInstanceTransactUpdateComponent } from './item-instance-transact-update.component';

@Injectable({ providedIn: 'root' })
export class ItemInstanceTransactResolve implements Resolve<IItemInstanceTransact> {
  constructor(private service: ItemInstanceTransactService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IItemInstanceTransact> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((itemInstanceTransact: HttpResponse<ItemInstanceTransact>) => {
          if (itemInstanceTransact.body) {
            return of(itemInstanceTransact.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ItemInstanceTransact());
  }
}

export const itemInstanceTransactRoute: Routes = [
  {
    path: '',
    component: ItemInstanceTransactComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'store13App.itemInstanceTransact.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ItemInstanceTransactDetailComponent,
    resolve: {
      itemInstanceTransact: ItemInstanceTransactResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'store13App.itemInstanceTransact.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ItemInstanceTransactUpdateComponent,
    resolve: {
      itemInstanceTransact: ItemInstanceTransactResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'store13App.itemInstanceTransact.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ItemInstanceTransactUpdateComponent,
    resolve: {
      itemInstanceTransact: ItemInstanceTransactResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'store13App.itemInstanceTransact.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
