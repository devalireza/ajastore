import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IItemInstance } from 'app/shared/model/item-instance.model';
import { ItemInstanceService } from './item-instance.service';

@Component({
  templateUrl: './item-instance-delete-dialog.component.html',
})
export class ItemInstanceDeleteDialogComponent {
  itemInstance?: IItemInstance;

  constructor(
    protected itemInstanceService: ItemInstanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.itemInstanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('itemInstanceListModification');
      this.activeModal.close();
    });
  }
}
