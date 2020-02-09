package br.com.ilegra.model;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

public class Sale {
	private String id;
	private List<Item> itemList;
	private String salesmanName;

	public Sale(String id, List<Item> itemList, String salesmanName) {
		this.id = id;
		this.itemList = itemList;
		this.salesmanName = salesmanName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Double getTotalSaleAmount() {
		Double totalSaleAmount = 0d;
		if (CollectionUtils.isNotEmpty(itemList)) {
			totalSaleAmount = itemList.stream().mapToDouble(item -> (item.getPrice() * item.getQuantity())).sum();
		}
		return totalSaleAmount;
	}
}
