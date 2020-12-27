import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Store13TestModule } from '../../../test.module';
import { MenuItemDetailComponent } from 'app/entities/menu-item/menu-item-detail.component';
import { MenuItem } from 'app/shared/model/menu-item.model';

describe('Component Tests', () => {
  describe('MenuItem Management Detail Component', () => {
    let comp: MenuItemDetailComponent;
    let fixture: ComponentFixture<MenuItemDetailComponent>;
    const route = ({ data: of({ menuItem: new MenuItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Store13TestModule],
        declarations: [MenuItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MenuItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MenuItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load menuItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.menuItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
