package br.com.ilegra.constants;

import java.util.HashMap;

public enum DataType {

	SALESMAN("001"), CLIENT("002"), SALE("003");

	private final String dataTypeName;
	private static HashMap<String, DataType> dataTypeMap;
    static {
    	dataTypeMap = new HashMap<>();
        for (DataType type : DataType.values()) {
            dataTypeMap.put(type.toString(), type);
        }
    }

    public static DataType parse(String s) {
        return dataTypeMap.get(s);
    }
	
	DataType(final String text) {
        this.dataTypeName = text;
    }

	@Override
    public String toString() {
        return dataTypeName;
    }
}
