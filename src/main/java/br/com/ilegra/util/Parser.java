package br.com.ilegra.util;

import static br.com.ilegra.constants.Constants.ITEMS_DELIMITER;
import static br.com.ilegra.constants.Constants.ITEM_PROP_DELIMITER;
import static br.com.ilegra.constants.Constants.LINE_DELIMITER;

import java.util.ArrayList;
import java.util.List;

import br.com.ilegra.model.Client;
import br.com.ilegra.model.Item;
import br.com.ilegra.model.Sale;
import br.com.ilegra.model.Salesman;

public class Parser {

	private Parser() {
	}

	public static Salesman parseSalesman(String salesmanLine) {
		String lineWithoutDataType = getLineWithoutType(salesmanLine);
		int firstDelimiter = getFirstDelimiter(lineWithoutDataType);
		int lastDelimiter = getLastDelimiter(lineWithoutDataType);

		String cpf = getFirstSubString(lineWithoutDataType, firstDelimiter);
		String name = getSecondSubString(lineWithoutDataType, firstDelimiter, lastDelimiter);
		Double salary = Double.valueOf(getThirdSubString(lineWithoutDataType, lastDelimiter));
		return new Salesman(name, cpf, salary);
	}

	public static Client parseClient(String clientLine) {
		String lineWithoutDataType = getLineWithoutType(clientLine);
		int firstDelimiter = getFirstDelimiter(lineWithoutDataType);
		int lastDelimiter = getLastDelimiter(lineWithoutDataType);

		String cnpj = getFirstSubString(lineWithoutDataType, firstDelimiter);
		String name = getSecondSubString(lineWithoutDataType, firstDelimiter, lastDelimiter);
		String businessArea = getThirdSubString(lineWithoutDataType, lastDelimiter);
		return new Client(name, cnpj, businessArea);
	}

	public static Sale parseSales(String saleLine) {
		String lineWithoutDataType = getLineWithoutType(saleLine);
		int firstDelimiter = getFirstDelimiter(lineWithoutDataType);
		int lastDelimiter = getLastDelimiter(lineWithoutDataType);

		String id = getFirstSubString(lineWithoutDataType, firstDelimiter);
		List<Item> itemList = getItemsList(getSecondSubString(lineWithoutDataType, firstDelimiter, lastDelimiter));
		String salesmanName = getThirdSubString(lineWithoutDataType, lastDelimiter);
		return new Sale(id, itemList, salesmanName);
	}

	private static String getLineWithoutType(String text) {
		return text.substring(text.indexOf(LINE_DELIMITER) + 1);
	}

	private static int getFirstDelimiter(String text) {
		return text.indexOf(LINE_DELIMITER);
	}

	private static int getLastDelimiter(String text) {
		return text.lastIndexOf(LINE_DELIMITER);
	}

	private static String getFirstSubString(String text, int firstDelimiter) {
		return text.substring(0, firstDelimiter);
	}

	private static String getSecondSubString(String text, int firstDelimiter, int lastDelimiter) {
		return text.substring(firstDelimiter + 1, lastDelimiter);
	}

	private static String getThirdSubString(String text, int lastDelimiter) {
		return text.substring(lastDelimiter + 1);
	}

	private static List<Item> getItemsList(String saleLine) {
		List<Item> itemList = new ArrayList<>();
		for (String item : saleLine.substring(1, saleLine.length() - 1).split(ITEMS_DELIMITER)) {
			String[] itemProperties = item.split(ITEM_PROP_DELIMITER);
			itemList.add(new Item(itemProperties[0], Integer.parseInt(itemProperties[1]),
					Double.parseDouble(itemProperties[2])));
		}
		return itemList;
	}
}
