import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IItemInstanceTransact } from 'app/shared/model/item-instance-transact.model';
import { ItemInstanceTransactService } from './item-instance-transact.service';

@Component({
  templateUrl: './item-instance-transact-delete-dialog.component.html',
})
export class ItemInstanceTransactDeleteDialogComponent {
  itemInstanceTransact?: IItemInstanceTransact;

  constructor(
    protected itemInstanceTransactService: ItemInstanceTransactService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.itemInstanceTransactService.delete(id).subscribe(() => {
      this.eventManager.broadcast('itemInstanceTransactListModification');
      this.activeModal.close();
    });
  }
}
