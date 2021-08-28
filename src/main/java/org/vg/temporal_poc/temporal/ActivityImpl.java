package org.vg.temporal_poc.temporal;

public class ActivityImpl implements Activity {

	@Override
	public void placeOrder() {
		System.out.println("***** Order has been placed");
	}

	@Override
	public void setOrderAccepted() {
		System.out.println("***** Restaurant has accepted your order");
	}

	@Override
	public void setOrderPickedUp() throws Exception {
		if(false) {
			System.out.println("***** setOrderPickedUp - Force Retry");
			throw new Exception("setOrderPickedUp - Force Retry");
		}
		
		System.out.println("***** Order has been picked up - Recovery");
	}

	@Override
	public void setOrderDelivered() {
		System.out.println("***** Order Delivered");
	}

}
