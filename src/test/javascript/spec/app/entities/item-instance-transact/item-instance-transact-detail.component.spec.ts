import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Store13TestModule } from '../../../test.module';
import { ItemInstanceTransactDetailComponent } from 'app/entities/item-instance-transact/item-instance-transact-detail.component';
import { ItemInstanceTransact } from 'app/shared/model/item-instance-transact.model';

describe('Component Tests', () => {
  describe('ItemInstanceTransact Management Detail Component', () => {
    let comp: ItemInstanceTransactDetailComponent;
    let fixture: ComponentFixture<ItemInstanceTransactDetailComponent>;
    const route = ({ data: of({ itemInstanceTransact: new ItemInstanceTransact(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Store13TestModule],
        declarations: [ItemInstanceTransactDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ItemInstanceTransactDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ItemInstanceTransactDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load itemInstanceTransact on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.itemInstanceTransact).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
