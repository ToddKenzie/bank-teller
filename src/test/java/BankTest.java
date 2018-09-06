import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Bank underTest;
	Account account1;
	Account account2;
	
	//testing values only
	BigDecimal assert100 = new BigDecimal("100");
	BigDecimal assert200 = new BigDecimal("200");
	BigDecimal assert300 = new BigDecimal("300");
	BigDecimal assert50 = new BigDecimal("50");
	BigDecimal assert0 = new BigDecimal("0");
	
	@Before
	public void setUp() {
		underTest = new Bank("1234");
		account1 = new Account("1", "Checking", "100");
		account2 = new Account("2", "Savings", "500");
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
		BigDecimal accountBalance = retrievedAccount.checkAccountBalance();
		assertThat(accountBalance, is(assert100));
		
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
		assertThat(retrievedAccount.checkAccountBalance(), is(assert200));
	}

	@Test
	public void assertThatDepositInAct1for200MakesBalance300() {
		Account retrievedAccount = underTest.getAccount(account1.getAccountNumber());
		retrievedAccount.deposit("200");
		assertThat(retrievedAccount.checkAccountBalance(), is(assert300));
	}
	
	@Test
	public void assertThatWithdrawInAct1For50MakesBalance50() {
		account1.withdraw("50");
		assertThat(account1.checkAccountBalance(), is(assert50));
	}
	
	@Test
	public void assertThatWithdrawInAct1For100MakesBalance0() {
		account1.withdraw("100");
		assertThat(account1.checkAccountBalance(), is(assert0));
	}
	
	@Test
	public void assertThatWithdrawInAct1For200MakesBalance0() {
		account1.withdraw("200");
		assertThat(account1.checkAccountBalance(), is(assert0));
	}
	
	@Test
	public void assertAccessToAccountWithCorrectPin() {
		boolean access = underTest.verifyAccess("1234");
		assertTrue(access);
	}
	
	@Test
	public void assertDeniedAccessToAccountwithIncorrectPin() {
		boolean access = underTest.verifyAccess("1235");
		assertFalse(access);
	}
	
	
}
