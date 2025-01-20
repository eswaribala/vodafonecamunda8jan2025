package com.vodafone.deliveryapi.controllers;

import com.vodafone.deliveryapi.configurations.ProcessConstant;
import com.vodafone.deliveryapi.dtos.GenericResponse;
import io.camunda.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("db")
public class KafkaProcessController {

    @Autowired
    private ZeebeClient zeebeClient;

    //instantiate the bpmn
    @GetMapping("/v1.0")
    ResponseEntity<GenericResponse> startDBProcess(){

        this.zeebeClient
                .newCreateInstanceCommand()
                .bpmnProcessId(ProcessConstant.DB_BPMN_Process_Constant)
                .latestVersion()
                //.variables(variables)
                .send();

        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse("Instance created for"+ProcessConstant.DB_BPMN_Process_Constant));

    }

}
