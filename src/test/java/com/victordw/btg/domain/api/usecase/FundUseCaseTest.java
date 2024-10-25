package com.victordw.btg.domain.api.usecase;

import com.victordw.btg.domain.exception.NotFoundException;
import com.victordw.btg.domain.model.InvestmentFund;
import com.victordw.btg.domain.spi.IFundPersistencePort;
import com.victordw.btg.domain.util.ConstantDomain;
import com.victordw.btg.domain.util.OrderData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FundUseCaseTest {

	@InjectMocks
	private FundUseCase fundUseCase;

	@Mock
	private IFundPersistencePort fundPersistencePort;

	@Test
	@DisplayName("Get All Fund with Ascending Order")
	void test1() {

		// GIVEN
		String direction = "ASC";
		OrderData expectedOrderData = new OrderData("ASC", "name");
		InvestmentFund fund1 = InvestmentFund.builder().id(1L).name("DEUDAPRIVADA").build();
		InvestmentFund fund2 = InvestmentFund.builder().id(2L).name("FDO-ACCIONES").build();
		List<InvestmentFund> expectedFunds = List.of(fund1, fund2);

		given(fundPersistencePort.getAllFund(null, null, expectedOrderData))
				.willReturn(expectedFunds);

		// WHEN
		List<InvestmentFund> result = fundUseCase.getAllFund(null, null, direction);

		// THAT
		assertEquals(expectedFunds, result);
		verify(fundPersistencePort).getAllFund(null, null, expectedOrderData);
	}

	@Test
	@DisplayName("Get All Fund with Descending Order")
	void test2() {

		// GIVEN
		String direction = "DESC";
		OrderData expectedOrderData = new OrderData("DESC", "name");
		InvestmentFund fund1 = InvestmentFund.builder().id(1L).name("DEUDAPRIVADA").build();
		InvestmentFund fund2 = InvestmentFund.builder().id(2L).name("FDO-ACCIONES").build();
		List<InvestmentFund> expectedFunds = List.of(fund2, fund1);

		given(fundPersistencePort.getAllFund(null, null, expectedOrderData))
				.willReturn(expectedFunds);

		// WHEN
		List<InvestmentFund> result = fundUseCase.getAllFund(null, null, direction);

		// THAT
		assertEquals(expectedFunds, result);
		verify(fundPersistencePort).getAllFund(null, null, expectedOrderData);
	}

	@Test
	@DisplayName("Get All Fund with Null Direction defaults To Ascending Order")
	void test3() {

		// GIVEN
		BigDecimal maxAmount = new BigDecimal("1500");
		String category = "Mixed";
		String direction = null;

		OrderData expectedOrderData = new OrderData("ASC", "name");
		InvestmentFund fund1 = InvestmentFund.builder().id(1L).name("DEUDAPRIVADA").build();
		InvestmentFund fund2 = InvestmentFund.builder().id(2L).name("FDO-ACCIONES").build();
		List<InvestmentFund> expectedFunds = List.of(fund1, fund2);

		given(fundPersistencePort.getAllFund(maxAmount, category, expectedOrderData))
				.willReturn(expectedFunds);

		// WHEN
		List<InvestmentFund> result = fundUseCase.getAllFund(maxAmount, category, direction);

		// THAT
		assertEquals(expectedFunds, result);
		verify(fundPersistencePort).getAllFund(maxAmount, category, expectedOrderData);
	}

	@Test
	@DisplayName("must return the investment fund")
	void test4() {

		// GIVEN
		Long fundId = 1L;
		InvestmentFund fund = InvestmentFund.builder()
				.id(1L)
				.name("FPV_BTG_PACTUAL_RECAUDADORA")
				.minimumAmount(new BigDecimal("75000"))
				.category("FVP")
				.build();

		given(fundPersistencePort.getFundById(fundId)).willReturn(Optional.of(fund));

		// WHEN
		InvestmentFund resul = fundUseCase.getFundById(fundId);

		// THAT
		assertThat(fund.getId()).isEqualTo(resul.getId());
	}

	@Test
	@DisplayName("should throw an exception when the investment fund does not exist")
	void test5() {
		// GIVEN
		Long fundId = 1L;

		given(fundPersistencePort.getFundById(fundId)).willReturn(Optional.empty());

		// WHEN
		NotFoundException exception = assertThrows(NotFoundException.class,
				() -> fundUseCase.getFundById(fundId)
		);

		//THAT
		assertEquals(String.format(
				ConstantDomain.NOT_FOUND_MESSAGE, ConstantDomain.Utils.FUND.get(), fundId), exception.getMessage());
	}

}