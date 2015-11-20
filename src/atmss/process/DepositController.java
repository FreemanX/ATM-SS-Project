/**
 * 
 */
package atmss.process;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

import atmss.Operation;
import atmss.Session;

/**
 * @author Lihui
 *
 */
public class DepositController extends ProcessController {

	//private int amount;
	private String accountToDeposit;
	private int amountToDeposit;

	private final String OPERATION_NAME = "Deposit";
	private final String FAILED_FROM_DISPLAY = "No response from display";
	private final String FAILED_FROM_KEYPAD = "No response from the keypad";
	private final String FAILED_FROM_ENVELOPDISPENSER = "No response from envelop dispenser";
	private final String FAILED_FROM_DEPOSITCOLLECTOR = "No response from deposit collector";
	private final String FAILED_FROM_ADVICEPRINTER = "No response from advice printer";
	private final String FAILED_FROM_BAMS = "Failed from BAMS";
	private final String PROMPT_FOR_ACCOUNT = "Please select account to deposit :";
	private final String PROMPT_FOR_AMOUNT = "Please type in your deposit amount :";
	private final String PROMPT_FOR_CONFIRM = "Please confirm your deposit amount by ENTER. Press 0 to reinput amount.";
	private final String PROMPT_FOR_COLLECT_ENVELOP = "Please collect the envelop and put cheque/cash and receipt into the envelop";
	private final String PROMPT_FOR_RETURN_ENVELOP = "Please put the envelop with cheque/cash to deposit collector";
	private final String CANCELED = "Operation Canceled!";
	
	/**
	 * 
	 */
	public DepositController(Session Session) {
		// TODO Auto-generated constructor stub
		super(Session);
	}

	public Boolean doDeposit() {
		// boolean isSuccess = false;
		/*
		 * Implement the process here.
		 */
		// prompt for account to deposit
		if (!this.doGetAccountToDeposit())
			return false;

		// prompt for amount to deposit
		if (!this. doGetAmountToDeposit())
			return false;

		if (!this.doPrintReceipt())
			return false;

		if (!this.doEjectEnvelop())
			return false;

		if (!this.doEatEnvelop())
			return false;
		
		this.recordOperation();
		this.printOperation();
		return true;
	}
	
	private boolean doEatEnvelop(){
		if(!this._atmssHandler.doDisClearAll() || !this._atmssHandler.doDisDisplayUpper(new String[] {PROMPT_FOR_RETURN_ENVELOP}))
			return failProcess(FAILED_FROM_DISPLAY);
		
		if(!this._atmssHandler.doEDEatEnvelop())
			return failProcess(FAILED_FROM_DEPOSITCOLLECTOR);
		
		return true;
	}
	
	private boolean doEjectEnvelop(){
		if(!this._atmssHandler.doDisClearAll() || !this._atmssHandler.doDisDisplayUpper(new String[] {PROMPT_FOR_COLLECT_ENVELOP}))
			return failProcess(FAILED_FROM_DISPLAY);
		
		if(!this._atmssHandler.doEDEjectEnvelop())
			return failProcess(FAILED_FROM_ENVELOPDISPENSER);

		return true;
	}
	
		
	private boolean doPrintReceipt(){
		if(!this._atmssHandler .doAPPrintStrArray(new String[] {"Account to deposit: "+accountToDeposit, "Amount to deposit: $"+ Integer.toString(this.amountToDeposit)}))
			return failProcess(FAILED_FROM_ADVICEPRINTER);
		return true;
	}
	private boolean doGetAccountToDeposit() {

		if(!this._atmssHandler.doDisClearAll())
			return this.failProcess(FAILED_FROM_DISPLAY);
		
		List<String> accountsToChooseDisplay = new ArrayList<String>(Arrays.asList(new String[] {PROMPT_FOR_ACCOUNT}));

		String[] allAccountsInCard = this._atmssHandler.doBAMSGetAccounts(this._session);
		if(allAccountsInCard.length == 0){
			return this.failProcess(FAILED_FROM_BAMS);		
		}
		
		int index = 1;
		for (String account:allAccountsInCard){
			accountsToChooseDisplay.add("Account "+index+": "+account );
			index += 1;
		}
		

		
		
		if(!this._atmssHandler.doDisDisplayUpper(accountsToChooseDisplay.toArray(new String[0])))
			return this.failProcess(FAILED_FROM_DISPLAY);
		
		int accountNoSelectedByUser = allAccountsInCard.length + 1;
		
		while(accountNoSelectedByUser > allAccountsInCard.length){
		
		String accountSelectedByUser = this._atmssHandler.doKPGetSingleInput(3000);
		
		if(accountSelectedByUser!=null){
			try{
				accountNoSelectedByUser = Integer.parseInt(accountSelectedByUser);
			}
			catch(NumberFormatException e){
				if(accountSelectedByUser.equals("CANCEL"))
					return failProcess(CANCELED);
				
			}
		}
		else return this.failProcess(FAILED_FROM_KEYPAD);
		
		}
		
		this.accountToDeposit = allAccountsInCard[accountNoSelectedByUser-1];

		return true;

	}

	private boolean doGetAmountToDeposit() {

		boolean confirmAmountToDeposit = false;
		String userInputAmountToDeposit ="";
		
		 while (!confirmAmountToDeposit){
			System.out.println("new turn=========================================");
			if(!this._atmssHandler.doDisClearAll())
				return failProcess(FAILED_FROM_DISPLAY);
			if(!this._atmssHandler.doDisDisplayUpper(new String[] {PROMPT_FOR_AMOUNT}))
				return failProcess(FAILED_FROM_DISPLAY);
			
			userInputAmountToDeposit = this._atmssHandler.doKPGetIntegerMoneyAmount(300);
			if(userInputAmountToDeposit == null)
				return failProcess(FAILED_FROM_KEYPAD);
			
			if(!this._atmssHandler.doDisClearAll())
				return failProcess(FAILED_FROM_DISPLAY);
			if(!this._atmssHandler.doDisDisplayUpper(new String[] {PROMPT_FOR_CONFIRM, "$" + userInputAmountToDeposit}))
				return failProcess(FAILED_FROM_DISPLAY);
			
			String confirmInput = this._atmssHandler.doKPGetSingleInput(300);
			while (confirmInput != null){
				if (confirmInput.equals("ENTER")){
					confirmAmountToDeposit = true;
					break;
				}
				else if(confirmInput.equals("0")){
					break;
				}
				else if(confirmInput.equals("CANCEL")){
						return failProcess(CANCELED);
				}
				confirmInput = this._atmssHandler.doKPGetSingleInput(300);
			}

		}
		this.amountToDeposit = Integer.parseInt(userInputAmountToDeposit);
		return true;

	}
	
	private void recordOperation(){
		String description = 
				"Card Number: " + this._session.getCardNo() + ";" +
				"Result: " + "Succeeded; ";
		operationCache.add(new Operation(OPERATION_NAME,description));
	}
	
	private void recordOperation(String FailedReason){
		String description = 
				"Card Number: " + this._session.getCardNo() + ";" +
				"Result: " + "Failed;"+ 
				"Reason: " + FailedReason;
		operationCache.add(new Operation(OPERATION_NAME, description));
		
	}
	
	private boolean failProcess(String FailedReason){
		recordOperation(FailedReason);
		this.printOperationWhenFail();
		return false;
	}

}
