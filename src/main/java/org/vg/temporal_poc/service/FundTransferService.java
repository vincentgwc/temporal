package org.vg.temporal_poc.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vg.temporal_poc.temporal.fundTransfer.FundTransferWorkflow;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;

@Service
public class FundTransferService {
	@Autowired
	WorkflowServiceStubs workflowServiceStubs;

	@Autowired
	WorkflowClient workflowClient;
	
	public String startFundTransfer(String accFrm, String accTo, double amount) {
		
		WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(FundTransferWorkflow.QUEUE_NAME)
				.setWorkflowId(FundTransferWorkflow.QUEUE_NAME + "_" + accFrm).build();
		FundTransferWorkflow workflow = workflowClient.newWorkflowStub(FundTransferWorkflow.class, options);
		CompletableFuture<String> result = WorkflowClient.execute(workflow::performFundTransfer,accFrm,accTo,amount);
		try {
			result.get();
			return "COMPLETED";
		} catch (InterruptedException e) {			
			e.printStackTrace();
			return "InterruptedException";
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ExecutionException";
		}		
	}
	
public String startFundTransferNonWaiting(String accFrm, String accTo, double amount) {
		
		WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(FundTransferWorkflow.QUEUE_NAME)
				.setWorkflowId(FundTransferWorkflow.QUEUE_NAME + "_" + accFrm).build();
		FundTransferWorkflow workflow = workflowClient.newWorkflowStub(FundTransferWorkflow.class, options);
		WorkflowClient.start(workflow::performFundTransfer,accFrm,accTo,amount);
		return "COMPLETED";	
	}
}
