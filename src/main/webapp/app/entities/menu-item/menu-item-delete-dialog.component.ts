import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMenuItem } from 'app/shared/model/menu-item.model';
import { MenuItemService } from './menu-item.service';

@Component({
  templateUrl: './menu-item-delete-dialog.component.html',
})
export class MenuItemDeleteDialogComponent {
  menuItem?: IMenuItem;

  constructor(protected menuItemService: MenuItemService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.menuItemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('menuItemListModification');
      this.activeModal.close();
    });
  }
}
