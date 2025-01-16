package com.vodafone.orderapi.configurations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vodafone.orderapi.models.Order;
import com.vodafone.orderapi.models.OrderStatus;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ExtractOrderAmountJobConfiguration {

    @JobWorker(type = "extractorderamount",autoComplete = false)
    public Map<String, Long> extractOrderAmount(final JobClient jobClient, final ActivatedJob activatedJob) throws JsonProcessingException {

        ObjectMapper objectMapper=new ObjectMapper();
        String variablesJson = activatedJob.getVariables();

        // Deserialize variables to a Map
        Map<String, Object> variables = objectMapper.readValue(variablesJson, new TypeReference<>() {});

        // Read the "items" variable (array of JSON objects)
        Map<String, Object> item = (Map<String, Object>) variables.get("order");

        long orderAmount=Long.parseLong(item.get("orderAmount").toString());

        Map<String,Long> orderAmountMap=new HashMap<>();
        orderAmountMap.put("orderAmountCaptured",orderAmount);
        jobClient.newCompleteCommand(activatedJob.getKey())
                .variables(orderAmountMap)
                .send()
                .exceptionally((throwable)->{
                    throw new RuntimeException("Job not found");
                });
        return orderAmountMap;
    }
}
