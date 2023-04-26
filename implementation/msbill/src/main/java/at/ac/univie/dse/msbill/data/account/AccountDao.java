package at.ac.univie.dse.msbill.data.account;

import java.util.List;

import at.ac.univie.dse.msbill.data.datatypes.Account;

public interface AccountDao {

	void createAccount(Account account);

	void updateAccount(Account account);

	List<Account> getAllAccounts();

	/**
	 * @return Account if clntInstanceId exists in database, otherwise null
	 */
	Account getAccount(String instanceId);

}
