import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMenuItem, MenuItem } from 'app/shared/model/menu-item.model';
import { MenuItemService } from './menu-item.service';
import { MenuItemComponent } from './menu-item.component';
import { MenuItemDetailComponent } from './menu-item-detail.component';
import { MenuItemUpdateComponent } from './menu-item-update.component';

@Injectable({ providedIn: 'root' })
export class MenuItemResolve implements Resolve<IMenuItem> {
  constructor(private service: MenuItemService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMenuItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((menuItem: HttpResponse<MenuItem>) => {
          if (menuItem.body) {
            return of(menuItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MenuItem());
  }
}

export const menuItemRoute: Routes = [
  {
    path: '',
    component: MenuItemComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'store13App.menuItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MenuItemDetailComponent,
    resolve: {
      menuItem: MenuItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'store13App.menuItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MenuItemUpdateComponent,
    resolve: {
      menuItem: MenuItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'store13App.menuItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MenuItemUpdateComponent,
    resolve: {
      menuItem: MenuItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'store13App.menuItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
