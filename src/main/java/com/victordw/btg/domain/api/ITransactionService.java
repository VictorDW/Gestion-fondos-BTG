package com.victordw.btg.domain.api;

import com.victordw.btg.domain.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface ITransactionService {

	void registerTransaction(String clientId, String fundId, BigDecimal amount, String type);
	List<Transaction> getAllTransaction(String clientId);
}
