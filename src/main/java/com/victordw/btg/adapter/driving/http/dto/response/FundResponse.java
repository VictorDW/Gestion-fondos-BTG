package com.victordw.btg.adapter.driving.http.dto.response;

import java.math.BigDecimal;

public record FundResponse(
		String name,
		BigDecimal minimumAmount,
		String category
) {
}
