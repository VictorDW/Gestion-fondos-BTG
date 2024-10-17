package com.victordw.btg.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
public class Client {

	private final String name;
	private final String email;
	private final String cellPhone;
	private final String dni;
	private final BigDecimal availableBalance;
	private final String notificationPreference;
	private final List<FundSubscribed> fundsSubscribed;
}
