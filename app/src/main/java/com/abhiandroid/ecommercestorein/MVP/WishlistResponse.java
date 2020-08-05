package com.abhiandroid.ecommercestorein.MVP;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WishlistResponse {

    private String success;
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Product> getProducts() {
        return product;
    }

    public void setProducts(List<Product> products) {
        this.product = products;
    }

    private List<Product> product = null;

}