package com.tony.sales.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Salesman implements LineLayout {

	private static final long serialVersionUID = -5916060185007373371L;
	private String cpf;
	private String name;
	private BigDecimal salary;

	public Salesman(String cpf, String name, BigDecimal salary) {
		this.setCpf(cpf);
		this.setName(name);
		this.setSalary(salary);
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	@Override
	public LineLayoutType getLineLayoutType() {
		return LineLayoutType.SALESMAN;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		return hashCode() == Salesman.class.cast(obj).hashCode();
	}

	@Override
	public String toString() {
		return this.toJsonString();
	}

}
