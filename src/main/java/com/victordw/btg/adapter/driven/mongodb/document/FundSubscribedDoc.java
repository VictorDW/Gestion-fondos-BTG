package com.victordw.btg.adapter.driven.mongodb.document;

import java.math.BigDecimal;

public record FundSubscribedDoc(
		Long fundId,
		BigDecimal investmentAmount
) {
}
