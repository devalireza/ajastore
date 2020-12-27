import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ItemInstanceTransactService } from 'app/entities/item-instance-transact/item-instance-transact.service';
import { IItemInstanceTransact, ItemInstanceTransact } from 'app/shared/model/item-instance-transact.model';

describe('Service Tests', () => {
  describe('ItemInstanceTransact Service', () => {
    let injector: TestBed;
    let service: ItemInstanceTransactService;
    let httpMock: HttpTestingController;
    let elemDefault: IItemInstanceTransact;
    let expectedResult: IItemInstanceTransact | IItemInstanceTransact[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ItemInstanceTransactService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ItemInstanceTransact(0, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            deliveryDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ItemInstanceTransact', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            deliveryDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            deliveryDate: currentDate,
          },
          returnedFromService
        );

        service.create(new ItemInstanceTransact()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ItemInstanceTransact', () => {
        const returnedFromService = Object.assign(
          {
            deliveryDate: currentDate.format(DATE_FORMAT),
            transactionType: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            deliveryDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ItemInstanceTransact', () => {
        const returnedFromService = Object.assign(
          {
            deliveryDate: currentDate.format(DATE_FORMAT),
            transactionType: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            deliveryDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ItemInstanceTransact', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
