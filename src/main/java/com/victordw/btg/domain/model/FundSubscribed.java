package com.victordw.btg.domain.model;

import java.math.BigDecimal;

public record FundSubscribed(
		Long fundId,
		BigDecimal investmentAmount
) {
}
