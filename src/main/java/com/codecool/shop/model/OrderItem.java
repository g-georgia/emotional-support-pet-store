package com.codecool.shop.model;

public class OrderItem extends Product {
    public int quantity = 1;
    public float subtotalPrice;
    public OrderItem(String name, float defaultPrice, String currencyString, String description, ProductCategory productCategory, Supplier supplier) {
        super(name, defaultPrice, currencyString, description, productCategory, supplier);
        this.subtotalPrice = defaultPrice;
    }
    public OrderItem(Product product){
        super(product.name, product.getDefaultPrice(), product.getDefaultCurrency().toString(), product.description, product.getProductCategory(), product.getSupplier());
        subtotalPrice = product.getDefaultPrice();
    }
}
