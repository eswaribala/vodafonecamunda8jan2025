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
public class SlackUserJobConfiguration {

    @JobWorker(type = "identifyuser",autoComplete = false)
public Map<String,Boolean> refundReject(final JobClient jobClient, final ActivatedJob activatedJob){

        log.info("Slack Published the Mail");
        activatedJob.getVariablesAsMap().entrySet().stream().forEach(entryset->{
            log.info(entryset.getKey()+","+entryset.getValue());
        });

        Map<String,Boolean> refundMap=new HashMap<>();
        refundMap.put("delivered",true);
        //pub sub with bpmn error
        jobClient.newCompleteCommand(activatedJob.getKey())
                .variables(refundMap)
                .send()
                .exceptionally((throwable)->{
                    throw new RuntimeException("Job not found");
                });
        return refundMap;

}


}




