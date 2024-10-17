package com.victordw.btg.adapter.driven.mongodb.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionDoc {

	@Id
	private String id;

	@Field("client_id")
	private String clientId;

	@Field("name_fund")
	private String nameFund;

	@Field("type")
	private String type;

	@Field("mount")
	private BigDecimal mount;

	@Field("date_registration")
	private LocalDateTime dateRegistration;
}
