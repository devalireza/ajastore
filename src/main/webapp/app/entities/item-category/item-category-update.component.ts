import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IItemCategory, ItemCategory } from 'app/shared/model/item-category.model';
import { ItemCategoryService } from './item-category.service';

@Component({
  selector: 'jhi-item-category-update',
  templateUrl: './item-category-update.component.html',
})
export class ItemCategoryUpdateComponent implements OnInit {
  isSaving = false;
  itemcategories: IItemCategory[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(100)]],
    parent: [],
  });

  constructor(protected itemCategoryService: ItemCategoryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ itemCategory }) => {
      this.updateForm(itemCategory);

      this.itemCategoryService.query().subscribe((res: HttpResponse<IItemCategory[]>) => (this.itemcategories = res.body || []));
    });
  }

  updateForm(itemCategory: IItemCategory): void {
    this.editForm.patchValue({
      id: itemCategory.id,
      name: itemCategory.name,
      parent: itemCategory.parent,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const itemCategory = this.createFromForm();
    if (itemCategory.id !== undefined) {
      this.subscribeToSaveResponse(this.itemCategoryService.update(itemCategory));
    } else {
      this.subscribeToSaveResponse(this.itemCategoryService.create(itemCategory));
    }
  }

  private createFromForm(): IItemCategory {
    return {
      ...new ItemCategory(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      parent: this.editForm.get(['parent'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemCategory>>): void {
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

  trackById(index: number, item: IItemCategory): any {
    return item.id;
  }
}
