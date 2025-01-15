package com.vodafone.orderapi.configurations;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class PaymentJobConfiguration {
    @JobWorker(type = "paymentgateway", autoComplete = false)
    Map<String,Boolean> processPayment(final JobClient jobClient, final ActivatedJob activatedJob){

        Map<String,Object> receivedData=activatedJob.getVariablesAsMap();
        receivedData.entrySet().stream().forEach(entrySet->{
            log.info(entrySet.getKey()+","+entrySet.getValue());
        });

        Map<String,Boolean> paymentStatusMap=new HashMap<>();

        if(receivedData.get("paymentMethod").toString().equals("Credit Card"))
            paymentStatusMap.put("paymentStatus",true);
        else
            paymentStatusMap.put("paymentStatus",false);

        jobClient.newCompleteCommand(activatedJob.getKey())
                .variables(paymentStatusMap)
                .send()
                .exceptionally((throwable)->{
                    throw new RuntimeException("Job not found");
                });
        return paymentStatusMap;


    }


}
