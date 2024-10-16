package com.victordw.btg.adapter.driven.mongodb.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(collection = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transaction {

	@Id
	private String id;

	@Field("email_client")
	private String email;

	@Field("fund_id")
	private Long fundId;

	@Field("type")
	private String type;

	@Field("mount")
	private BigDecimal mount;

	@Field("date_registration")
	private LocalDate dateRegistration;
}
