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
        Supplier catFarm = new Supplier("Cat Farm", "Cute and healthy cats.");
        supplierDataStore.add(catFarm);
        Supplier shelter = new Supplier("Shelter", "Best furry friends.");
        supplierDataStore.add(shelter);
        Supplier horseRanch = new Supplier("HorseRanch", "Beautiful stallions.");
        supplierDataStore.add(horseRanch);
        Supplier budapestZoo = new Supplier("Budapest Zoo", "Home of exotic animals from all over the world.");
        supplierDataStore.add(budapestZoo);
        Supplier arctic = new Supplier("Arctic", "Home of polar bears.");
        supplierDataStore.add(arctic);
        Supplier antarctic = new Supplier("Antarctic", "Home of penguins.");
        supplierDataStore.add(antarctic);
        Supplier china = new Supplier("China", "Home of Mao Ce.");
        supplierDataStore.add(china);

        //setting up a new product category
        ProductCategory heartbreak = new ProductCategory("Heartbreak", "Depression", "A mental state when you are sad, for example after a breakup.");
        productCategoryDataStore.add(heartbreak);
        ProductCategory gift = new ProductCategory("Gift", "Celebration", "When you feel like giving a pet as a gift is a good idea. (But it's actually not!)");
        productCategoryDataStore.add(gift);
        ProductCategory anxiety = new ProductCategory("Anxiety", "Mental Health", "A serious disorder that requires a little furry friend.");
        productCategoryDataStore.add(anxiety);
        ProductCategory OCD = new ProductCategory("OCD","Mental Health", "A serious maniac disorder.");
        productCategoryDataStore.add(OCD);
        ProductCategory nervousBreakdown = new ProductCategory("Nervous Breakdown","Mental Health", "A serious mental issue.");
        productCategoryDataStore.add(nervousBreakdown);

        //setting up products and printing it
        productDataStore.add(new Product("Cat", 49.9f, "USD", "Fantastic price. Helpful emotional support. A little bit selfish.", heartbreak, catFarm));
        productDataStore.add(new Product("Dog", 47.9f, "USD", "A true friend. (S)he will love you the best. Amazing emotional support.", heartbreak, shelter));
        productDataStore.add(new Product("Bunny", 89, "USD", "A great furry friend. Good for support.", heartbreak, shelter));
        productDataStore.add(new Product("Horse", 55.8f, "USD", "Because why not?", gift, horseRanch));
        productDataStore.add(new Product("Capuchin Monkey", 45, "USD", "An exotic gift.", gift, budapestZoo));
        productDataStore.add(new Product("Walrus", 63.6f, "USD", "Gives good hugs.", gift, budapestZoo));
        productDataStore.add(new Product("Panda",98.9f,"USD","A big, friendly fluff. The monochrome version.", anxiety, china));
        productDataStore.add(new Product("Red Panda", 87.99f,"USD","It's like a panda, but red, smaller, pointy ears, fluffy tail. Great listener.", anxiety, budapestZoo));
        productDataStore.add(new Product("Sloth", 55.99f,"USD","The chillest little dude on Earth. Will help you calm down.", nervousBreakdown, budapestZoo));
        productDataStore.add(new Product("Polar Bear", 112.6f,"USD","A chilly dude who enjoys rolling in the snow. Good at keeping you organized.", OCD, arctic));
        productDataStore.add(new Product("Penguin", 47.9f,"USD","A fluffy, hugable, loyal bird. Has attention to details.", OCD, antarctic));
        productDataStore.add(new Product("Seal", 78.99f,"USD","A cool dude with a cute face. Great at water sports.", OCD, arctic));
        productDataStore.add(new Product("Otter", 87.99f,"USD","There is just no any OTTER than this little guy. Will fill the hole in your heart for sure!", heartbreak, shelter));
        productDataStore.add(new Product("Micro Pig", 30.99f,"USD","It's like a pig but very small. Will heal your body, mind and soul at once.", nervousBreakdown, shelter));
        productDataStore.add(new Product("Corgi", 44.99f,"USD","His fluffy butt will make you forget all your problems.", anxiety, shelter));
    }
}
