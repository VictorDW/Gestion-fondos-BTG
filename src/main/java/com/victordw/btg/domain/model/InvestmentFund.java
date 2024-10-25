package com.victordw.btg.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class InvestmentFund {

	private final Long id;
	private final String name;
	private final BigDecimal minimumAmount;
	private final String category;
}
