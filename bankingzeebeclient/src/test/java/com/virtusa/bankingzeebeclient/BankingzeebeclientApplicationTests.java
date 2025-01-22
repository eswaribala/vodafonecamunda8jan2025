package com.virtusa.bankingzeebeclient;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.client.api.response.ProcessInstanceResult;
import io.camunda.zeebe.process.test.api.ZeebeTestEngine;
import io.camunda.zeebe.process.test.assertions.BpmnAssert;
import io.camunda.zeebe.process.test.assertions.DeploymentAssert;
import io.camunda.zeebe.process.test.assertions.ProcessInstanceAssert;
import io.camunda.zeebe.process.test.extension.ZeebeProcessTest;
import io.camunda.zeebe.process.test.filters.RecordStream;
import io.camunda.zeebe.process.test.inspections.InspectionUtility;
import io.camunda.zeebe.process.test.inspections.model.InspectedProcessInstance;

@ZeebeProcessTest
public class BankingzeebeclientApplicationTests {
	private ZeebeTestEngine engine;
	  private ZeebeClient client;
	  private RecordStream recordStream;
    
	

	@Test
	public void testDeployment() {
		DeploymentEvent event = client.newDeployResourceCommand()
				  .addResourceFromClasspath("refprocesses/Multi-instance_subprocess_User_v02.bpmn")
				  .send()
				  .join();
				DeploymentAssert assertions = BpmnAssert.assertThat(event);
	}

	@Test
	public void testProcessInstance() {
		
		ProcessInstanceEvent event = client.newCreateInstanceCommand()
				  .bpmnProcessId("Process_PaymentProcess")
				  .latestVersion()
				  .send()
				  .join();
				ProcessInstanceAssert assertions = BpmnAssert.assertThat(event);
	}
	

	@Test
	public void testProcessInstanceEvent() {
		ProcessInstanceEvent event = client.newCreateInstanceCommand()
				  .bpmnProcessId("Process_PaymentProcess")
				  .latestVersion()
				  
				  .send()
				  .join();
				ProcessInstanceAssert assertions = BpmnAssert.assertThat(event);
	}
	@Test
	public void testProcessInstanceEventResult() {
		ProcessInstanceResult event = client.newCreateInstanceCommand()
				  .bpmnProcessId("Process_PaymentProcess")
				  .latestVersion()
				  .withResult()
				  .send()
				  .join();
				ProcessInstanceAssert assertions = BpmnAssert.assertThat(event);
	}
	@Test
	public void testProcessByInstanceKey() {
		Optional<InspectedProcessInstance> firstProcessInstance = 
				InspectionUtility.findProcessInstances()
				  .withParentProcessInstanceKey(2251799813838201L)
				  .withBpmnProcessId("Process_Travel")
				  .findFirstProcessInstance();
				ProcessInstanceAssert assertions = BpmnAssert.assertThat(firstProcessInstance.get());
	}
	
}
