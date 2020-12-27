import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IItemInstanceTransact, ItemInstanceTransact } from 'app/shared/model/item-instance-transact.model';
import { ItemInstanceTransactService } from './item-instance-transact.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IItemInstance } from 'app/shared/model/item-instance.model';
import { ItemInstanceService } from 'app/entities/item-instance/item-instance.service';

type SelectableEntity = IUser | IItemInstance;

@Component({
  selector: 'jhi-item-instance-transact-update',
  templateUrl: './item-instance-transact-update.component.html',
})
export class ItemInstanceTransactUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  iteminstances: IItemInstance[] = [];
  deliveryDateDp: any;

  editForm = this.fb.group({
    id: [],
    deliveryDate: [null, [Validators.required]],
    transactionType: [null, [Validators.required, Validators.maxLength(10)]],
    user: [],
    itemInstanceId: [],
  });

  constructor(
    protected itemInstanceTransactService: ItemInstanceTransactService,
    protected userService: UserService,
    protected itemInstanceService: ItemInstanceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ itemInstanceTransact }) => {
      this.updateForm(itemInstanceTransact);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.itemInstanceService.query().subscribe((res: HttpResponse<IItemInstance[]>) => (this.iteminstances = res.body || []));
    });
  }

  updateForm(itemInstanceTransact: IItemInstanceTransact): void {
    this.editForm.patchValue({
      id: itemInstanceTransact.id,
      deliveryDate: itemInstanceTransact.deliveryDate,
      transactionType: itemInstanceTransact.transactionType,
      user: itemInstanceTransact.user,
      itemInstanceId: itemInstanceTransact.itemInstanceId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const itemInstanceTransact = this.createFromForm();
    if (itemInstanceTransact.id !== undefined) {
      this.subscribeToSaveResponse(this.itemInstanceTransactService.update(itemInstanceTransact));
    } else {
      this.subscribeToSaveResponse(this.itemInstanceTransactService.create(itemInstanceTransact));
    }
  }

  private createFromForm(): IItemInstanceTransact {
    return {
      ...new ItemInstanceTransact(),
      id: this.editForm.get(['id'])!.value,
      deliveryDate: this.editForm.get(['deliveryDate'])!.value,
      transactionType: this.editForm.get(['transactionType'])!.value,
      user: this.editForm.get(['user'])!.value,
      itemInstanceId: this.editForm.get(['itemInstanceId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemInstanceTransact>>): void {
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
