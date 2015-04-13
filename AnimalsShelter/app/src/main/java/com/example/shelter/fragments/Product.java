package com.example.shelter.fragments;

/**
 * Created by Дмитрий on 23.03.2015.
 */
public class Product {
    String name;
    String breed; // Порода
    String love;
    String dnlove;
    String age;
    String numberClient;
    int image;
    int imageCall;


    public Product(String describe, String breed, String love, String dnlove, String age, String numberClient, int image,int imageCall) {
        this.name = describe;
        this.image = image;
        this.breed = breed;
        this.love = love;
        this.dnlove = dnlove;
        this.age = age;
        this.numberClient = numberClient;
        this.imageCall = imageCall;
    }
}
