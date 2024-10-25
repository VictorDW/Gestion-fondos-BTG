package com.victordw.btg.domain.api;

import com.victordw.btg.domain.model.InvestmentFund;

public interface IFundService {

	InvestmentFund getFundById(Long fundId);
}
