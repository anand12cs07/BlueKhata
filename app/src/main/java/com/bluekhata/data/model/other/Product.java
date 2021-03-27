package com.bluekhata.data.model.other;

import com.bluekhata.data.model.db.Recurrence;

import java.util.ArrayList;

public class Product {
    private String id;
    private String url;
    private String title;
    private int offerAmount;
    private int mrpAmount;
    private int offPercentage;

    public Product(String id, String url, String title, int offerAmount, int mrpAmount) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.offerAmount = offerAmount;
        this.mrpAmount = mrpAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOfferAmount() {
        return offerAmount;
    }

    public void setOfferAmount(int offerAmount) {
        this.offerAmount = offerAmount;
    }

    public int getMrpAmount() {
        return mrpAmount;
    }

    public void setMrpAmount(int mrpAmount) {
        this.mrpAmount = mrpAmount;
    }

    public int getOffPercentage() {
        if (mrpAmount - offerAmount > 0) {
            return (mrpAmount - offerAmount) * 100 / mrpAmount;
        }
        return 0;
    }

    public static ArrayList<Product> getInitialProducts() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("None", null, "Patanjali Musturd Oil 1L", 145, 155));
        products.add(new Product("None", null, "Musturd Oil 1L", 165, 175));
        products.add(new Product("None", "https://5.imimg.com/data5/XI/GG/QO/SELLER-12341595/1-ltr-pouch-kachi-ghani-mustard-oil-500x500.jpg", "hawai ghoda Oil 1L", 145, 155));
        products.add(new Product("None", "https://app.digiboxx.com/public/1246526", "Curd 1L", 145, 155));
        products.add(new Product("None", "https://app.digiboxx.com/public/1246526", "Musturd Oil 1L", 145, 155));
        products.add(new Product("None", "https://app.digiboxx.com/public/1246526", "Patanjali Oil 1L", 145, 155));
        products.add(new Product("None", "https://app.digiboxx.com/public/1246526", "Patanjali Musturd 1L", 145, 155));
        products.add(new Product("None", "https://app.digiboxx.com/public/1246526", "Patanjali Musturd Oil", 145, 155));
        products.add(new Product("None", "https://app.digiboxx.com/public/1246526", "1L", 45, 15));
        products.add(new Product("None", "https://app.digiboxx.com/public/1246526", "Patanjali Musturd", 145, 155));
        return products;
    }
}
