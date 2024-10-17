package com.victordw.btg.domain.api;

import java.math.BigDecimal;

public interface ITransactionService {

	void registerTransaction(String clientId, String fundId, BigDecimal amount, String type);
}
