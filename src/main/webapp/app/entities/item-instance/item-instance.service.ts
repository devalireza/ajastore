import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IItemInstance } from 'app/shared/model/item-instance.model';

type EntityResponseType = HttpResponse<IItemInstance>;
type EntityArrayResponseType = HttpResponse<IItemInstance[]>;

@Injectable({ providedIn: 'root' })
export class ItemInstanceService {
  public resourceUrl = SERVER_API_URL + 'api/item-instances';

  constructor(protected http: HttpClient) {}

  create(itemInstance: IItemInstance): Observable<EntityResponseType> {
    return this.http.post<IItemInstance>(this.resourceUrl, itemInstance, { observe: 'response' });
  }

  update(itemInstance: IItemInstance): Observable<EntityResponseType> {
    return this.http.put<IItemInstance>(this.resourceUrl, itemInstance, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IItemInstance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IItemInstance[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
