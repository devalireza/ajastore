import { IUser } from 'app/core/user/user.model';

export interface IStore {
  id?: number;
  name?: string;
  user?: IUser;
}

export class Store implements IStore {
  constructor(public id?: number, public name?: string, public user?: IUser) {}
}
