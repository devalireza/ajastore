import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IItemInstanceTransact } from 'app/shared/model/item-instance-transact.model';

type EntityResponseType = HttpResponse<IItemInstanceTransact>;
type EntityArrayResponseType = HttpResponse<IItemInstanceTransact[]>;

@Injectable({ providedIn: 'root' })
export class ItemInstanceTransactService {
  public resourceUrl = SERVER_API_URL + 'api/item-instance-transacts';

  constructor(protected http: HttpClient) {}

  create(itemInstanceTransact: IItemInstanceTransact): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(itemInstanceTransact);
    return this.http
      .post<IItemInstanceTransact>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(itemInstanceTransact: IItemInstanceTransact): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(itemInstanceTransact);
    return this.http
      .put<IItemInstanceTransact>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IItemInstanceTransact>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IItemInstanceTransact[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(itemInstanceTransact: IItemInstanceTransact): IItemInstanceTransact {
    const copy: IItemInstanceTransact = Object.assign({}, itemInstanceTransact, {
      deliveryDate:
        itemInstanceTransact.deliveryDate && itemInstanceTransact.deliveryDate.isValid()
          ? itemInstanceTransact.deliveryDate.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.deliveryDate = res.body.deliveryDate ? moment(res.body.deliveryDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((itemInstanceTransact: IItemInstanceTransact) => {
        itemInstanceTransact.deliveryDate = itemInstanceTransact.deliveryDate ? moment(itemInstanceTransact.deliveryDate) : undefined;
      });
    }
    return res;
  }
}
