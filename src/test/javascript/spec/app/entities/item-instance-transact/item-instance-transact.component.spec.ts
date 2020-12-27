import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { Store13TestModule } from '../../../test.module';
import { ItemInstanceTransactComponent } from 'app/entities/item-instance-transact/item-instance-transact.component';
import { ItemInstanceTransactService } from 'app/entities/item-instance-transact/item-instance-transact.service';
import { ItemInstanceTransact } from 'app/shared/model/item-instance-transact.model';

describe('Component Tests', () => {
  describe('ItemInstanceTransact Management Component', () => {
    let comp: ItemInstanceTransactComponent;
    let fixture: ComponentFixture<ItemInstanceTransactComponent>;
    let service: ItemInstanceTransactService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Store13TestModule],
        declarations: [ItemInstanceTransactComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(ItemInstanceTransactComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ItemInstanceTransactComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ItemInstanceTransactService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ItemInstanceTransact(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.itemInstanceTransacts && comp.itemInstanceTransacts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ItemInstanceTransact(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.itemInstanceTransacts && comp.itemInstanceTransacts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
