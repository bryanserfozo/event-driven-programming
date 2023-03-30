package com.revature.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.revature.models.Item;
import com.revature.models.Order;
import com.revature.models.Response;
import com.revature.models.User;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShippingHandler implements RequestHandler<SNSEvent, Response> {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static List<User> users = new ArrayList<>();

    static {
        User user = new User("Bryan", "Serfozo", "bryan.serfozo@revature.com", "123 Example Street");
        users.add(user);
    }

    @Override
    public Response handleRequest(SNSEvent snsEvent, Context context) {
        LambdaLogger logger = context.getLogger();

        try {
            Optional<SNSEvent.SNSRecord> snsRecord = snsEvent.getRecords().stream().findAny();
            if(snsRecord.isPresent()) {
                SNSEvent.SNS sns = snsRecord.get().getSNS();
                String jsonMessage = sns.getMessage();
                Order order = gson.fromJson(jsonMessage, Order.class);
                String label = "Ship the following items: \n";
                for (Item item : order.getCart()) {
                    label += item.getName() + " | Quantity: " + item.getQuantity() + "\n";
                }
                for (User user : users) {
                    if (user.getEmail().equals(order.getEmail())) {
                        label += "To the following person: " +
                                user.getFirstName() + " " + user.getLastName() +
                                " @ " + user.getAddress();
                    }
                }

                logger.log(label);
            }
            return new Response("OK", HttpURLConnection.HTTP_OK);
        }
        catch (Exception e){
            return new Response("Something went wrong", HttpURLConnection.HTTP_INTERNAL_ERROR);
        }
    }
}
