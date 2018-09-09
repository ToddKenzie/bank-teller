import java.util.Collection;

public class Client {

	private String clientName;
	private String accessPin;
	private Collection<String> userAccounts;

	public String getClientName() {
		return clientName;
	}

	public Client(String clientName, String accessPin, Collection<String> userAccounts) {
		this.clientName = clientName;
		this.accessPin = accessPin;
		this.userAccounts = userAccounts;
	}
	
	public boolean verifyAccess(String userEnteredPin) {
		return (userEnteredPin.equals(accessPin));
	}
	
	public Collection<String> getClientAccounts() {
		return userAccounts;
	}


}
