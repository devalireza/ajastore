import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IItemInstanceTransact } from 'app/shared/model/item-instance-transact.model';

@Component({
  selector: 'jhi-item-instance-transact-detail',
  templateUrl: './item-instance-transact-detail.component.html',
})
export class ItemInstanceTransactDetailComponent implements OnInit {
  itemInstanceTransact: IItemInstanceTransact | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ itemInstanceTransact }) => (this.itemInstanceTransact = itemInstanceTransact));
  }

  previousState(): void {
    window.history.back();
  }
}
