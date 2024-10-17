package com.victordw.btg.domain.api.usecase;

import com.victordw.btg.domain.api.ITransactionService;
import com.victordw.btg.domain.model.Transaction;
import com.victordw.btg.domain.spi.ITransactionPersistencePort;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class TransactionUseCase implements ITransactionService {

	private final ITransactionPersistencePort transactionPersistencePort;

	@Override
	public void registerTransaction(String clientId, String fundId, BigDecimal amount, String type) {
		Transaction transaction = createTransaction(clientId, fundId, amount, type);
		transactionPersistencePort.saveTransaction(transaction);
	}

	private Transaction createTransaction(String clientId, String fundId, BigDecimal amount, String type) {
		return Transaction.builder()
				.clientId(clientId)
				.nameFund(fundId)
				.type(type)
				.mount(amount)
				.dateRegistration(LocalDateTime.now())
				.build();
	}
}
