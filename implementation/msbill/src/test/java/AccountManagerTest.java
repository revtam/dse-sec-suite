import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import at.ac.univie.dse.msbill.data.account.AccountDao;
import at.ac.univie.dse.msbill.data.account.AccountRepository;
import at.ac.univie.dse.msbill.data.account.AccountStorage;
import at.ac.univie.dse.msbill.data.datatypes.Account;
import at.ac.univie.dse.msbill.exception.MissingKeyException;
import at.ac.univie.dse.msbill.logic.AccountManager;

@TestMethodOrder(OrderAnnotation.class)
class AccountManagerTest {

	static AccountManager accountManager;

	@BeforeAll
	static void init() {
		AccountDao accDao = new AccountStorage();
		AccountRepository accRepo = AccountRepository.getInstance(accDao);
		accountManager = new AccountManager(accRepo);
	}

	@Test
	@Order(1)
	void givenAccountNotStored_whenAccountAdded_thenAccountStored() {
		accountManager.addClnt("id1");
		Account account = accountManager.getAccount("id1");
		Assertions.assertEquals("id1", account.getClntInstanceId());
		Assertions.assertEquals(0.0, account.getBookedBalance());
	}

	@Test
	@Order(2)
	void givenAccountStored_whenCostRepeatedlyAdded_thenBookedBalanceAccumulated() {
		accountManager.addCost("id1", 10.0);
		Account account = accountManager.getAccount("id1");
		Assertions.assertEquals(10.0, account.getBookedBalance());
		accountManager.addCost("id1", 20.0);
		Assertions.assertEquals(30.0, account.getBookedBalance());
	}

	@Test
	@Order(3)
	void givenAccountStored_whenAccountWithSameIdAdded_thenNewAccountNotStored() {
		accountManager.addClnt("id1");
		Account account = accountManager.getAccount("id1");
		Assertions.assertEquals(30.0, account.getBookedBalance());
		Assertions.assertEquals(1, accountManager.getAllAccounts().size());
	}

	@Test
	@Order(2)
	void givenAccountNotExisting_whenAccountModifiedOrRetrieved_thenExceptionThrown() {
		Assertions.assertThrows(MissingKeyException.class, () -> accountManager.addCost("id2", 10.0));
		Assertions.assertThrows(MissingKeyException.class, () -> accountManager.getAccount("id2"));
	}

}
