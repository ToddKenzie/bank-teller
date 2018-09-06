import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Bank {

	Map<String, Account> accounts = new HashMap<>();
	private Object accessPin;
	
	public Bank(String accessPin) {
		this.accessPin = accessPin;
	}

	public void openNewAccount(Account account) {
		accounts.put(account.getAccountNumber(), account);
	}

	public Account getAccount(String accountNumber) {
		return accounts.get(accountNumber);
	}

	public Collection<Account> getAllAccounts() {
		return accounts.values();
	}

	public void closeAccount(String accountNumber) {
		accounts.remove(accountNumber);
	}

	public boolean verifyAccess(String userEnteredPin) {
		return (userEnteredPin.equals(accessPin));
	}

}
