import java.util.Collection;
import java.util.Scanner;

public class BankingApp {

	public static void main(String[] args) {
		banking();
	}

	private static Bank wcciBank;
	private static Account accountA;
	private static Account accountB;
	private static Scanner input;
	
	public static void bankStartUp() {
		wcciBank = new Bank();
		accountA = new Account("1234", "Checking", 2000);
		accountB = new Account("5678", "Savings", 5000);
		wcciBank.openNewAccount(accountA);
		wcciBank.openNewAccount(accountB);
		input = new Scanner(System.in);
	}
	
	public static void banking() {
		bankStartUp();
		displayAccounts();
		bankMenu();
	}
	
	public static void displayAccounts() {
		Collection<Account> bankAccounts = wcciBank.getAllAccounts();
		System.out.println("Current Bank Accounts");
		for (Account account : bankAccounts) {
			System.out.println(account);
		}
		System.out.println();
	}
	
	public static void bankMenu() {
		do {
		displayMainMenu();
		String userMainInput = input.nextLine();
		executeMainMenuCommand(userMainInput);
		} while (true);
	}
	
	public static void displayMainMenu() {
		System.out.println("What would you like to do next?");
		System.out.println("Press 1 to deposit");
		System.out.println("Press 2 to withdraw");
		System.out.println("Press 3 to check balance");
		System.out.println("Press 4 to close account");
		System.out.println("Press 0 to exit\n");
	}
	
	private static void executeMainMenuCommand(String userMainInput) {
		if (userMainInput.equals("1")) {
			executeDeposit();
		} else if (userMainInput.equals("2")) {
			executerWithdraw();
		} else if (userMainInput.equals("3")) {
			displayAccounts();
		} else if (userMainInput.equals("4")) {
			executeAccountClosure();
		} else if (userMainInput.equals("0")) {
			exitProgram();
		} else {
			System.out.println("Invalid command.");
		}
	}

	// With the way you're writing executeDeposit/Withdraw/Closure - is this a case
	// for inheritance (in a normal - not main method - situation)?
	
	private static void executeDeposit() {
		displayAccounts();
		System.out.println("Enter the account number to deposit into");
		Account accountToEdit = retrieveAccount();
		if (accountToEdit != null) {
			System.out.println("How much would you like to deposit?");
			String userInput = input.nextLine();
			int depositAmount = checkIfValid(userInput);
			accountToEdit.deposit(depositAmount);
			System.out.println(depositAmount + " was deposited into the account\n");
			
		} else {
			System.out.println("Invalid account choice.  Returning to main menu.\n");
		}
	}

	private static void executerWithdraw() {
		displayAccounts();
		System.out.println("Enter the account number to withdraw from");
		Account accountToEdit = retrieveAccount();
		if (accountToEdit != null) {
			System.out.println("How much would you like to withdraw?");
			String userInput = input.nextLine();
			int withdrawAmount = checkIfValid(userInput);
			if (withdrawAmount > accountToEdit.checkAccountBalance()) {
				System.out.println("That is more money than exists in the account.");
				System.out.println(accountToEdit.checkAccountBalance() + " will be withdrawn instead");
				withdrawAmount = accountToEdit.checkAccountBalance();
			}
			accountToEdit.withdraw(withdrawAmount);
			System.out.println(withdrawAmount + " was withdrawn from the account\n");

		} else {
			System.out.println("Invalid account choice.  Returning to main menu.\n");
		}
	}
	
	private static void executeAccountClosure() {
		displayAccounts();
		System.out.println("Enter the account you wish to close");
		Account accountToEdit = retrieveAccount();
		if (accountToEdit != null) {
			if (accountToEdit.checkAccountBalance() > 0) {
				System.out.println("We cannot close this account.  Please withdraw all money prior to closing the account.\n");
			} else {
				System.out.println("Closing account " + accountToEdit.getAccountNumber() + "\n");
				wcciBank.closeAccount(accountToEdit.getAccountNumber());
			}
			
		} else {
			System.out.println("Invalid account choice.  Returning to main menu.");
		}
	}

	private static Account retrieveAccount() {
		String accountNumber = input.nextLine();
		return wcciBank.getAccount(accountNumber);
	}
	
	private static int checkIfValid(String userInput) {
		int amount = 0;
		try {
			amount = Integer.parseInt(userInput);
		} catch (NumberFormatException ex) {
			System.out.println("The value entered is not a number.");
		}
		return amount;
	}

	
	private static void exitProgram() {
		System.out.println("Thank you for banking with us.");
		System.exit(0);
	}
	
}

