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

        assertEquals(product, priceList.get(id).get());
        assertFalse(priceList.get(id + 1).isPresent());
    }

    @Test
    void getByProductTest() {
        MockedProduct product = new MockedProduct();

        int id = priceList.add(product);
        assertEquals(Integer.valueOf(id), priceList.getId(product).get());
        assertFalse(priceList.getId(new MockedProduct()).isPresent());
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
        assertTrue(priceList.changePrice(id, newPrice));

        assertNotEquals(oldPrice, priceList.getPrice(id));
        assertEquals(newPrice, priceList.getPrice(id));
    }

    @Test
    void changePriceWrongIdTest() {
        int id = addNewProduct();
        assertFalse(priceList.changePrice(id + 1, new MockedPrice(0)));
    }

    @Test
    void renameProduct() {
        MockedProduct product = new MockedProduct();
        String oldName = product.getName();
        String newName = "foo";

        int id = priceList.add(product);
        priceList.renameProduct(id, newName);

        String returnedNewName = priceList.get(id).get().getName();

        assertNotEquals(oldName, returnedNewName);
        assertEquals(newName, returnedNewName);
    }

    @Test
    void renameProductWithWrongIdTest() {
        int id = addNewProduct();
        assertFalse(priceList.renameProduct(id + 1, "bar"));
    }

    @Test
    void delete() {
        int id = addNewProduct();
        priceList.get(id);

        priceList.delete(id);
        assertFalse(priceList.get(id).isPresent());
    }

    @Test
    void calculateCost() {
        int amount = 10;
        int price = 100;
        int cost = price * amount;

        MockedProduct product = new MockedProduct(new MockedPrice(price));
        int id = priceList.add(product);
        assertEquals(cost, priceList.calculateCost(id, amount).get().getInMinimalUnits());
    }
}