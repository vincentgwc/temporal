package org.vg.temporal_poc.temporal.fundTransfer;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface FundTransferWorkflow {
	
	public static final String QUEUE_NAME = "FundTransfer_WF";
	
	@WorkflowMethod
	String performFundTransfer(String accountFrom, String accountTo, double amount);
	
}
