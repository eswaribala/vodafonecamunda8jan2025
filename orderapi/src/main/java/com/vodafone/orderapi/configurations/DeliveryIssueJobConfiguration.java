package com.vodafone.orderapi.configurations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vodafone.orderapi.models.Order;
import com.vodafone.orderapi.models.OrderStatus;
import com.vodafone.orderapi.services.OrderDao;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class DeliveryIssueJobConfiguration {

    @Autowired
    private OrderDao orderDao;
    @JobWorker(type = "deliveryissue", autoComplete = false)
    public Map<String,Boolean> processPayment(final JobClient jobClient, final ActivatedJob activatedJob) throws JsonProcessingException {


         Map<String,Boolean> orderMap=new HashMap<>();
         orderMap.put("deliveryStatus",false);

        jobClient.newCompleteCommand(activatedJob.getKey())
                .variables(orderMap)
                .send()
                .exceptionally((throwable)->{
                    throw new RuntimeException("Job not found");
                });
        return orderMap;


    }


}
