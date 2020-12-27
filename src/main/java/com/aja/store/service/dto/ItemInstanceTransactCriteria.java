package com.aja.store.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.aja.store.domain.ItemInstanceTransact} entity. This class is used
 * in {@link com.aja.store.web.rest.ItemInstanceTransactResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /item-instance-transacts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ItemInstanceTransactCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter deliveryDate;

    private StringFilter transactionType;

    private LongFilter userId;

    private LongFilter itemInstanceIdId;

    public ItemInstanceTransactCriteria() {
    }

    public ItemInstanceTransactCriteria(ItemInstanceTransactCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.deliveryDate = other.deliveryDate == null ? null : other.deliveryDate.copy();
        this.transactionType = other.transactionType == null ? null : other.transactionType.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.itemInstanceIdId = other.itemInstanceIdId == null ? null : other.itemInstanceIdId.copy();
    }

    @Override
    public ItemInstanceTransactCriteria copy() {
        return new ItemInstanceTransactCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateFilter deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public StringFilter getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(StringFilter transactionType) {
        this.transactionType = transactionType;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getItemInstanceIdId() {
        return itemInstanceIdId;
    }

    public void setItemInstanceIdId(LongFilter itemInstanceIdId) {
        this.itemInstanceIdId = itemInstanceIdId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ItemInstanceTransactCriteria that = (ItemInstanceTransactCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(deliveryDate, that.deliveryDate) &&
            Objects.equals(transactionType, that.transactionType) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(itemInstanceIdId, that.itemInstanceIdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        deliveryDate,
        transactionType,
        userId,
        itemInstanceIdId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemInstanceTransactCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (deliveryDate != null ? "deliveryDate=" + deliveryDate + ", " : "") +
                (transactionType != null ? "transactionType=" + transactionType + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (itemInstanceIdId != null ? "itemInstanceIdId=" + itemInstanceIdId + ", " : "") +
            "}";
    }

}
