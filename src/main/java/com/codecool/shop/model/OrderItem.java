package com.codecool.shop.model;

public class OrderItem extends Product {
    public int OrderItemId;
    public int quantity = 1;
    public float subtotalPrice;
    public OrderItem(String name, float defaultPrice, String currencyString, String description, ProductCategory productCategory, Supplier supplier) {
        super(0, name, defaultPrice, currencyString, description, productCategory, supplier);
        this.subtotalPrice = defaultPrice;
    }
    public OrderItem(Product product){
        super(product.getId(), product.name, product.getDefaultPrice(), product.getDefaultCurrency().toString(), product.description, product.getProductCategory(), product.getSupplier());
        subtotalPrice = product.getDefaultPrice();
        OrderItemId =product.getId();
    }

    public void increaseOrder(){
        this.quantity += 1;
        this.subtotalPrice = this.quantity*this.getDefaultPrice();
    }

    public void decreaseOrder(){
        this.quantity -= 1;
        this.subtotalPrice = this.quantity*this.getDefaultPrice();
    }
}
