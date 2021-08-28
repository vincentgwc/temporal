package org.vg.temporal_poc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vg.temporal_poc.service.FundTransferService;
import org.vg.temporal_poc.service.OrderService;

@RestController
public class MainController {
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	FundTransferService fundTransferService;

	@PostMapping("/startWorkflow")
	public String createOrder(@RequestParam("id") String id) {
		orderService.placeOrder(id);
		return "Order Placed";
	}

	@PostMapping("/orderAccepted")
	public String orderAccepted(@RequestParam("id") String id) {
		orderService.makeOrderAccepted(id);
		return "Order Accepted";
	}

	@PostMapping("/orderPickedUp")
	public String orderPickedUp(@RequestParam("id") String id) throws Exception {
		orderService.makeOrderPickedUp(id);
		return "Order Picked Up";
	}

	@PostMapping("/orderDelivered")
	public String orderDelivered(@RequestParam("id") String id) {
		orderService.makeOrderDelivered(id);
		return "Order Delivered";
	}
	
	@PostMapping("/performFT")
	public String performFT(@RequestParam("fromAcc") String fromAcc,@RequestParam("toAcc") String toAcc,@RequestParam("amount") double amount,@RequestParam String fireNForget) {
		
		if(fireNForget.equalsIgnoreCase("Y")){
			String result = fundTransferService.startFundTransferNonWaiting(fromAcc,toAcc,amount);
			System.out.println("fireNForget Result: "+result);
		}else {
			String result = fundTransferService.startFundTransfer(fromAcc,toAcc,amount);
			System.out.println("fireNTrack Result: "+result);
		}
		return "performFT performed with fireNForget : "+fireNForget;
	}
}
