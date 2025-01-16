package com.vodafone.orderapi.configurations;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ExtractOrderAmountJobConfiguration {

    @JobWorker(type = "extractorderamount",autoComplete = false)
    public Map<String, Long> extractOrderAmount(final JobClient jobClient, final ActivatedJob activatedJob){

        Map<String,Object> receivedMap=activatedJob.getVariablesAsMap();
        long orderAmount=Long.parseLong(receivedMap.get("orderAmount").toString());
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
