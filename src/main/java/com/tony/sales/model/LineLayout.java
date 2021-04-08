package com.tony.sales.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tony.sales.util.JsonSerializable;

public interface LineLayout extends JsonSerializable {

	@JsonIgnore
	LineLayoutType getLineLayoutType();

}
