import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Store13TestModule } from '../../../test.module';
import { ItemInstanceDetailComponent } from 'app/entities/item-instance/item-instance-detail.component';
import { ItemInstance } from 'app/shared/model/item-instance.model';

describe('Component Tests', () => {
  describe('ItemInstance Management Detail Component', () => {
    let comp: ItemInstanceDetailComponent;
    let fixture: ComponentFixture<ItemInstanceDetailComponent>;
    const route = ({ data: of({ itemInstance: new ItemInstance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Store13TestModule],
        declarations: [ItemInstanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ItemInstanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ItemInstanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load itemInstance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.itemInstance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
