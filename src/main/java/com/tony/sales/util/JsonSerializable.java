package com.tony.sales.util;

import java.io.Serializable;
import java.text.MessageFormat;

public interface JsonSerializable extends Serializable {

	default String toStringJson() {
		return MessageFormat.format("{0} -> [{1}]", this.getClass().getSimpleName(), JsonUtil.toJson(this));
	}

}
