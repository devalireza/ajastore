export interface IMenuItem {
  id?: number;
  name?: string;
  entityName?: string;
  parent?: IMenuItem;
}

export class MenuItem implements IMenuItem {
  constructor(public id?: number, public name?: string, public entityName?: string, public parent?: IMenuItem) {}
}
