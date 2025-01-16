package com.virtusa.bankingzeebeclient.controllers;

import com.virtusa.bankingzeebeclient.configurations.ProcessConstant;
import io.camunda.zeebe.client.ZeebeClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/processes")
@Slf4j
public class ProcessController {

    @Autowired
    private ZeebeClient zeebeClient;

    @GetMapping("/")
    public ResponseEntity<?> startProcess(){

        zeebeClient.newCreateInstanceCommand().bpmnProcessId(ProcessConstant.BPMN_PROCESS_ID)
                .latestVersion()
                .send();

        return ResponseEntity.status(HttpStatus.OK).body("Process Started..."+ProcessConstant.BPMN_PROCESS_ID);



    }
}
