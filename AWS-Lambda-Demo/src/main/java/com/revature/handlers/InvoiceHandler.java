package com.revature.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.revature.models.Order;
import com.revature.models.Response;

import java.net.HttpURLConnection;
import java.util.Optional;

public class InvoiceHandler implements RequestHandler<SNSEvent, Response> {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Response handleRequest(SNSEvent snsEvent, Context context) {
        LambdaLogger log = context.getLogger();

        try{
            Optional<SNSEvent.SNSRecord> snsRecord = snsEvent.getRecords().stream().findAny();
            if(snsRecord.isPresent()){
                final SNSEvent.SNS sns = snsRecord.get().getSNS();
                final String jsonMessage = sns.getMessage();
                final Order order = gson.fromJson(jsonMessage, Order.class);
                log.log("Order Object:{ " + order.toString() + "}");
            }
            return new Response("OK", HttpURLConnection.HTTP_OK);

        } catch (Exception e){

            return new Response("Something went wrong", HttpURLConnection.HTTP_INTERNAL_ERROR);
        }

    }
}
