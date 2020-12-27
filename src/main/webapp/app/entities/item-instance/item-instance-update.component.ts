import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IItemInstance, ItemInstance } from 'app/shared/model/item-instance.model';
import { ItemInstanceService } from './item-instance.service';
import { IItem } from 'app/shared/model/item.model';
import { ItemService } from 'app/entities/item/item.service';
import { IStore } from 'app/shared/model/store.model';
import { StoreService } from 'app/entities/store/store.service';

type SelectableEntity = IItem | IStore;

@Component({
  selector: 'jhi-item-instance-update',
  templateUrl: './item-instance-update.component.html',
})
export class ItemInstanceUpdateComponent implements OnInit {
  isSaving = false;
  items: IItem[] = [];
  stores: IStore[] = [];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    item: [],
    store: [],
  });

  constructor(
    protected itemInstanceService: ItemInstanceService,
    protected itemService: ItemService,
    protected storeService: StoreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ itemInstance }) => {
      this.updateForm(itemInstance);

      this.itemService.query().subscribe((res: HttpResponse<IItem[]>) => (this.items = res.body || []));

      this.storeService.query().subscribe((res: HttpResponse<IStore[]>) => (this.stores = res.body || []));
    });
  }

  updateForm(itemInstance: IItemInstance): void {
    this.editForm.patchValue({
      id: itemInstance.id,
      code: itemInstance.code,
      item: itemInstance.item,
      store: itemInstance.store,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const itemInstance = this.createFromForm();
    if (itemInstance.id !== undefined) {
      this.subscribeToSaveResponse(this.itemInstanceService.update(itemInstance));
    } else {
      this.subscribeToSaveResponse(this.itemInstanceService.create(itemInstance));
    }
  }

  private createFromForm(): IItemInstance {
    return {
      ...new ItemInstance(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      item: this.editForm.get(['item'])!.value,
      store: this.editForm.get(['store'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemInstance>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
