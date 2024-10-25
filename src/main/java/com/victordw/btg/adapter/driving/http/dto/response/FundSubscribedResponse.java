package com.victordw.btg.adapter.driving.http.dto.response;

import java.math.BigDecimal;

public record FundSubscribedResponse(
		Long fundId,
		String name,
		BigDecimal investmentAmount
) {
}
