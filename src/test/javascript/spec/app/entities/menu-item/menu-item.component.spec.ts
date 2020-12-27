import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Store13TestModule } from '../../../test.module';
import { MenuItemComponent } from 'app/entities/menu-item/menu-item.component';
import { MenuItemService } from 'app/entities/menu-item/menu-item.service';
import { MenuItem } from 'app/shared/model/menu-item.model';

describe('Component Tests', () => {
  describe('MenuItem Management Component', () => {
    let comp: MenuItemComponent;
    let fixture: ComponentFixture<MenuItemComponent>;
    let service: MenuItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Store13TestModule],
        declarations: [MenuItemComponent],
      })
        .overrideTemplate(MenuItemComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MenuItemComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MenuItemService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MenuItem(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.menuItems && comp.menuItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
