package com.tech.learning.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.tech.learning.constants.QueueConstants.*;

@RestController
@RequestMapping("/amqp")
public class AMQPTestController {

    public static final Logger logger = LoggerFactory.getLogger(AMQPTestController.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping(path = "/test/{msg}")
    public ResponseEntity<String> testQueue(@PathVariable("msg") String msg) {
        logger.info("Received message in controller: {}", msg);
        amqpTemplate.convertAndSend(TECH_LEARNING_QUE, msg);
        return ResponseEntity.ok(msg);
    }

}
