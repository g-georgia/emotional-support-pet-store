package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier catFarm = new Supplier("Catfarm", "Cute and healthy cats.");
        supplierDataStore.add(catFarm);
        Supplier shelter = new Supplier("Shelter", "Best furry friends.");
        supplierDataStore.add(shelter);
        Supplier horseRanch = new Supplier("HorseRanch", "Beautiful stallions.");
        supplierDataStore.add(horseRanch);
        Supplier budapestZoo = new Supplier("BudapestZoo", "Home of exotic animals from all over the world.");
        supplierDataStore.add(budapestZoo);
        //setting up a new product category
        ProductCategory heartbreak = new ProductCategory("Heartbreak", "Depression", "A mental state when you are sad, for example after a breakup.");
        productCategoryDataStore.add(heartbreak);
        ProductCategory gift = new ProductCategory("Gift", "Celebration", "When you feel like giving a pet as a gift is a good idea. (But it's actually not!)");
        productCategoryDataStore.add(gift);

        //setting up products and printing it
        productDataStore.add(new Product("Cat", 49.9f, "USD", "Fantastic price. Helpful emotional support. A little bit selfish.", heartbreak, catFarm));
        productDataStore.add(new Product("Dog", 479, "USD", "A true friend. (S)he will love you the best. Amazing emotional support.", heartbreak, shelter));
        productDataStore.add(new Product("Bunny", 89, "USD", "A great furry friend. Good for support.", heartbreak, shelter));
        productDataStore.add(new Product("Horse", 55.8f, "USD", "Because why not?", gift, horseRanch));
        productDataStore.add(new Product("Capuchin Monkey", 45, "USD", "An exotic gift.", gift, budapestZoo));
        productDataStore.add(new Product("Walrus", 63.6f, "USD", "Gives good hugs.", gift, budapestZoo));
    }
}
