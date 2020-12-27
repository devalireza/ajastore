export interface IItemCategory {
  id?: number;
  name?: string;
  parent?: IItemCategory;
}

export class ItemCategory implements IItemCategory {
  constructor(public id?: number, public name?: string, public parent?: IItemCategory) {}
}
