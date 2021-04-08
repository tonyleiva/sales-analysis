package com.tony.sales.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Parser {

	protected Matcher getMatcher(final Pattern pattern, final String line) {
		return pattern.matcher(line);
	}

	protected static Pattern getPattern(final String regex) {
		return Pattern.compile(regex, Pattern.CANON_EQ);
	}

}
