package at.ac.univie.dse.msbill.data.account;

import java.util.List;

import at.ac.univie.dse.msbill.data.datatypes.Account;
import at.ac.univie.dse.msbill.exception.ExistingKeyException;
import at.ac.univie.dse.msbill.exception.MissingKeyException;

public class AccountRepository {

	private static AccountRepository instance = null;

	private AccountDao dao;

	private AccountRepository(AccountDao dao) {
		this.dao = dao;
	}

	public static AccountRepository getInstance(AccountDao dao) {
		synchronized (AccountRepository.class) {
			if (instance == null) {
				instance = new AccountRepository(dao);
			}
		}
		return instance;
	}

	public boolean isInstanceIdPresent(String clntInstanceId) {
		return dao.getAccount(clntInstanceId) != null;
	}

	/**
	 * @throws ExistingKeyException if clntInstanceId is already present in the
	 *                              database
	 */
	public void createAccount(String clntInstanceId) {
		if (isInstanceIdPresent(clntInstanceId)) {
			throw new ExistingKeyException(clntInstanceId);
		}
		Account newAccount = new Account(clntInstanceId);
		dao.createAccount(newAccount);
	}

	/**
	 * @throws MissingKeyException if clntInstanceId does not exist in the database
	 */
	public void setAccountBalance(String clntInstanceId, Double newBalance) {
		Account account = retrieveAccountOrThrowException(clntInstanceId);
		account.setBookedBalance(newBalance);
		dao.updateAccount(account);
	}

	/**
	 * @throws MissingKeyException if clntInstanceId does not exist in the database
	 */
	public Double getAccountBalance(String clntInstanceId) {
		return retrieveAccountOrThrowException(clntInstanceId).getBookedBalance();
	}

	/**
	 * @throws MissingKeyException if clntInstanceId does not exist in the database
	 */
	public Account getAccount(String clntInstanceId) {
		return retrieveAccountOrThrowException(clntInstanceId);
	}

	public List<Account> getAllAccounts() {
		return dao.getAllAccounts();
	}

	private Account retrieveAccountOrThrowException(String instanceId) {
		if (!isInstanceIdPresent(instanceId)) {
			throw new MissingKeyException(instanceId);
		}
		return dao.getAccount(instanceId);
	}

}
