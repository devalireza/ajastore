package com.aja.store.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ItemInstanceTransact.
 */
@Entity
@Table(name = "item_instance_transact")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ItemInstanceTransact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate;

    @NotNull
    @Size(max = 10)
    @Column(name = "transaction_type", length = 10, nullable = false)
    private String transactionType;

    @ManyToOne
    @JsonIgnoreProperties(value = "itemInstanceTransacts", allowSetters = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = "itemInstanceTransacts", allowSetters = true)
    private ItemInstance itemInstanceId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public ItemInstanceTransact deliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public ItemInstanceTransact transactionType(String transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public User getUser() {
        return user;
    }

    public ItemInstanceTransact user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ItemInstance getItemInstanceId() {
        return itemInstanceId;
    }

    public ItemInstanceTransact itemInstanceId(ItemInstance itemInstance) {
        this.itemInstanceId = itemInstance;
        return this;
    }

    public void setItemInstanceId(ItemInstance itemInstance) {
        this.itemInstanceId = itemInstance;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemInstanceTransact)) {
            return false;
        }
        return id != null && id.equals(((ItemInstanceTransact) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemInstanceTransact{" +
            "id=" + getId() +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", transactionType='" + getTransactionType() + "'" +
            "}";
    }
}
