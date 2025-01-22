package com.virtusa.bankingzeebeclient.configurations;



import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Configuration
@Slf4j
public class JobConfiguration {
    //bean
    @JobWorker(type = "secretaccess",autoComplete = false)
    public HashMap<String,Object> applicationNoGenerator(final JobClient jobClient, ActivatedJob activatedJob){

        Map<String,Object> receivedMap=activatedJob.getVariablesAsMap();
        receivedMap.entrySet().stream().forEach(entrySet->{
            log.info(entrySet.getKey()+","+entrySet.getValue());
        });


        //generate loan application no
        HashMap<String,Object> map =new HashMap<>();
        map.put("applicationNo", new Random().nextInt(1000000));
        jobClient.newCompleteCommand(activatedJob.getKey())
                .variables(map).send().exceptionally(throwable -> {
                    throw new RuntimeException("Exception due to non available job");
                });
        return map;

    }
    @JobWorker(type = "activatepayment",autoComplete = false)
    public HashMap<String,Boolean> activatePayment(final JobClient jobClient, ActivatedJob activatedJob){

        Map<String,Object> receivedMap=activatedJob.getVariablesAsMap();
        receivedMap.entrySet().stream().forEach(entrySet->{
            log.info(entrySet.getKey()+","+entrySet.getValue());
        });


        //generate loan application no
        HashMap<String,Boolean> map =new HashMap<>();
        map.put("paymentstatus", true);
        jobClient.newCompleteCommand(activatedJob.getKey())
                .variables(map).send().exceptionally(throwable -> {
                    throw new RuntimeException("Exception due to non available job");
                });
        return map;

    }


    @JobWorker(type = "refund",autoComplete = false)
    public HashMap<String,Boolean> refundPayment(final JobClient jobClient, ActivatedJob activatedJob){

        Map<String,Object> receivedMap=activatedJob.getVariablesAsMap();
        receivedMap.entrySet().stream().forEach(entrySet->{
            log.info(entrySet.getKey()+","+entrySet.getValue());
        });


        //generate loan application no
        HashMap<String,Boolean> map =new HashMap<>();
        map.put("refundstatus", true);
        jobClient.newCompleteCommand(activatedJob.getKey())
                .variables(map).send().exceptionally(throwable -> {
                    throw new RuntimeException("Exception due to non available job");
                });
        return map;

    }


}
