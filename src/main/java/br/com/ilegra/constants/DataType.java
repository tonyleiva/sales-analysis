package br.com.ilegra.constants;

import java.util.HashMap;

public enum DataType {

	SALESMAN("001"), CLIENT("002"), SALE("003"), INVALID(null);

	private final String dataTypeName;
	private static HashMap<String, DataType> dataTypeMap;
    static {
    	dataTypeMap = new HashMap<>();
        for (DataType type : DataType.values()) {
            dataTypeMap.put(type.toString(), type);
        }
    }

    public static DataType parse(String text) {
    	text = (text.length() >= 3) ? text.substring(0, 3) : null;
        return dataTypeMap.get(text) == null ? INVALID : dataTypeMap.get(text);
    }
	
	DataType(final String text) {
        this.dataTypeName = text;
    }

	@Override
    public String toString() {
        return dataTypeName;
    }
}
