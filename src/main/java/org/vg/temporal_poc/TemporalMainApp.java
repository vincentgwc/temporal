package org.vg.temporal_poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.vg.temporal_poc.temporal.Activity;
import org.vg.temporal_poc.temporal.WorkFlow;
import org.vg.temporal_poc.temporal.WorkFlowImpl;
import org.vg.temporal_poc.temporal.fundTransfer.FundTransferActivity;
import org.vg.temporal_poc.temporal.fundTransfer.FundTransferWorkflow;
import org.vg.temporal_poc.temporal.fundTransfer.FundTransferWorkflowImpl;

import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

@SpringBootApplication
public class TemporalMainApp {
	public static void main(String[] args) {
		ConfigurableApplicationContext appContext = SpringApplication.run(TemporalMainApp.class, args);
		WorkerFactory factory = appContext.getBean(WorkerFactory.class);
		
		Activity signUpActivity = appContext.getBean(Activity.class);
		Worker worker = factory.newWorker(WorkFlow.QUEUE_NAME);
		worker.registerWorkflowImplementationTypes(WorkFlowImpl.class);
		worker.registerActivitiesImplementations(signUpActivity);
		
		FundTransferActivity ft_Activity = appContext.getBean(FundTransferActivity.class);
		Worker ft_worker = factory.newWorker(FundTransferWorkflow.QUEUE_NAME);
		ft_worker.registerWorkflowImplementationTypes(FundTransferWorkflowImpl.class);
		ft_worker.registerActivitiesImplementations(ft_Activity);
		
		factory.start();
	}
}
