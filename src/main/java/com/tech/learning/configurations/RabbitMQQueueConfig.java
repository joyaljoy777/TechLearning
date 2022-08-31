package com.tech.learning.configurations;

import com.tech.learning.constants.QueueConstants;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import static com.tech.learning.constants.QueueConstants.*;

@Configuration
public class RabbitMQQueueConfig {

//    private static final String NOTIFICATION_QUE = "notificationQue";
//    private static final String TECH_LEARNING_QUE = "techLearningQue";

    @Bean
    public Queue myQueue() {
        return new Queue(QueueConstants.TECH_LEARNING_QUE, false);
    }

    @Bean
    public Declarables notificationAndTechLearningQueBindings() {
        Queue notificationQueue = new Queue(NOTIFICATION_QUE, false);
        Queue techLearningQueue = new Queue(TECH_LEARNING_QUE, false);
        FanoutExchange fanoutExchange = new FanoutExchange(NOT_AND_TECH_LEARN_FAN_OUT_EXCHANGE);

        return new Declarables(
                notificationQueue,
                techLearningQueue,
                fanoutExchange,
                BindingBuilder.bind(notificationQueue).to(fanoutExchange),
                BindingBuilder.bind(techLearningQueue).to(fanoutExchange));
    }

//    What is the difference between FanoutExchanges and topicExchanges
//    @Bean
//    public Declarables topicBindings() {
//        Queue topicQueue1 = new Queue(topicQueue1Name, false);
//        Queue topicQueue2 = new Queue(topicQueue2Name, false);
//
//        TopicExchange topicExchange = new TopicExchange(topicExchangeName);
//
//        return new Declarables(
//                topicQueue1,
//                topicQueue2,
//                topicExchange,
//                BindingBuilder
//                        .bind(topicQueue1)
//                        .to(topicExchange).with("*.important.*"),
//                BindingBuilder
//                        .bind(topicQueue2)
//                        .to(topicExchange).with("#.error"));
//    }


//    @Autowired
//    private AmqpAdmin amqpAdmin;
//
//    @PostConstruct()
//    public void createQueues() {
//        amqpAdmin.declareQueue(new Queue(QueueConstants.NOTIFICATION_QUE));
////        amqpAdmin.declareQueue(new Queue(NOTIFICATIONS_LIGHT));
////        amqpAdmin.declareQueue(new Queue(NOTIFICATIONS));
//    }
}
