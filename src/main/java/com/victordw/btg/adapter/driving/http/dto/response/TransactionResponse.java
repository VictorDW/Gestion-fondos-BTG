package com.victordw.btg.adapter.driving.http.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
		String id,
		String nameFund,
		String type,
		BigDecimal mount,
		LocalDateTime dateRegistration
) {
}
