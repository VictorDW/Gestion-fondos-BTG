package com.victordw.btg.adapter.driven.mongodb.document;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Document(collection = "investment_fund")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InvestmentFund {

	@Id
	private String id;

	@Field("name")
	private String name;

	@Field("minimum_amount")
	private BigDecimal minimumAmount;

	@Field("category")
	private String category;

}
