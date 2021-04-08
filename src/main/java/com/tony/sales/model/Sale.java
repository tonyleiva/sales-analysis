package com.tony.sales.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class Sale implements LineLayout {

	private static final long serialVersionUID = -8540416408985839612L;
	private String saleId;
	private List<Item> itemList;
	private String salesmanName;

	public Sale(String saleId, List<Item> itemList, String salesmanName) {
		this.setSaleId(saleId);
		this.setItemList(itemList);
		this.setSalesmanName(salesmanName);
	}

	public String getSaleId() {
		return saleId;
	}

	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	public String getSalesmanName() {
		return salesmanName;
	}

	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}

	@JsonIgnore
	public BigDecimal getTotalSaleAmount() {
		return (itemList == null) ? BigDecimal.ZERO : itemList.stream()
				.map(Item ::getTotal)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Override
	public LineLayoutType getLineLayoutType() {
		return LineLayoutType.SALE;
	}

	@Override
	public int hashCode() {
		return Objects.hash(saleId);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (this.getClass() != other.getClass())
			return false;
		return hashCode() == Sale.class.cast(other).hashCode();
	}

	@Override
	public String toString() {
		return this.toJsonString();
	}

}
