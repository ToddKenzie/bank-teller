import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Scanner;

public class BankingApp {

	public static void main(String[] args) {
		banking();
	}

	private static Bank wcciBank;
	private static Scanner input;
	private static NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
	private static Collection<Account> bankAccounts;
	private static Client clientAccessingBank;
	
	public static void banking() {
		bankStartUp();
		runApp();
	}
	
	public static void runApp() {
		boolean isThereAnotherClientWhoNeedsAccess;
		do {
			gainAccess();
			bankMenu();
			isThereAnotherClientWhoNeedsAccess = checkForNextUser();
		} while (isThereAnotherClientWhoNeedsAccess);
	}
	
	public static boolean checkForNextUser() {
		System.out.println("\nIs there another client waiting to use the app? (Y / N)");
		String userInput = input.nextLine();
		return (userInput.equalsIgnoreCase("Y"));
	}
	
	
	public static void bankStartUp() {
		wcciBank = new Bank();
		bankStartUpTodd();
		bankStartUpAlan();
		input = new Scanner(System.in);
	}
	
	public static void bankStartUpTodd() {
		wcciBank.openNewAccount(new Account("2727", "Checking", "3000"));
		wcciBank.openNewAccount(new Account("2728", "Savings", "4000"));
		Collection<String> toddAccounts = new ArrayList<>();
		toddAccounts.add("2727");
		toddAccounts.add("2728");
		wcciBank.addClient(new Client("Todd", "5672", toddAccounts));
	}

	public static void bankStartUpAlan() {
		wcciBank.openNewAccount(new Account("6262", "Checking", "7000"));
		wcciBank.openNewAccount(new Account("6263", "Savings", "3000"));
		Collection<String> alanAccounts = new ArrayList<>();
		alanAccounts.add("6262");
		alanAccounts.add("6263");
		wcciBank.addClient(new Client("Alan", "1234", alanAccounts));
	}


	public static void gainAccess() {
		verifyUserName();
		if (!hasUserVerifiedPin()) {
			denyAccess();
		}
		System.out.println("\nWelcome to the Bank of We Can Code IT!");
		displayAccounts();
	}
	
	public static void verifyUserName() {
		boolean isUserNameInvalid;
		do {
			System.out.println("Please enter your username (Case sensitive):");
			String clientName = input.nextLine();	
			clientAccessingBank = wcciBank.retrieveClientInfo(clientName);
			isUserNameInvalid = (clientAccessingBank == null) ? true : false;
			if (isUserNameInvalid) {
				System.out.println("Unrecognized user.");
			}
		} while (isUserNameInvalid);
	}

	public static boolean hasUserVerifiedPin() {
		int attemptsRemainAtEnteringPin = 3;
		boolean isEnteredPinCorrect;
		do {
			System.out.println("Please enter your PIN to access your accounts");
			String userEnteredPin = input.nextLine();
			isEnteredPinCorrect = clientAccessingBank.verifyAccess(userEnteredPin);
			if (!isEnteredPinCorrect) {
				attemptsRemainAtEnteringPin--;
				String display = "PIN Incorrect. You have " + attemptsRemainAtEnteringPin;
				String display2 = (attemptsRemainAtEnteringPin == 1) ? " attempt remaining." : " attempts remaining.";
				System.out.println(display + display2);
			}
		} while (!isEnteredPinCorrect && attemptsRemainAtEnteringPin > 0);
		return isEnteredPinCorrect;
	}

	public static void denyAccess() {
		System.out.println("You have exhausted your attempts.\nPlease call the bank to unlock your account.");
		System.exit(0);
	}

	public static void displayAccounts() {
		bankAccounts = wcciBank.accessClientAccounts(clientAccessingBank);
		System.out.println("Current Bank Accounts");
		for (Account account : bankAccounts) {
			System.out.println(account);
		}
		System.out.println();
	}

	public static void bankMenu() {
		boolean continueBanking;
		do {
			displayMainMenu();
			String userMainInput = input.nextLine();
			continueBanking = executeMainMenuCommand(userMainInput);
		} while (continueBanking);
	}

	public static void displayMainMenu() {
		String display = "What would you like to do next?\n";
		if (!bankAccounts.isEmpty()) {
			display += "Press 1 to deposit\nPress 2 to withdraw\n";
			display += "Press 3 to check balance\nPress 4 to close account\n";
		}
		display += "Press 5 to create new account\nPress 0 to exit\n";
		System.out.println(display);
	}

	private static boolean executeMainMenuCommand(String userMainInput) {
		if (userMainInput.equals("1") && !bankAccounts.isEmpty()) {
			executeDeposit();
		} else if (userMainInput.equals("2") && !bankAccounts.isEmpty()) {
			executeWithdraw();
		} else if (userMainInput.equals("3") && !bankAccounts.isEmpty()) {
			displayAccounts();
		} else if (userMainInput.equals("4") && !bankAccounts.isEmpty()) {
			executeAccountClosure();
		} else if (userMainInput.equals("5")) {
			createNewAccount();
		} else if (userMainInput.equals("0")) {
			exitProgram();
			return false;
		} else {
			System.out.println("Invalid command.");
		}
		return true;
	}

