package com.dekinci.pricelist;

public interface Product {

    void setName(String name);
    String getName();

    void setPrice(Price price);
    Price getPrice();
}
