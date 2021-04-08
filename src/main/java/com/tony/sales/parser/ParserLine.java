package com.tony.sales.parser;

import com.tony.sales.model.LineLayout;

public interface ParserLine {

	boolean isValid(final String line);
	LineLayout parse(final String line);

}
