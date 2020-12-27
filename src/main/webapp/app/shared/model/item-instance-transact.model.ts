import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IItemInstance } from 'app/shared/model/item-instance.model';

export interface IItemInstanceTransact {
  id?: number;
  deliveryDate?: Moment;
  transactionType?: string;
  user?: IUser;
  itemInstanceId?: IItemInstance;
}

export class ItemInstanceTransact implements IItemInstanceTransact {
  constructor(
    public id?: number,
    public deliveryDate?: Moment,
    public transactionType?: string,
    public user?: IUser,
    public itemInstanceId?: IItemInstance
  ) {}
}
