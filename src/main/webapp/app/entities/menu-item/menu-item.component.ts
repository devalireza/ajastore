import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMenuItem } from 'app/shared/model/menu-item.model';
import { MenuItemService } from './menu-item.service';
import { MenuItemDeleteDialogComponent } from './menu-item-delete-dialog.component';

@Component({
  selector: 'jhi-menu-item',
  templateUrl: './menu-item.component.html',
})
export class MenuItemComponent implements OnInit, OnDestroy {
  menuItems?: IMenuItem[];
  eventSubscriber?: Subscription;

  constructor(protected menuItemService: MenuItemService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.menuItemService.query().subscribe((res: HttpResponse<IMenuItem[]>) => (this.menuItems = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMenuItems();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMenuItem): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMenuItems(): void {
    this.eventSubscriber = this.eventManager.subscribe('menuItemListModification', () => this.loadAll());
  }

  delete(menuItem: IMenuItem): void {
    const modalRef = this.modalService.open(MenuItemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.menuItem = menuItem;
  }
}
