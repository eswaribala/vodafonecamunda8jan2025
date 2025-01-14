package com.vodafone.orderapi.controllers;

import com.vodafone.orderapi.configurations.ProcessConstant;
import com.vodafone.orderapi.dtos.GenericResponse;
import io.camunda.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("orders")
public class OrderProcessController {

    @Autowired
    private ZeebeClient zeebeClient;

    @PostMapping("/v1.0")
    ResponseEntity<GenericResponse> startOrderProcess(@RequestBody Map<String,Object> variables){

        this.zeebeClient
                .newCreateInstanceCommand()
                .bpmnProcessId(ProcessConstant.Order_BPMN_Process_Constant)
                .latestVersion()
                .variables(variables)
                .send();

         return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse("Instance created for"+ProcessConstant.Order_BPMN_Process_Constant));

    }


}
