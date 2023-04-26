package at.ac.univie.dse.msbill.data.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.ac.univie.dse.msbill.data.datatypes.Account;

public class AccountStorage implements AccountDao {

	private Map<String, Account> accounts;

	public AccountStorage(Map<String, Account> accounts) {
		this.accounts = accounts;
	}

	public AccountStorage() {
		this(new HashMap<>());
	}

	@Override
	public synchronized void createAccount(Account account) {
		accounts.put(account.getClntInstanceId(), account);
	}

	@Override
	public synchronized void updateAccount(Account account) {
		accounts.put(account.getClntInstanceId(), account);
	}

	@Override
	public synchronized Account getAccount(String instanceId) {
		return accounts.get(instanceId);
	}

	@Override
	public List<Account> getAllAccounts() {
		return new ArrayList<>(accounts.values());
	}

}
