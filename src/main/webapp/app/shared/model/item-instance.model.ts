import { IItem } from 'app/shared/model/item.model';
import { IStore } from 'app/shared/model/store.model';

export interface IItemInstance {
  id?: number;
  code?: string;
  item?: IItem;
  store?: IStore;
}

export class ItemInstance implements IItemInstance {
  constructor(public id?: number, public code?: string, public item?: IItem, public store?: IStore) {}
}
