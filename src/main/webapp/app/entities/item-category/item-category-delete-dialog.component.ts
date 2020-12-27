import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IItemCategory } from 'app/shared/model/item-category.model';
import { ItemCategoryService } from './item-category.service';

@Component({
  templateUrl: './item-category-delete-dialog.component.html',
})
export class ItemCategoryDeleteDialogComponent {
  itemCategory?: IItemCategory;

  constructor(
    protected itemCategoryService: ItemCategoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.itemCategoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('itemCategoryListModification');
      this.activeModal.close();
    });
  }
}
