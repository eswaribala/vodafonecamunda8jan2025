package com.vodafone.orderapi.configurations;

import com.github.javafaker.Faker;
import com.vodafone.orderapi.models.Order;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class StoreOrderJobConfiguration {

    @JobWorker(type = "storeorder",autoComplete = false)
    public Map<String, Order> saveOrder(final JobClient jobClient, final ActivatedJob activatedJob){
        Faker faker=new Faker();
        Map<String,Object> receivedData=activatedJob.getVariablesAsMap();
        receivedData.entrySet().stream().forEach(entrySet->{
            System.out.println(entrySet.getKey()+","+entrySet.getValue());
        });

        Map<String, Order> orderMap=new HashMap<>();
        orderMap.put("order",new Order(faker.random().nextInt(1,100000),LocalDate.now(),faker.random().nextInt(1,100)));

        jobClient.newCompleteCommand(activatedJob.getKey())
                .variables(orderMap)
                .send()
                .exceptionally((throwable)->{
                    throw new RuntimeException("Job not found");
                });
      return orderMap;

    }

}
