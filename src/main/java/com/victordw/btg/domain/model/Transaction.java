package com.victordw.btg.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
public class Transaction {

	private String id;
	private String clientId;
	private String nameFund;
	private String type;
	private BigDecimal mount;
	private LocalDateTime dateRegistration;
}
