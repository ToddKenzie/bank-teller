import static org.junit.Assert.assertThat;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Bank underTest;
	Account account1;
	Account account2;

	
	@Before
	public void setUp() {
		underTest = new Bank();
		account1 = new Account("1", "Checking", 100);
		account2 = new Account("2", "Savings", 500);
		underTest.openNewAccount(account1);
	}
	@Test
	public void assertBankAccountIsAdded() {
		Account retrievedAccount = underTest.getAccount("1");
		assertThat(retrievedAccount, is(account1));
	}
	
	@Test
	public void assertBankAccountIsChecking() {
		Account retrievedAccount = underTest.getAccount("1");
		String accountType = retrievedAccount.getAccountType();
		assertThat(accountType, is("Checking"));
	}
	
	@Test
	public void assertBankAccountBalanceIs100() {
		Account retrievedAccount = underTest.getAccount("1");
		int accountBalance = retrievedAccount.checkAccountBalance();
		assertThat(accountBalance, is(100));
		
	}
	
	@Test
	public void assertTwoAccountsAreAdded() {
		underTest.openNewAccount(account2);
		Collection<Account> allAccounts = underTest.getAllAccounts();
		assertThat(allAccounts, containsInAnyOrder(account1, account2));
	}
	
	@Test
	public void assertClosureOfAccount() {
		underTest.closeAccount("1");
		Account retrievedAccount = underTest.getAccount(account1.getAccountNumber());
		assertThat(retrievedAccount, is(nullValue()));
	}
	
	
	
	
}
