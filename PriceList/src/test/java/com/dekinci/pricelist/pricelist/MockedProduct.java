package com.dekinci.pricelist.pricelist;

import com.dekinci.pricelist.Price;
import com.dekinci.pricelist.Product;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class MockedProduct implements Product {
    private static AtomicInteger productCounter = new AtomicInteger(1);

    private static String generateName() {
        return "MockedProduct #" + productCounter.getAndIncrement();
    }

    private AtomicReference<String> name = new AtomicReference<>();
    private AtomicReference<Price> price = new AtomicReference<>();

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
        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }

    public void setPrice(Price price) {
        this.price.set(price);
    }

    public Price getPrice() {
        return price.get();
    }
}
