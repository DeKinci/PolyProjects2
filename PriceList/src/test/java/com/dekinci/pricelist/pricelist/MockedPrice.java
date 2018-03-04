package com.dekinci.pricelist.pricelist;

import com.dekinci.pricelist.Price;

import java.util.concurrent.atomic.AtomicInteger;

public class MockedPrice implements Price {
    private final AtomicInteger price = new AtomicInteger();

    MockedPrice(int price) {
        this.price.set(price);
    }

    @Override
    public Price multiply(int multiplier) {
        return new MockedPrice(price.get() * multiplier);
    }

    @Override
    public int getInMinimalUnits() {
        return price.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof MockedPrice))
            return false;

        MockedPrice that = (MockedPrice) o;
        return price.equals(that.price);
    }

    @Override
    public int hashCode() {
        return price.get();
    }
}
