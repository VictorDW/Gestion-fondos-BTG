package com.victordw.btg.domain.api.usecase;

import com.victordw.btg.domain.api.ITransactionService;
import com.victordw.btg.domain.model.Transaction;
import com.victordw.btg.domain.spi.ITransactionPersistencePort;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class TransactionUseCase implements ITransactionService {

	private final ITransactionPersistencePort transactionPersistencePort;

	@Override
	public void registerTransaction(String clientId, String nameFund, BigDecimal amount, String type) {
		Transaction transaction = createTransaction(clientId, nameFund, amount, type);
		transactionPersistencePort.saveTransaction(transaction);
	}

	private Transaction createTransaction(String clientId, String nameFund, BigDecimal amount, String type) {
		return Transaction.builder()
				.clientId(clientId)
				.nameFund(nameFund)
				.type(type)
				.mount(amount)
				.dateRegistration(LocalDateTime.now())
				.build();
	}

	@Override
	public List<Transaction> getAllTransaction(String clientId) {
		return transactionPersistencePort.getAllTransactionByClientId(clientId);
	}
}
