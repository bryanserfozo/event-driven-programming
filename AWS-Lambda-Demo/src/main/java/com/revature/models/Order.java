package com.revature.models;

import java.util.List;

public class Order {

    private String email;

    private List<Item> cart;

    public Order() {

    }

    public Order(String username, List<Item> cart) {
        this.email = username;
        this.cart = cart;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public List<Item> getCart() {
        return cart;
    }

    public void setCart(List<Item> cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "Order{" +
                "email='" + email + '\'' +
                ", cart=" + cart +
                '}';
    }
}