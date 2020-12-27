import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Store13TestModule } from '../../../test.module';
import { ItemCategoryUpdateComponent } from 'app/entities/item-category/item-category-update.component';
import { ItemCategoryService } from 'app/entities/item-category/item-category.service';
import { ItemCategory } from 'app/shared/model/item-category.model';

describe('Component Tests', () => {
  describe('ItemCategory Management Update Component', () => {
    let comp: ItemCategoryUpdateComponent;
    let fixture: ComponentFixture<ItemCategoryUpdateComponent>;
    let service: ItemCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Store13TestModule],
        declarations: [ItemCategoryUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ItemCategoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ItemCategoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ItemCategoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ItemCategory(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ItemCategory();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
