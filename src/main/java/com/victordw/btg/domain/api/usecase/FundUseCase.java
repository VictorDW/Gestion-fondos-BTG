package com.victordw.btg.domain.api.usecase;

import com.victordw.btg.domain.api.IFundServiceBasic;
import com.victordw.btg.domain.api.IFundServicesPort;
import com.victordw.btg.domain.exception.NotFoundException;
import com.victordw.btg.domain.model.InvestmentFund;
import com.victordw.btg.domain.spi.IFundPersistencePort;
import com.victordw.btg.domain.util.ConstantDomain;
import com.victordw.btg.domain.util.OrderData;
import com.victordw.btg.domain.util.OrderUtil;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
public class FundUseCase implements IFundServicesPort, IFundServiceBasic {

	private final IFundPersistencePort fundPersistencePort;

	@Override
	public List<InvestmentFund> getAllFund(BigDecimal maxAmount, String category, String direction) {
		OrderData orderData = OrderUtil.definiOrderData(direction);
		return fundPersistencePort.getAllFund(maxAmount, category, orderData);
	}


	@Override
	public InvestmentFund getFundById(Long fundId) {
		return fundPersistencePort
				.getFundById(fundId)
				.orElseThrow(()-> new NotFoundException(String.format(
						ConstantDomain.NOT_FOUND_MESSAGE,
						ConstantDomain.Utils.FUND.get(),
						fundId)
				));
	}
}
