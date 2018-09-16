
public class Account {

	private String accountNumber;
	private String accountType;
	private Money accountBalance;

	public String getAccountNumber() {
		return accountNumber;
	}
	
	public Money checkAccountBalance() {
		return accountBalance;
	}
	
	public Account(String accountNumber, String accountType, String accountBalance) {
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.accountBalance = new Money(accountBalance);
	}

	public void deposit(String depositAmount) {
		Money deposit = new Money(depositAmount);
		accountBalance.add(deposit);
	}

	public void withdraw(String withdrawAmount) {
		Money withdrawal = new Money(withdrawAmount);
		if (withdrawal.compareTo(accountBalance) == 1) {
			accountBalance.subtract(accountBalance);
		} else {
		accountBalance.subtract(withdrawal);
		}
	}
	
	public String toString() {
		return "(" + accountNumber + ") " + accountType + " " + accountBalance;
	}



}
