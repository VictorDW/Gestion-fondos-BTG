package com.victordw.btg.adapter.driving.http.dto.request;

import java.math.BigDecimal;

public record FundSubscribedRequest(
		Long fundId,
		BigDecimal investmentAmount
) {
}
