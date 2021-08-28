package org.vg.temporal_poc.temporal.fundTransfer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Async;
import io.temporal.workflow.Promise;
import io.temporal.workflow.Saga;
import io.temporal.workflow.Workflow;

public class FundTransferWorkflowImpl implements FundTransferWorkflow {

	private final RetryOptions retryoptions = RetryOptions.newBuilder().setInitialInterval(Duration.ofSeconds(1))
			.setMaximumInterval(Duration.ofSeconds(100)).setBackoffCoefficient(2).setMaximumAttempts(3).build();
	private final ActivityOptions options = ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(30))
			.setRetryOptions(retryoptions).build();

	private final FundTransferActivity activities = Workflow.newActivityStub(FundTransferActivity.class, options);

	@Override
	public String performFundTransfer(String accountFrom, String accountTo, double amount){
		
		List<Promise<String>> localNamePromises = new ArrayList<>();
	    localNamePromises.add(Async.function(activities::checkAccountStatus, accountFrom));
	    localNamePromises.add(Async.function(activities::checkHoldCode, accountFrom));
	    localNamePromises.add(Async.function(activities::checkTextId, accountFrom));
		Promise.allOf(localNamePromises).get();
		
		for(Promise<String> promiss : localNamePromises) {
			System.out.println(promiss.isCompleted() +" "+ promiss.get());
		}

		Saga.Options sagaOptions = new Saga.Options.Builder().setParallelCompensation(false).build();
	    Saga saga = new Saga(sagaOptions);
	    
	    try {
	        activities.preformCADebit(accountFrom+"_1", amount);
	        saga.addCompensation(activities::preformCACrebit, accountFrom+"_1", amount);
	        
	        activities.preformCADebit(accountFrom+"_2", amount);
	        saga.addCompensation(activities::preformCACrebit, accountFrom+"_2", amount);
	        
	        activities.preformCADebit(accountFrom+"_3", amount);
	        saga.addCompensation(activities::preformCACrebit, accountFrom+"_3", amount);

	        String saRefNo = activities.preformSADebit(accountTo, amount);
	        saga.addCompensation(activities::preformSACrebit, accountTo, amount);
	        
	      } catch (Exception e) {
	        saga.compensate();
	      }
	    
	    return "COMPLETED";
	}
}
