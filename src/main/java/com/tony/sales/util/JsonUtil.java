package com.tony.sales.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class JsonUtil {

	private static final ObjectMapper OBJECT_MAPPER = JsonUtil.createObjectMapper();

	private JsonUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static String toJson(final Object data) {
		try {
			return OBJECT_MAPPER.writeValueAsString(data);
		} catch (final JsonProcessingException e) {
			return EMPTY;
		}
	}

	private static ObjectMapper createObjectMapper() {
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		return objectMapper;
	}

}
