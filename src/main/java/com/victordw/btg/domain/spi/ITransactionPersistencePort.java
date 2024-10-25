package com.victordw.btg.domain.spi;

import com.victordw.btg.domain.model.Transaction;

import java.util.List;

public interface ITransactionPersistencePort {
	void saveTransaction(Transaction transaction);
	List<Transaction> getAllTransactionByClientId(String clientId);
}
