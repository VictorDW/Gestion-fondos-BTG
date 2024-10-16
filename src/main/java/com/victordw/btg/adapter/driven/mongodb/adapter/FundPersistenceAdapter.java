package com.victordw.btg.adapter.driven.mongodb.adapter;

import com.victordw.btg.domain.model.InvestmentFund;
import com.victordw.btg.domain.spi.IFundPersistenPort;
import com.victordw.btg.domain.util.OrderData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FundPersistenceAdapter implements IFundPersistenPort {

	private final MongoTemplate mongoTemplate;

	@Override
	public List<InvestmentFund> getAllFund(BigDecimal maxAmount, String category, OrderData orderData) {

		Query query = new Query();
		Sort.Direction direction = Sort.Direction.valueOf(orderData.direction());

		Sort sort = Sort.by(direction, orderData.orderBy());
		query = this.filterByMaxAmountOrCategory(query, maxAmount, category);
		query.with(sort);

		return mongoTemplate.find(query, InvestmentFund.class);
	}

	private Query filterByMaxAmountOrCategory(Query query, BigDecimal maxAmount, String category) {

		if (category != null && !category.isEmpty()) {
			query.addCriteria(Criteria.where("category").regex(category, "i"));
		}

		if(maxAmount != null) {
			query.addCriteria(Criteria.where("minimumAmount").gte(new BigDecimal(0)).lte(maxAmount));
		}

		return query;
	}
}
