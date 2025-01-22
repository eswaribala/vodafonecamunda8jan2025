package com.vodafone.orderapi.controllers;

import com.github.javafaker.Faker;
import com.vodafone.orderapi.configurations.ProcessConstant;
import com.vodafone.orderapi.dtos.GenericResponse;
import io.camunda.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/slack")
public class SlackProcessController {

    @Autowired
    private ZeebeClient zeebeClient;

    //instantiate the bpmn
    @GetMapping("/v1.0")
    ResponseEntity<GenericResponse> slackProcess(){
        Map<String,List<String>> userMap=new HashMap<>();
        Faker faker=new Faker();
        List<String> users=new ArrayList<>();
        for(int i=0;i<10;i++){
           users.add(faker.name().fullName());
        }
         userMap.put("usersList",users);
        this.zeebeClient
                .newCreateInstanceCommand()
                .bpmnProcessId(ProcessConstant.SLACK_BPMN_Process_Constant)
                .latestVersion()
                .variables(userMap)
                .send();

        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse("Instance created for"+ProcessConstant.SLACK_BPMN_Process_Constant));

    }

}
