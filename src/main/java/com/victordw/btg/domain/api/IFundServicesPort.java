package com.victordw.btg.domain.api;

import com.victordw.btg.domain.model.InvestmentFund;

import java.math.BigDecimal;
import java.util.List;

public interface IFundServicesPort {

	 List<InvestmentFund> getAllFund(BigDecimal maxAmount, String category, String direction);
}
