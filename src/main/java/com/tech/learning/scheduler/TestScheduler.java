package com.tech.learning.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.tech.learning.constants.QueueConstants.*;

@Component
public class TestScheduler {

    public static final Logger logger = LoggerFactory.getLogger(TestScheduler.class);

    private static int count = 0;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${spring.application.instance_id}")
    private String instanceId;

//    https://www.cloudamqp.com/blog/part1-rabbitmq-for-beginners-what-is-rabbitmq.html
//    Very good tutorial for RabbitMQ starting

//    # How does spring connect without this rabbitMq configs from properties file?

//    What is the difference between spring scheduled tasks and spring batch jobs?

//    2 aspects which i can think of: afaik when a job-run fails, in 2. run, it will run with the same job parameters..
//    at least you can configure this i think. and this kind of error situations which you can configure more easily than writing all in code in the same place manually (your scheduled method).
//    Secondly, maybe batch gives a structure to your code when you also have to read your data from somewhere, and write somewhere...
//    batch has some kind of reader, processor, writer schema.. Also some automatically created database tables (BATCH_JOB_INSTANCE) and batch job results.. like when the job started etc...
//
//    Edit: More Reasons for a batch: large amount of data, Transaction management, Chunk based processing,
//    Declarative I/O, Start/Stop/Restart, Retry/Skip, Web based administration interface.

    //  Scheduled to run at every two minutes
    //  Spring cron and UNIX cron are different
//    @Scheduled(cron = "*/30 * * * * *")
    public void scheduleTaskUsingCronExpression() {

        String threadName = Thread.currentThread().getName();
        String msg = "Message from Thread: " + instanceId + "-" + threadName + "-" + count;
        logger.info("Started scheduler in TestScheduler: {}", msg);
        amqpTemplate.convertAndSend(TECH_LEARNING_QUE, msg);
        count++;
    }

//    What will happen if queue will go down?


//    This will broadcast to both queues(notification and techLearn)
//    Here also sync between multiple instance are ensured
    @Scheduled(cron = "*/30 * * * * *")
    public void scheduleTaskUsingBindingExchange() {

        String threadName = Thread.currentThread().getName();
        String msg = "Message from Binding Exchange testing: " + instanceId + "-" + threadName + "-" + count;
        logger.info("Started scheduler in Binding Exchange: {}", msg);
        amqpTemplate.convertAndSend(NOT_AND_TECH_LEARN_FAN_OUT_EXCHANGE, "",msg);
        count++;
    }
}
