package com.tony.sales.model;

import java.util.Objects;

public class Customer implements LineLayout {

	private static final long serialVersionUID = -7681233693502374522L;
	private String cnpj;
	private String name;
	private String businessArea;

	public Customer(String cnpj, String name, String businessArea) {
		this.setCnpj(cnpj);
		this.setName(name);
		this.setBusinessArea(businessArea);
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(final String cnpj) {
		this.cnpj = cnpj;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getBusinessArea() {
		return businessArea;
	}

	public void setBusinessArea(final String businessArea) {
		this.businessArea = businessArea;
	}

	@Override
	public LineLayoutType getLayoutType() {
		return LineLayoutType.CUSTOMER;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cnpj);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		return hashCode() == Customer.class.cast(obj).hashCode();
	}

	@Override
	public String toString() {
		return this.toJsonString();
	}

}
