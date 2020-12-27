import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Store13TestModule } from '../../../test.module';
import { ItemInstanceUpdateComponent } from 'app/entities/item-instance/item-instance-update.component';
import { ItemInstanceService } from 'app/entities/item-instance/item-instance.service';
import { ItemInstance } from 'app/shared/model/item-instance.model';

describe('Component Tests', () => {
  describe('ItemInstance Management Update Component', () => {
    let comp: ItemInstanceUpdateComponent;
    let fixture: ComponentFixture<ItemInstanceUpdateComponent>;
    let service: ItemInstanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Store13TestModule],
        declarations: [ItemInstanceUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ItemInstanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ItemInstanceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ItemInstanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ItemInstance(123);
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
        const entity = new ItemInstance();
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
