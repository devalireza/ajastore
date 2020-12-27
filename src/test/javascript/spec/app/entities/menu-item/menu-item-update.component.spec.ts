import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Store13TestModule } from '../../../test.module';
import { MenuItemUpdateComponent } from 'app/entities/menu-item/menu-item-update.component';
import { MenuItemService } from 'app/entities/menu-item/menu-item.service';
import { MenuItem } from 'app/shared/model/menu-item.model';

describe('Component Tests', () => {
  describe('MenuItem Management Update Component', () => {
    let comp: MenuItemUpdateComponent;
    let fixture: ComponentFixture<MenuItemUpdateComponent>;
    let service: MenuItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Store13TestModule],
        declarations: [MenuItemUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MenuItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MenuItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MenuItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MenuItem(123);
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
        const entity = new MenuItem();
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
