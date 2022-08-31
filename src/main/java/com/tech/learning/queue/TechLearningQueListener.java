package com.tech.learning.queue;

import com.tech.learning.constants.QueueConstants;
import com.tech.learning.controllers.AMQPTestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.tech.learning.constants.QueueConstants.*;

@Component
public class TechLearningQueListener {

    public static final Logger logger = LoggerFactory.getLogger(TechLearningQueListener.class);

//    Check / Find the answer for this
//    https://stackoverflow.com/questions/60263819/spring-rabbitmq-consumer-multiple-instances

//    Concurrency / Sync between multiple instances is ensured in que
//    changed the port and instanceId in properties file

    // Here all the constants in the class are imported statically
    // Instead of the string argument, create a custom AMQP object which is a domain modal
    @RabbitListener(queues = TECH_LEARNING_QUE)
    public void techLearningListener(String msg) throws InterruptedException {
        Thread.sleep(5000);
        logger.info("Received message in Tech Learning Que: {}", msg);
        System.out.println("Received message in Tech Learning Que : " + TECH_LEARNING_QUE + " & the message is: " + msg);
    }

    @RabbitListener(queues = NOTIFICATION_QUE)
    public void notificationListener(String msg) throws InterruptedException {
        Thread.sleep(5000);
        logger.info("Received message in Notification Que: {}", msg);
        System.out.println("Received message in Notification Que : " + NOTIFICATION_QUE + " & the message is: " + msg);
    }
}
