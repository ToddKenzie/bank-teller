
public class Account {

	private String accountNumber;
	private String accountType;
	private int accountBalance;

	public String getAccountNumber() {
		return accountNumber;
	}
	
	public String getAccountType() {
		return accountType;
	}
	
	public int checkAccountBalance() {
		return accountBalance;
	}
	
	public Account(String accountNumber, String accountType, int accountBalance) {
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.accountBalance = accountBalance;
	}

	public void deposit(int depositAmount) {
		accountBalance += depositAmount;
	}

	public void withdraw(int withdrawAmount) {
		if (withdrawAmount > accountBalance) {
			accountBalance = 0;
		} else {
		accountBalance -= withdrawAmount;
		}
	}
	
	public String toString() {
		return "(" + accountNumber + ") " + accountType + "\t" + accountBalance;
	}



}
