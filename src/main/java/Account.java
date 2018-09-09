import java.math.BigDecimal;

public class Account {

	private String accountNumber;
	private String accountType;
	private BigDecimal accountBalance;

	public String getAccountNumber() {
		return accountNumber;
	}
	
	public BigDecimal checkAccountBalance() {
		return accountBalance.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	public Account(String accountNumber, String accountType, String accountBalance) {
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.accountBalance = new BigDecimal(accountBalance);
	}

	public void deposit(String depositAmount) {
		BigDecimal deposit = new BigDecimal(depositAmount);
		accountBalance = accountBalance.add(deposit);
	}

	public void withdraw(String withdrawAmount) {
		BigDecimal withdrawal = new BigDecimal(withdrawAmount);
		if (withdrawal.compareTo(accountBalance) == 1) {
			accountBalance = accountBalance.subtract(accountBalance);
		} else {
		accountBalance = accountBalance.subtract(withdrawal);
		}
	}
	
	public String toString() {
		return "(" + accountNumber + ") " + accountType + "\t$" + accountBalance.setScale(2, BigDecimal.ROUND_HALF_UP);
	}



}
