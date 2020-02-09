package br.com.ilegra.constants;

import java.util.Arrays;

public enum DataType {

	SALESMAN("001"), CLIENT("002"), SALE("003"), INVALID("INVALID");

	private final String dataTypeName;

    public static DataType parse(String text) {
    	String code = (text.length() >= 3) ? text.substring(0, 3) : DataType.INVALID.dataTypeName;
    	return Arrays.stream(values())
    			.filter(dataType -> dataType.dataTypeName.equals(code))
    			.findFirst()
    			.orElse(INVALID);
    }
	
	DataType(final String text) {
        this.dataTypeName = text;
    }

	@Override
    public String toString() {
        return dataTypeName;
    }
}
