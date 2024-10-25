package com.victordw.btg.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
public class FundSubscribed {
		private final Long fundId;
		@Setter
		private String name;
		private final BigDecimal investmentAmount;
}
