package at.ac.univie.dse.msbill.logic;

import java.util.List;
import java.util.logging.Logger;

import at.ac.univie.dse.msbill.data.account.AccountRepository;
import at.ac.univie.dse.msbill.data.datatypes.Account;
import at.ac.univie.dse.msbill.exception.MissingKeyException;

public class AccountManager {

	private AccountRepository accountRepo;
	private static Logger logger = Logger.getLogger(AccountManager.class.getName());

	public AccountManager(AccountRepository accountRepo) {
		this.accountRepo = accountRepo;
	}

	/**
	 * Checks if clntInstanceId already exists in database and creates new account
	 * only if it doesn't.
	 */
	public void addClnt(String clntInstanceId) {
		if (!accountRepo.isInstanceIdPresent(clntInstanceId)) {
			accountRepo.createAccount(clntInstanceId);
		} else {
			logger.warning("Client account already stored with id " + clntInstanceId);
		}
	}

	/**
	 * @throws MissingKeyException If clntInstanceId is not present in database
	 */
	public void addCost(String clntInstanceId, Double amount) {
		Double oldBalance = accountRepo.getAccountBalance(clntInstanceId);
		Double newBalance = oldBalance + amount;
		accountRepo.setAccountBalance(clntInstanceId, newBalance);
		logger.info("Cost added to client account with id " + clntInstanceId + ", amount = " + amount
				+ ", new booked balance = " + newBalance);
	}

	/**
	 * @throws MissingKeyException If clntInstanceId is not present in database
	 */
	public Account getAccount(String clntInstanceId) {
		return accountRepo.getAccount(clntInstanceId);
	}

	public List<Account> getAllAccounts() {
		return accountRepo.getAllAccounts();
	}

}
