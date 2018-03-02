package com.dekinci.pricelist.pricelist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceListTest {
    private PriceList<MockedProduct> priceList;

    private

    @BeforeEach
    void setUp() {
        priceList = new PriceList<>();
    }

    @Test
    void simpleAddTest() {
        addNewProduct();
        assertEquals(0, priceList.getPrice(addNewProduct()).getInMinimalUnits());
    }

    @Test
    void sameProductExceptionAddTest() {
        MockedProduct product = new MockedProduct();
        priceList.add(product);

        assertThrows(IllegalArgumentException.class, () -> priceList.add(product));
    }

    @Test
    void differentIdsAddTest() {
        assertNotEquals(addNewProduct(), addNewProduct());
    }


    @Test
    void getByIdTest() {
        MockedProduct product = new MockedProduct();

        int id = priceList.add(product);
        assertEquals(product, priceList.get(id));
    }

    @Test
    void getByProductTest() {
        MockedProduct product = new MockedProduct();

        int id = priceList.add(product);
        assertEquals(id, priceList.get(product));
    }

    private int addNewProduct() {
        MockedProduct product = new MockedProduct();
        return priceList.add(product);
    }


    @Test
    void changePriceTest() {
        MockedPrice oldPrice = new MockedPrice(10);
        MockedPrice newPrice = new MockedPrice(100);

        MockedProduct product = new MockedProduct(oldPrice);
        int id = priceList.add(product);
        priceList.changePrice(id, newPrice);

        assertNotEquals(oldPrice, priceList.getPrice(id));
        assertEquals(newPrice, priceList.getPrice(id));
    }

    @Test
    void renameProduct() {
        MockedProduct product = new MockedProduct();
        String oldName = product.getName();
        String newName = "Best product of the world";

        int id = priceList.add(product);
        priceList.renameProduct(id, newName);

        String returnedNewName = priceList.get(id).getName();

        assertNotEquals(oldName, returnedNewName);
        assertEquals(newName, returnedNewName);
    }

    @Test
    void delete() {
        int id = addNewProduct();
        priceList.get(id);

        priceList.delete(id);
        assertThrows(IllegalArgumentException.class, () -> priceList.get(id));
    }

    @Test
    void calculateCost() {
        int amount = 10;
        int price = 100;
        int cost = price * amount;

        MockedProduct product = new MockedProduct(new MockedPrice(price));
        int id = priceList.add(product);
        assertEquals(cost, priceList.calculateCost(id, amount).getInMinimalUnits());
    }
}