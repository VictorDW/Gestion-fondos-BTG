package com.victordw.btg.adapter.driven.mongodb.document;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "client")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientDoc {

	@Id
	private String id;

	@Field("name")
	private String name;

	@Field("dni")
	private String dni;

	@Field("email")
	private String email;

	@Field("cellphone")
	private String cellPhone;

	@Field("available_balance")
	private BigDecimal availableBalance;

	@Field("funds_subscribed")
	private List<FundSubscribedDoc> fundsSubscribed;

	@Field("notification_preference")
	private String notificationPreference;

}
