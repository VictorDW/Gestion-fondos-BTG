package com.victordw.btg.domain.api.usecase;

import com.victordw.btg.domain.api.IFundServicesPort;
import com.victordw.btg.domain.model.InvestmentFund;
import com.victordw.btg.domain.spi.IFundPersistenPort;
import com.victordw.btg.domain.util.OrderData;
import com.victordw.btg.domain.util.OrderUtil;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
public class FundUseCase implements IFundServicesPort {

	private final IFundPersistenPort fundPersistenPort;

	@Override
	public List<InvestmentFund> getAllFund(BigDecimal maxAmount, String category, String direction) {
		OrderData orderData = OrderUtil.definiOrderData(direction);
		return fundPersistenPort.getAllFund(maxAmount, category, orderData);
	}
}
