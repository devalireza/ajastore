import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Store13TestModule } from '../../../test.module';
import { ItemCategoryDetailComponent } from 'app/entities/item-category/item-category-detail.component';
import { ItemCategory } from 'app/shared/model/item-category.model';

describe('Component Tests', () => {
  describe('ItemCategory Management Detail Component', () => {
    let comp: ItemCategoryDetailComponent;
    let fixture: ComponentFixture<ItemCategoryDetailComponent>;
    const route = ({ data: of({ itemCategory: new ItemCategory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Store13TestModule],
        declarations: [ItemCategoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ItemCategoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ItemCategoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load itemCategory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.itemCategory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
