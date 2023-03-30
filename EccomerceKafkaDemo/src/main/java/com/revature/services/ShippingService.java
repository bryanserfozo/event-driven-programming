package com.revature.services;

import com.revature.models.Item;
import com.revature.models.Order;
import com.revature.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShippingService {

    private static final Logger logger = LoggerFactory.getLogger(ShippingService.class);

    private static List<User> users = new ArrayList<>();

    static {
        User user = new User("Bryan", "Serfozo", "bryan.serfozo@revature.com", "123 Example Street");
        users.add(user);
    }

    @KafkaListener(topics = "users")
    public void updateUsers(User user){

        for (User sampledUser: users){
            if (sampledUser.getEmail().equals(user.getEmail())){
                users.remove(sampledUser);
                users.add(user);
                break;
            }
        }
    }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "orders", groupId = "different-id")
    public void generateShippingLabel(Order order){
        logger.info("Shipping service receives order: " + order);
        String label = "Ship the following items: \n";
        for (Item item: order.getCart()){
            label += item.getName() + " | Quantity: " + item.getQuantity() + "\n";
        }
        for(User user: users){
            if (user.getEmail().equals(order.getEmail())){
                label += "To the following person: " +
                        user.getFirstName() + " " + user.getLastName() +
                        " @ " + user.getAddress();
            }
        }


        this.kafkaTemplate.send("factory", label);

    }


}