import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Store13TestModule } from '../../../test.module';
import { ItemInstanceTransactUpdateComponent } from 'app/entities/item-instance-transact/item-instance-transact-update.component';
import { ItemInstanceTransactService } from 'app/entities/item-instance-transact/item-instance-transact.service';
import { ItemInstanceTransact } from 'app/shared/model/item-instance-transact.model';

describe('Component Tests', () => {
  describe('ItemInstanceTransact Management Update Component', () => {
    let comp: ItemInstanceTransactUpdateComponent;
    let fixture: ComponentFixture<ItemInstanceTransactUpdateComponent>;
    let service: ItemInstanceTransactService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Store13TestModule],
        declarations: [ItemInstanceTransactUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ItemInstanceTransactUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ItemInstanceTransactUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ItemInstanceTransactService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ItemInstanceTransact(123);
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
        const entity = new ItemInstanceTransact();
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
