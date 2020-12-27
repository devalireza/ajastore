import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IItemInstance } from 'app/shared/model/item-instance.model';

@Component({
  selector: 'jhi-item-instance-detail',
  templateUrl: './item-instance-detail.component.html',
})
export class ItemInstanceDetailComponent implements OnInit {
  itemInstance: IItemInstance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ itemInstance }) => (this.itemInstance = itemInstance));
  }

  previousState(): void {
    window.history.back();
  }
}
