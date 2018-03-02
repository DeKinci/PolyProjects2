package com.dekinci.pricelist.pricelist;

import com.dekinci.pricelist.Price;
import com.dekinci.pricelist.Product;

import java.util.*;

public class PriceList<P extends Product> {
    private HashMap<Integer, P> priceMap = new HashMap<>();
    private int id = 0;

    /**
     * @return id of the added product
     * @param product to add.
     * @throws IllegalArgumentException if the product already exists.
     */
    public int add(P product) throws IllegalArgumentException {
        while (priceMap.containsKey(id))
            id++;

        if (!priceMap.containsValue(product)) {
            priceMap.put(id, product);
            return id++;
        }
        throw new IllegalArgumentException("Product already exists");
    }

    /**
     * @throws IllegalArgumentException if id not found
     */
    public P get(int id) throws IllegalArgumentException {
        if (!priceMap.containsKey(id))
            throw new IllegalArgumentException("No product with such id!");
        return priceMap.get(id);
    }

    /**
     * @throws IllegalArgumentException if product not found
     */
    public int get(P product) throws IllegalArgumentException {
        Set<Map.Entry<Integer, P>> entries = priceMap.entrySet();
        for (Map.Entry<Integer, P> entry : entries)
            if (entry.getValue().equals(product))
                return entry.getKey();
        throw new IllegalArgumentException("No such product in price list!");
    }

    /**
     * Ignores if id not found
     */
    public void changePrice(int id, Price price) {
        Product product = priceMap.get(id);
        if (product != null)
            product.setPrice(price);
    }

    /**
     * Ignores if id not found
     */
    public void renameProduct(int id, String name) {
        Product product = priceMap.get(id);
        if (product != null)
            product.setName(name);
    }

    /**
     * Ignores if id not found
     */
    public void delete(int id) {
        priceMap.remove(id);
    }

    //Tests only!
    Price getPrice(int id) {
        return get(id).getPrice();
    }

    /**
     * @throws IllegalArgumentException if id not found
     */
    public Price calculateCost(int id, int amount) throws IllegalArgumentException {
        return get(id).getPrice().multiply(amount);
    }
}
