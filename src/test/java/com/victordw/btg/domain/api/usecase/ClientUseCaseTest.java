package com.victordw.btg.domain.api.usecase;


import com.victordw.btg.domain.api.IFundService;
import com.victordw.btg.domain.api.ITransactionService;
import com.victordw.btg.domain.exception.FundAlreadyExistsException;
import com.victordw.btg.domain.exception.InvestmentAmountException;
import com.victordw.btg.domain.exception.NotFoundException;
import com.victordw.btg.domain.model.Client;
import com.victordw.btg.domain.model.FundSubscribed;
import com.victordw.btg.domain.model.InvestmentFund;
import com.victordw.btg.domain.model.Transaction;
import com.victordw.btg.domain.spi.IClientPersistencePort;
import com.victordw.btg.domain.spi.ISendNotificationPort;
import com.victordw.btg.domain.util.ConstantDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ClientUseCaseTest {

	@Mock
	private IClientPersistencePort clientPersistencePort;

	@Mock
	private IFundService fundService;

	@Mock
	private ITransactionService transactionService;

	@Mock
	private ISendNotificationPort notificationPort;

	@Mock
	private Function<String, ISendNotificationPort> methodSend;

	@InjectMocks
	private ClientUseCase clientUseCase;

	String clientId;

	@BeforeEach
	void setup() {
		clientId = "client-123";
	}

	@Test
	@DisplayName("must correctly add a subscription with sms notification")
	void test1() {

		// GIVEN
		BigDecimal deductAmount = new BigDecimal("300000");
		FundSubscribed fundSubscribed = FundSubscribed.builder()
				.fundId(1L)
				.investmentAmount(new BigDecimal("200000"))
				.build();
		Client client = Client.builder()
				.id(clientId)
				.availableBalance(new BigDecimal("500000"))
				.cellPhone("+573186360926")
				.notificationPreference("sms")
				.fundsSubscribed(new ArrayList<>())
				.build();
		InvestmentFund fund = InvestmentFund.builder()
				.id(1L)
				.name("FPV_BTG_PACTUAL_RECAUDADORA")
				.minimumAmount(new BigDecimal("75000"))
				.category("FVP")
				.build();
		String message = String.format(ConstantDomain.MESSAGE_NOTIFICATION, fund.getName());

		given(clientPersistencePort.getClient(clientId)).willReturn(Optional.of(client));
		given(fundService.getFundById(fundSubscribed.getFundId())).willReturn(fund);
		given(methodSend.apply(client.getNotificationPreference())).willReturn(notificationPort);


		// WHEN
		clientUseCase.addSubscription(clientId, fundSubscribed);

		// THAT
		assertEquals(deductAmount, client.getAvailableBalance());
		assertTrue(client.getFundsSubscribed().contains(fundSubscribed));
		verify(clientPersistencePort).saveClient(client);
		verify(transactionService).registerTransaction(
				clientId,
				fund.getName(),
				fundSubscribed.getInvestmentAmount(),
				ConstantDomain.TYPE_OPENING
		);
		verify(notificationPort).sendNotification(client.getCellPhone(), message);
	}

	@Test
	@DisplayName("must throw an exception when the client does not exist")
	void test2() {
		// GIVEN
		FundSubscribed fundSubscribed = FundSubscribed.builder()
				.fundId(1L)
				.investmentAmount(new BigDecimal("200000"))
				.build();

		given(clientPersistencePort.getClient(clientId)).willReturn(Optional.empty());

		// WHEN
		NotFoundException exception = assertThrows(NotFoundException.class,
				() -> clientUseCase.addSubscription(clientId, fundSubscribed)
		);

		//THAT
		assertEquals(String.format(
				ConstantDomain.NOT_FOUND_MESSAGE, ConstantDomain.Utils.CLIENT.get(), clientId), exception.getMessage());
		verify(clientPersistencePort, never()).saveClient(any());
	}

	@Test
	@DisplayName("should throw an exception when the available balance is not sufficient")
	void test3() {
		// GIVEN
		FundSubscribed fundSubscribed = FundSubscribed.builder()
				.fundId(1L)
				.investmentAmount(new BigDecimal("75000"))
				.build();
		Client client = Client.builder()
				.id(clientId)
				.availableBalance(new BigDecimal("50000"))
				.fundsSubscribed(new ArrayList<>())
				.build();
		InvestmentFund fund = InvestmentFund.builder()
				.id(1L)
				.name("FPV_BTG_PACTUAL_RECAUDADORA")
				.minimumAmount(new BigDecimal("75000"))
				.category("FVP")
				.build();

		given(clientPersistencePort.getClient(clientId)).willReturn(Optional.of(client));
		given(fundService.getFundById(fundSubscribed.getFundId())).willReturn(fund);

		// WHEN
		InvestmentAmountException exception = assertThrows(InvestmentAmountException.class,
				() -> clientUseCase.addSubscription(clientId, fundSubscribed)
		);

		// THAT
		assertEquals(String.format(ConstantDomain.INSUFFICIENT_BALANCE, fund.getName()), exception.getMessage());
		verify(clientPersistencePort, never()).saveClient(any());
	}

	@Test
	@DisplayName("an exception must be triggered when the investment amount is less than the minimum amount allowed by the fund.")
	void test4() {
		// GIVEN
		FundSubscribed fundSubscribed = FundSubscribed.builder()
				.fundId(1L)
				.investmentAmount(new BigDecimal("50000"))
				.build();
		Client client = Client.builder()
				.id(clientId)
				.availableBalance(new BigDecimal("100000"))
				.fundsSubscribed(new ArrayList<>())
				.build();
		InvestmentFund fund = InvestmentFund.builder()
				.id(1L)
				.name("FPV_BTG_PACTUAL_RECAUDADORA")
				.minimumAmount(new BigDecimal("75000"))
				.category("FVP")
				.build();

		given(clientPersistencePort.getClient(clientId)).willReturn(Optional.of(client));
		given(fundService.getFundById(fundSubscribed.getFundId())).willReturn(fund);

		// WHEN
		InvestmentAmountException exception = assertThrows(InvestmentAmountException.class,
				() -> clientUseCase.addSubscription(clientId, fundSubscribed)
		);

		// THAT
		assertEquals(String.format(ConstantDomain.AMOUNT_TO_INVEST_IS_NOT_VALID, fund.getName()), exception.getMessage());
		verify(clientPersistencePort, never()).saveClient(any());
	}


	@Test
	@DisplayName("must throw an exception when you want to subscribe to a fund to which you are already subscribed")
	void test5() {
		// GIVEN
		FundSubscribed fundSubscribed = FundSubscribed.builder()
				.fundId(1L)
				.investmentAmount(new BigDecimal("75000"))
				.build();
		Client client = Client.builder()
				.id(clientId)
				.availableBalance(new BigDecimal("100000"))
				.fundsSubscribed(List.of(fundSubscribed))
				.build();
		InvestmentFund fund = InvestmentFund.builder()
				.id(1L)
				.name("FPV_BTG_PACTUAL_RECAUDADORA")
				.minimumAmount(new BigDecimal("75000"))
				.category("FVP")
				.build();

		given(clientPersistencePort.getClient(clientId)).willReturn(Optional.of(client));
		given(fundService.getFundById(fundSubscribed.getFundId())).willReturn(fund);

		// WHEN -THAT
	 assertThrows(FundAlreadyExistsException.class,
				() -> clientUseCase.addSubscription(clientId, fundSubscribed)
		);
	}


	@Test
	@DisplayName("must correctly add a subscription with mail notification")
	void test6() {

		// GIVEN
		BigDecimal deductAmount = new BigDecimal("300000");
		FundSubscribed fundSubscribed = FundSubscribed.builder()
				.fundId(1L)
				.investmentAmount(new BigDecimal("200000"))
				.build();
		Client client = Client.builder()
				.id(clientId)
				.availableBalance(new BigDecimal("500000"))
				.cellPhone("+573186360926")
				.notificationPreference("correo")
				.fundsSubscribed(new ArrayList<>())
				.build();
		InvestmentFund fund = InvestmentFund.builder()
				.id(1L)
				.name("FPV_BTG_PACTUAL_RECAUDADORA")
				.minimumAmount(new BigDecimal("75000"))
				.category("FVP")
				.build();
		String message = String.format(ConstantDomain.MESSAGE_NOTIFICATION, fund.getName());

		given(clientPersistencePort.getClient(clientId)).willReturn(Optional.of(client));
		given(fundService.getFundById(fundSubscribed.getFundId())).willReturn(fund);
		given(methodSend.apply(client.getNotificationPreference())).willReturn(notificationPort);


		// WHEN
		clientUseCase.addSubscription(clientId, fundSubscribed);

		// THAT
		assertEquals(deductAmount, client.getAvailableBalance());
		assertTrue(client.getFundsSubscribed().contains(fundSubscribed));
		verify(clientPersistencePort).saveClient(client);
		verify(transactionService).registerTransaction(
				clientId,
				fund.getName(),
				fundSubscribed.getInvestmentAmount(),
				ConstantDomain.TYPE_OPENING
		);
		verify(notificationPort).sendNotification(client.getEmail(), ConstantDomain.SUBJECT, message);
	}

	@Test
	@DisplayName("must cancel a subscription and add the amount to the available balance")
	void test7() {

		// GIVEN
		Long fundId = 1L;
		BigDecimal aggregateAmount = new BigDecimal("500000");
		FundSubscribed fundSubscribed = FundSubscribed.builder()
				.fundId(1L)
				.investmentAmount(new BigDecimal("200000"))
				.build();
		Client client = Client.builder()
				.id(clientId)
				.availableBalance(new BigDecimal("300000"))
				.cellPhone("+573186360926")
				.notificationPreference("sms")
				.fundsSubscribed(new ArrayList<>(Collections.singleton(fundSubscribed)))
				.build();
		InvestmentFund fund = InvestmentFund.builder()
				.id(1L)
				.name("FPV_BTG_PACTUAL_RECAUDADORA")
				.minimumAmount(new BigDecimal("75000"))
				.category("FVP")
				.build();

		given(clientPersistencePort.getClient(clientId)).willReturn(Optional.of(client));
		given(fundService.getFundById(fundSubscribed.getFundId())).willReturn(fund);

		// WHEN
		clientUseCase.cancellationSubscription(clientId, fundId);

		// THAT
		assertEquals(aggregateAmount, client.getAvailableBalance());
		assertFalse(client.getFundsSubscribed().contains(fundSubscribed));
		verify(clientPersistencePort).saveClient(client);
		verify(transactionService).registerTransaction(
				clientId,
				fund.getName(),
				fundSubscribed.getInvestmentAmount(),
				ConstantDomain.TYPE_CANCELLATION
		);
	}

	@Test
	@DisplayName("must throw an exception when you want to cancel a fund to which you are not subscribed. ")
	void test8() {
		// GIVEN
		Long fundId = 1L;
		Client client = Client.builder()
				.id(clientId)
				.availableBalance(new BigDecimal("300000"))
				.fundsSubscribed(new ArrayList<>())
				.build();
		InvestmentFund fund = InvestmentFund.builder()
				.id(1L)
				.name("FPV_BTG_PACTUAL_RECAUDADORA")
				.minimumAmount(new BigDecimal("75000"))
				.category("FVP")
				.build();

		given(clientPersistencePort.getClient(clientId)).willReturn(Optional.of(client));
		given(fundService.getFundById(fundId)).willReturn(fund);

		// WHEN -THAT
		assertThrows(NotFoundException.class,
				() -> clientUseCase.cancellationSubscription(clientId, fundId)
		);
	}

	@Test
	@DisplayName("must return a list of subscribed funds")
	void test9() {
		//GIVEN
		Client client = Client.builder()
				.id(clientId)
				.name("Juan Perez")
				.availableBalance(new BigDecimal("300000"))
				.fundsSubscribed(new ArrayList<>())
				.build();

		given(clientPersistencePort.getClient(clientId)).willReturn(Optional.of(client));

		//WHEN
		List<FundSubscribed> resul = clientUseCase.listAssociatedFunds(clientId);

		//THAT
		assertNotNull(resul);
	}

	@Test
	@DisplayName("must return a list of subscribed funds")
	void test10() {
		//GIVEN
		Client client = Client.builder()
				.id(clientId)
				.name("Juan Perez")
				.availableBalance(new BigDecimal("300000"))
				.build();

		given(clientPersistencePort.getClient(clientId)).willReturn(Optional.of(client));

		//WHEN
		Client resul = clientUseCase.getClientInformation(clientId);

		//THAT
		assertNotNull(resul);
		assertEquals(client, resul);
	}

	@Test
	@DisplayName("must return a list of transactions")
	void test11() {

		//GIVEN
		Transaction transaction = Transaction.builder()
				.id("671184d7d4051e5ddcb389f7")
				.clientId("670ff7facaba27fa00d268ae")
				.nameFund("DEUDAPRIVADA")
				.type(ConstantDomain.TYPE_CANCELLATION)
				.mount(new BigDecimal(50000))
				.dateRegistration(LocalDateTime.now())
				.build();

		given(transactionService.getAllTransaction(clientId)).willReturn(List.of(transaction));

		//WHEN
		List<Transaction> resul = clientUseCase.getAllTransaction(clientId);

		//THAT
		assertNotNull(resul);
	}

}