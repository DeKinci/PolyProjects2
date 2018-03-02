package com.dekinci.pricelist;

public interface Price {
    Price multiply(int multiplier);

    int getInMinimalUnits();
}
