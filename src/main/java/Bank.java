import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Bank {

	Map<String, Client> clients = new HashMap<>();
	Map<String, Account> accounts = new HashMap<>();
	
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

	public void addClient(Client client) {
		clients.put(client.getClientName(), client);
	}
	
	public Client retrieveClientInfo(String clientName) {
		return clients.get(clientName);
	}

	public Collection<Account> accessClientAccounts(Client client) {
		Collection<String> clientAccountNumbers = client.getClientAccounts();
		Collection<Account> clientAccounts = new ArrayList<>();
		for (String clientAccountNumber : clientAccountNumbers) {
			clientAccounts.add(getAccount(clientAccountNumber));
		}
		return clientAccounts;
	}


}
