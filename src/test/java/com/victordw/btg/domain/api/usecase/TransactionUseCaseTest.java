package com.victordw.btg.domain.api.usecase;

import com.victordw.btg.domain.spi.ITransactionPersistencePort;
import com.victordw.btg.domain.util.ConstantDomain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionUseCaseTest {

	@Mock
	private ITransactionPersistencePort transactionPersistencePort;

	@InjectMocks
	private TransactionUseCase transactionUseCase;

	@Test
	@DisplayName("should save a transaction successfully")
	void test1() {

		// GIVEN
		String clientId = "client-123";
		String nameFund = "FPV_BTG_PACTUAL_RECAUDADORA";
		BigDecimal amount = new BigDecimal("100000");
		String type = ConstantDomain.TYPE_OPENING;

		// WHEN
		transactionUseCase.registerTransaction(clientId, nameFund, amount, type);

		// THAT
		verify(transactionPersistencePort, times(1)).saveTransaction(argThat(transaction ->
				transaction.getClientId().equals(clientId) &&
						transaction.getNameFund().equals(nameFund) &&
						transaction.getMount().equals(amount) &&
						transaction.getType().equals(type) &&
						transaction.getDateRegistration() != null
		));
	}
}