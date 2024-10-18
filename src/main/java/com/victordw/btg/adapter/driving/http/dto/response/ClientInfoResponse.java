package com.victordw.btg.adapter.driving.http.dto.response;

import java.math.BigDecimal;

public record ClientInfoResponse(
		String id,
		String name,
		BigDecimal availableBalance
) {
}
