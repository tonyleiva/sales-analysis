package br.com.ilegra.properties;

import static br.com.ilegra.properties.DefaultValues.ITEMS_DELIMITER;
import static br.com.ilegra.properties.DefaultValues.ITEM_PROP_DELIMITER;
import static br.com.ilegra.properties.DefaultValues.LINE_DELIMITER;

public class Delimiter {

	private String lineProperties;
	private String items;
	private String itemProperties;

	public Delimiter() {
		lineProperties = LINE_DELIMITER;
		items = ITEMS_DELIMITER;
		itemProperties = ITEM_PROP_DELIMITER;
	}

	public String getLineProperties() {
		return lineProperties;
	}

	public void setLineProperties(String lineProperties) {
		this.lineProperties = lineProperties;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String getItemProperties() {
		return itemProperties;
	}

	public void setItemProperties(String itemProperties) {
		this.itemProperties = itemProperties;
	}

	@Override
	public String toString() {
		return "Delimiter [lineProperties='" + lineProperties + "', items='" + items + "', itemProperties='"
				+ itemProperties + "']";
	}

}
