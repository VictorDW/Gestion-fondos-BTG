package com.victordw.btg.domain.spi;

import com.victordw.btg.domain.model.InvestmentFund;
import com.victordw.btg.domain.util.OrderData;

import java.math.BigDecimal;
import java.util.List;

public interface IFundPersistenPort {
	List<InvestmentFund> getAllFund(BigDecimal amount, String category, OrderData orderData);
}
