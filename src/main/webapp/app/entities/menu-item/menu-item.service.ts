import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMenuItem } from 'app/shared/model/menu-item.model';

type EntityResponseType = HttpResponse<IMenuItem>;
type EntityArrayResponseType = HttpResponse<IMenuItem[]>;

@Injectable({ providedIn: 'root' })
export class MenuItemService {
  public resourceUrl = SERVER_API_URL + 'api/menu-items';

  constructor(protected http: HttpClient) {}

  create(menuItem: IMenuItem): Observable<EntityResponseType> {
    return this.http.post<IMenuItem>(this.resourceUrl, menuItem, { observe: 'response' });
  }

  update(menuItem: IMenuItem): Observable<EntityResponseType> {
    return this.http.put<IMenuItem>(this.resourceUrl, menuItem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMenuItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMenuItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
