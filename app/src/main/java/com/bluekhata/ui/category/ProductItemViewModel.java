package com.bluekhata.ui.category;

import androidx.databinding.ObservableField;

import com.bluekhata.data.model.other.Product;

public class ProductItemViewModel {
    public Product product;

    public ProductItemViewModel(){
    }

    public void setProduct(Product product){
        this.product = product;
    }

    public String getTitle(){
        return product.getTitle();
    }

    public String getUrl(){
        return product.getUrl();
    }

    public String getOfferAmount(){
        return String.valueOf(product.getOfferAmount());
    }

    public String getMrpAmount(){
        return String.valueOf(product.getMrpAmount());
    }

    public String getDiscountPercentage(){
        return String.valueOf(product.getOffPercentage());
    }

    public String getIconText(){
        if (product.getUrl() == null || product.getUrl().isEmpty()){
            return product.getTitle();
        }
        return "";
    }
}
