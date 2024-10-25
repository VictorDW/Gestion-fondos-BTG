package com.victordw.btg.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
public class Client {
	private final String id;
	private final String name;
	private final String email;
	private final String cellPhone;
	private final String dni;
	@Setter
	private BigDecimal availableBalance;
	private final String notificationPreference;
	private final List<FundSubscribed> fundsSubscribed;
}
