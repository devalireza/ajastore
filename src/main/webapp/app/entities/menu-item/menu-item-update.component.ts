import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMenuItem, MenuItem } from 'app/shared/model/menu-item.model';
import { MenuItemService } from './menu-item.service';

@Component({
  selector: 'jhi-menu-item-update',
  templateUrl: './menu-item-update.component.html',
})
export class MenuItemUpdateComponent implements OnInit {
  isSaving = false;
  menuitems: IMenuItem[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    entityName: [],
    parent: [],
  });

  constructor(protected menuItemService: MenuItemService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ menuItem }) => {
      this.updateForm(menuItem);

      this.menuItemService.query().subscribe((res: HttpResponse<IMenuItem[]>) => (this.menuitems = res.body || []));
    });
  }

  updateForm(menuItem: IMenuItem): void {
    this.editForm.patchValue({
      id: menuItem.id,
      name: menuItem.name,
      entityName: menuItem.entityName,
      parent: menuItem.parent,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const menuItem = this.createFromForm();
    if (menuItem.id !== undefined) {
      this.subscribeToSaveResponse(this.menuItemService.update(menuItem));
    } else {
      this.subscribeToSaveResponse(this.menuItemService.create(menuItem));
    }
  }

  private createFromForm(): IMenuItem {
    return {
      ...new MenuItem(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      entityName: this.editForm.get(['entityName'])!.value,
      parent: this.editForm.get(['parent'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMenuItem>>): void {
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

  trackById(index: number, item: IMenuItem): any {
    return item.id;
  }
}
