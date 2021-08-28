package org.vg.temporal_poc.temporal.fundTransfer;

public class FundTransferActivityImpl implements FundTransferActivity {

	@Override
	public String checkAccountStatus(String accountId) {
		System.out.println("checkAccountStatus : " + accountId);
		return "SUCCESS";
	}

	@Override
	public String checkTextId(String accountId) {
		System.out.println("checkTextId : " + accountId);
		return "SUCCESS";
	}

	@Override
	public String checkHoldCode(String accountId) {
		System.out.println("checkHoldCode : " + accountId);
		return "SUCCESS";
	}

	@Override
	public String preformCADebit(String accountId, double amount){
		System.out.println("preformCADebit : " + accountId + " " + amount);
		return "SUCCESS";
	}

	@Override
	public String preformCACrebit(String accountId, double amount) {
		// TODO Auto-generated method stub
		System.out.println("preformCACrebit : " + accountId + " " + amount);
		return "SUCCESS";
	}

	@Override
	public String preformSADebit(String accountId, double amount)  throws Exception {
		// TODO Auto-generated method stub
		System.out.println("preformSADebit : " + accountId + " " + amount);
		if(true) {
			throw new Exception("ABC");
		}
		return "SUCCESS";
	}

	@Override
	public String preformSACrebit(String accountId, double amount) {
		// TODO Auto-generated method stub
		System.out.println("preformSACrebit : " + accountId + " " + amount);
		return "SUCCESS";
	}

}
