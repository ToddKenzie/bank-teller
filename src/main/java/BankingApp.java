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

	private static void executeDeposit() {
		displayAccounts();
		System.out.println("Enter the account number to deposit into");
		String accountNumber = input.nextLine();
		Account useAccount = wcciBank.getAccount(accountNumber);
		
	}

	private static void executerWithdraw() {
		
	}
	
	private static void executeAccountClosure() {
		
	}


	
	private static void exitProgram() {
		System.out.println("Thank you for banking with us.");
		System.exit(0);
	}
	
}

