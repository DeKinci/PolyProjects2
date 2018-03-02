package com.dekinci.pricelist.pricelist;

import com.dekinci.pricelist.Price;
import com.dekinci.pricelist.Product;

public class MockedProduct implements Product {
    private static int productCounter = 1;

    private static String generateName() {
        return "MockedProduct #" + productCounter++;
    }

    private String name;
    private Price price;

    MockedProduct() {
        this(generateName());
    }

    MockedProduct (Price price) {
        this(generateName(), price);
    }

    MockedProduct (String name) {
        this(name, new MockedPrice(0));
    }

    MockedProduct(String name, Price price) {
        setName(name);
        setPrice(price);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Price getPrice() {
        return price;
    }
}
