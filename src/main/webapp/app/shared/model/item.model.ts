import { IItemCategory } from 'app/shared/model/item-category.model';

export interface IItem {
  id?: number;
  name?: string;
  categoty?: IItemCategory;
}

export class Item implements IItem {
  constructor(public id?: number, public name?: string, public categoty?: IItemCategory) {}
}
