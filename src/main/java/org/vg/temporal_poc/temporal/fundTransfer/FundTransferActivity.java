package org.vg.temporal_poc.temporal.fundTransfer;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface FundTransferActivity {

	String checkAccountStatus(String accountId);

	String checkTextId(String accountId);

	String checkHoldCode(String accountId);

	String preformCADebit(String accountId, double amount);

	String preformCACrebit(String accountId, double amount);

	String preformSADebit(String accountId, double amount) throws Exception;

	String preformSACrebit(String accountId, double amount);
}
