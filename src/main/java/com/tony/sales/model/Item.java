package com.tony.sales.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tony.sales.util.JsonSerializable;

import java.math.BigDecimal;
import java.util.Objects;

public class Item implements JsonSerializable {

	private static final long serialVersionUID = -6412309004296862660L;
	private String id;
	private Integer quantity;
	private BigDecimal price;

	public Item(String id, Integer quantity, BigDecimal price) {
		this.setId(id);
		this.setQuantity(quantity);
		this.setPrice(price);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@JsonIgnore
	public BigDecimal getTotal() {
		return getPrice().multiply(BigDecimal.valueOf(getQuantity()));
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		return hashCode() == Item.class.cast(obj).hashCode();
	}

	@Override
	public String toString() {
		return this.toStringJson();
	}

}
