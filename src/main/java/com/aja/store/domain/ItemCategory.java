package com.aja.store.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ItemCategory.
 */
@Entity
@Table(name = "item_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ItemCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @ManyToOne
    @JsonIgnoreProperties(value = "itemCategories", allowSetters = true)
    private ItemCategory parent;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ItemCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemCategory getParent() {
        return parent;
    }

    public ItemCategory parent(ItemCategory itemCategory) {
        this.parent = itemCategory;
        return this;
    }

    public void setParent(ItemCategory itemCategory) {
        this.parent = itemCategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemCategory)) {
            return false;
        }
        return id != null && id.equals(((ItemCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
