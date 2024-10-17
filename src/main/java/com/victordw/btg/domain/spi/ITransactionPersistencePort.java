package com.victordw.btg.domain.spi;

import com.victordw.btg.domain.model.Transaction;

public interface ITransactionPersistencePort {
	void saveTransaction(Transaction transaction);
}
