import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Bank underTest;
	Account account1;
	Account account2;
	Client client1;
	Collection<String> userAccounts;
	
	//testing values only
	Money assert100 = new Money("100");
	Money assert200 = new Money("200");
	Money assert300 = new Money("300");
	Money assert50 = new Money("50");
	Money assert0 = new Money("0");
	
	@Before
	public void setUp() {
		underTest = new Bank();
		account1 = new Account("1", "Checking", "100");
		account2 = new Account("2", "Savings", "500");
		underTest.openNewAccount(account1);
		userAccounts = new ArrayList<>();
		userAccounts.add("1");
		client1 = new Client("Todd", "1234", userAccounts);
		underTest.addClient(client1);

	}
	
	@Test
	public void assertBankAccountIsAdded() {
		Account retrievedAccount = underTest.getAccount("1");
		assertThat(retrievedAccount, is(account1));
	}
	
	@Test
	public void assertBankAccountBalanceIs100() {
		Account retrievedAccount = underTest.getAccount("1");
		Money accountBalance = retrievedAccount.checkAccountBalance();
		assertThat(accountBalance.getValue(), is(assert100.getValue()));
		
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
	
	
	@Test
	public void assertThatDepositInAct1for100MakesBalance200() {
		Account retrievedAccount = underTest.getAccount(account1.getAccountNumber());
		retrievedAccount.deposit("100");
		assertThat(retrievedAccount.checkAccountBalance().getValue(), is(assert200.getValue()));
	}

	@Test
	public void assertThatDepositInAct1for200MakesBalance300() {
		Account retrievedAccount = underTest.getAccount(account1.getAccountNumber());
		retrievedAccount.deposit("200");
		assertThat(retrievedAccount.checkAccountBalance().getValue(), is(assert300.getValue()));
	}
	
	@Test
	public void assertThatWithdrawInAct1For50MakesBalance50() {
		account1.withdraw("50");
		assertThat(account1.checkAccountBalance().getValue(), is(assert50.getValue()));
	}
	
	@Test
	public void assertThatWithdrawInAct1For100MakesBalance0() {
		account1.withdraw("100");
		assertThat(account1.checkAccountBalance().getValue(), is(assert0.getValue()));
	}
	
	@Test
	public void assertThatWithdrawInAct1For200MakesBalance0() {
		account1.withdraw("200");
		assertThat(account1.checkAccountBalance().getValue(), is(assert0.getValue()));
	}
	
	@Test
	public void assertAccessToAccountWithCorrectPin() {
		boolean access = client1.verifyAccess("1234");
		assertTrue(access);
	}
	
	@Test
	public void assertDeniedAccessToAccountwithIncorrectPin() {
		boolean access = client1.verifyAccess("1235");
		assertFalse(access);
	}
	
	@Test
	public void assertClientExists() {
		underTest.addClient(client1);
		Client retrievedClient = underTest.retrieveClientInfo("Todd");
		assertThat(retrievedClient, is(client1));
	}
	
	@Test
	public void verifyClient1HasNoAccessToAccount2() {
		underTest.openNewAccount(account2);
		Collection<Account> myAccounts = underTest.accessClientAccounts(client1);
		assertThat(myAccounts, not(containsInAnyOrder(account2)));
	}
	
	@Test
	public void verifyClient1HasAccessToAccount1() {
		underTest.openNewAccount(account2);
		Collection<Account> myAccounts = underTest.accessClientAccounts(client1);
		assertThat(myAccounts, containsInAnyOrder(account1));
	}
	
	//account transfer
	//account merge
	//co-ownership
	
}
