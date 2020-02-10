package br.com.ilegra.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class SaleTest {

    @Test
    void testTotalSaleAmount() {
        List<Item> itemList = new ArrayList<Item>();
        itemList.add(new Item("01",10, 10.0));
        itemList.add(new Item("02", 2, 0.50));
        Sale sale = new Sale("00", itemList, "salesman");
        assertTrue(sale.getTotalSaleAmount() == (101.0));
    }

    @Test
    void testTotalSaleAmountNull() {
        Sale sale = new Sale(null, null, null);
        assertTrue(sale.getTotalSaleAmount() == (0d));
    }

}
