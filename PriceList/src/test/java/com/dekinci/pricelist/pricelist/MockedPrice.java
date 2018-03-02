package com.dekinci.pricelist.pricelist;

import com.dekinci.pricelist.Price;

public class MockedPrice implements Price {
    private int price;

    MockedPrice(int price) {
        this.price = price;
    }

    @Override
    public Price multiply(int multiplier) {
        return new MockedPrice(price * multiplier);
    }

    @Override
    public int getInMinimalUnits() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof MockedPrice))
            return false;

        MockedPrice that = (MockedPrice) o;
        return price == that.price;
    }

    @Override
    public int hashCode() {
        return price;
    }
}
