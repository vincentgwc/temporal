package org.vg.temporal_poc.temporal;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface Activity {

	@ActivityMethod
	void placeOrder();

	@ActivityMethod
	void setOrderAccepted();

	@ActivityMethod
	void setOrderPickedUp() throws Exception;

	@ActivityMethod
	void setOrderDelivered();
}
