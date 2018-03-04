package com.dekinci.pricelist.pricelist;

import com.dekinci.pricelist.Price;
import com.dekinci.pricelist.Product;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PriceList<P extends Product> {
    private Map<Integer, P> priceMap = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    /**
     * @return id of the added product
     * @throws IllegalArgumentException if the product already exists.
     */
    public int add(P product) throws IllegalArgumentException {
        while (priceMap.containsKey(id.get()))
            id.getAndIncrement();

        if (!priceMap.containsValue(product)) {
            priceMap.put(id.get(), product);
            return id.getAndIncrement();
        }
        throw new IllegalArgumentException("Product already exists");
    }

    public Optional<P> get(int id) {
        return Optional.ofNullable(priceMap.get(id));
    }

    public Optional<Integer> getId(P product) {
        Set<Map.Entry<Integer, P>> entries = priceMap.entrySet();
        for (Map.Entry<Integer, P> entry : entries)
            if (entry.getValue().equals(product))
                return Optional.of(entry.getKey());
        return Optional.empty();
    }

    /**
     * @return false if id not found
     */
    public boolean changePrice(int id, Price price) {
        Product product = priceMap.get(id);
        if (product != null) {
            product.setPrice(price);
            return true;
        }
        return false;
    }

    /**
     * @return false if id not found
     */
    public boolean renameProduct(int id, String name) {
        Product product = priceMap.get(id);
        if (product != null) {
            product.setName(name);
            return true;
        }
        return false;
    }

    /**
     * Ignores if id not found
     */
    public void delete(int id) {
        priceMap.remove(id);
    }

    //Tests only!
    Price getPrice(int id) {
        return get(id).get().getPrice();
    }

    /**
     * @throws IllegalArgumentException if id not found
     */
    public Optional<Price> calculateCost(int id, int amount) throws IllegalArgumentException {
        Optional<P> product = get(id);
        return product.isPresent() ? Optional.of(get(id).get().getPrice().multiply(amount)) : Optional.empty();
    }
}