	private static void executeDeposit() {
		String endOfPrompt = "deposit into";
		Account accountToEdit = validateAccountToEdit(endOfPrompt);
		System.out.println("How much would you like to deposit?");
		String depositAmount = receiveValidInput();
		accountToEdit.deposit(depositAmount);
		System.out.println(convertToCurrency(depositAmount) + " was deposited into the account\n");
	}

	private static void executeWithdraw() {
		String endOfPrompt = "withdraw from";
		Account accountToEdit = validateAccountToEdit(endOfPrompt);
		System.out.println("How much would you like to withdraw?");
		String withdrawAmount = receiveValidInput();
		verifyWithdrawAmount(accountToEdit, withdrawAmount);
		accountToEdit.withdraw(withdrawAmount);
	}
	
	private static void verifyWithdrawAmount(Account accountToEdit, String withdrawAmount) {
		BigDecimal withdrawal = new BigDecimal(withdrawAmount);
		if (withdrawal.compareTo(accountToEdit.checkAccountBalance()) == 1) {
			System.out.println("That is more money than exists in the account.");
			System.out.println(convertToCurrency(accountToEdit.checkAccountBalance()) + " will be withdrawn instead");
			withdrawal = accountToEdit.checkAccountBalance();
		}
		System.out.println(convertToCurrency(withdrawal) + " was withdrawn from the account\n");
	}

	private static void executeAccountClosure() {
		String endOfPrompt = "close";
		Account accountToEdit = validateAccountToEdit(endOfPrompt);
		if (accountToEdit.checkAccountBalance().compareTo(BigDecimal.ZERO) > 0) {
			System.out.println(
					"We cannot close this account.  Please withdraw all money prior to closing the account.\n");
		} else {
			System.out.println("Closing account " + accountToEdit.getAccountNumber() + "\n");
			clientAccessingBank.getClientAccounts().remove(accountToEdit.getAccountNumber());
			wcciBank.closeAccount(accountToEdit.getAccountNumber());
		}
	}

	private static Account validateAccountToEdit(String endOfPrompt) {
		Account accountToEdit;
		displayAccounts();
		System.out.println("Enter the account you wish to " + endOfPrompt);
		do {
			accountToEdit = retrieveAccount();
			if (accountToEdit == null) {
				System.out.println("Invalid user account.");
			}
		} while (accountToEdit == null);
		return accountToEdit;
	}
	
	private static void createNewAccount() {
		String accountType = verifyAccountType();
		String accountNumber = generateAccountNumber();
		Account newAccount = new Account(accountNumber, accountType, "0");
		wcciBank.openNewAccount(newAccount);
		bankAccounts.add(newAccount);
		clientAccessingBank.getClientAccounts().add(accountNumber);
		System.out.println("Account " + accountNumber + " created.\n");
	}

	private static String generateAccountNumber() {
		String accountNumber;
		do {
			Integer number = (int) (Math.random() * 9000 + 1000);
			accountNumber = number.toString();
		} while (wcciBank.accounts.containsKey(accountNumber));
		return accountNumber;
	}

	private static String verifyAccountType() {
		String userEnteredData;
		do {
			System.out.println("Which type of account?  Checking or savings?");
			userEnteredData = input.nextLine();
			if (!userEnteredData.equalsIgnoreCase("checking") && !userEnteredData.equalsIgnoreCase("savings")) {
				System.out.println("Invalid Account Type.");
			}
		} while (!userEnteredData.equalsIgnoreCase("checking") && !userEnteredData.equalsIgnoreCase("savings"));
		return userEnteredData.substring(0, 1).toUpperCase() + userEnteredData.substring(1).toLowerCase();
	}

	private static Account retrieveAccount() {
		String accountNumber = input.nextLine();
		if (!bankAccounts.contains(wcciBank.getAccount(accountNumber))) {
			return null;
		}
		return wcciBank.getAccount(accountNumber);
	}

	private static String receiveValidInput() {
		String userInput;
		boolean inputValid = false;
		do {
			userInput = input.nextLine();
			try {
				@SuppressWarnings("unused")
				BigDecimal convertCheck = new BigDecimal(userInput);
				inputValid = true;
			} catch (NumberFormatException ex) {
				System.out.println("The value entered is not a number.");
			}
		} while (!inputValid);
		return userInput;
	}

	private static String convertToCurrency(String value) {
		BigDecimal money = new BigDecimal(value);
		return currency.format(money);
	}

	private static String convertToCurrency(BigDecimal value) {
		return currency.format(value);
	}
	
	private static void exitProgram() {
		System.out.println("Thank you for banking with us.");
	}

}
