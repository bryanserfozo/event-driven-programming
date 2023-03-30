package com.revature.handlers;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OrderHandler {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    String order = "{\n" +
            "    \"email\": \"bryan.serfozo@revature.com\",\n" +
            "    \"cart\":[\n" +
            "        {\"name\": \"Baseball Cap\",\n" +
            "        \"price\": 57.38,\n" +
            "        \"quantity\": 1},\n" +
            "        {\"name\": \"Machine Learning Book\",\n" +
            "        \"price\": 164,\n" +
            "        \"quantity\": 2}\n" +
            "    ]\n" +
            "}";

    public void handleRequest(){
        AmazonSNS client = AmazonSNSClientBuilder.standard().build();

        client.publish("arn", "order");
    }
}
