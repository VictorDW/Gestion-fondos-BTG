package com.victordw.btg.domain.api.usecase;

import com.victordw.btg.domain.model.Transaction;
import com.victordw.btg.domain.spi.ITransactionPersistencePort;
import com.victordw.btg.domain.util.ConstantDomain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
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


	@Test
	@DisplayName("must return a list of transactions")
	void test2() {

		//GIVEN
		String clientId = "client-123";
		Transaction transaction = Transaction.builder()
				.id("671184d7d4051e5ddcb389f7")
				.clientId("670ff7facaba27fa00d268ae")
				.nameFund("DEUDAPRIVADA")
				.type(ConstantDomain.TYPE_CANCELLATION)
				.mount(new BigDecimal(50000))
				.dateRegistration(LocalDateTime.now())
				.build();

		given(transactionPersistencePort.getAllTransactionByClientId(clientId)).willReturn(List.of(transaction));

		//WHEN
		List<Transaction> resul = transactionUseCase.getAllTransaction(clientId);

		//THAT
		assertNotNull(resul);
	}
}