package com.victordw.btg.adapter.driven.mongodb.adapter;

import com.victordw.btg.adapter.driven.mongodb.document.InvestmentFundDoc;
import com.victordw.btg.adapter.driven.mongodb.mapper.IFundMapper;
import com.victordw.btg.adapter.driven.mongodb.repository.IFundRepository;
import com.victordw.btg.configuration.Constants;
import com.victordw.btg.domain.model.InvestmentFund;
import com.victordw.btg.domain.spi.IFundPersistencePort;
import com.victordw.btg.domain.util.OrderData;
import lombok.RequiredArgsConstructor;
import org.bson.types.Decimal128;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FundPersistenceAdapter implements IFundPersistencePort {

	private final IFundMapper fundMapper;
	private final MongoTemplate mongoTemplate;
	private final IFundRepository fundRepository;

	@Override
	public List<InvestmentFund> getAllFund(BigDecimal maxAmount, String category, OrderData orderData) {

		Query query = new Query();
		Sort.Direction direction = Sort.Direction.valueOf(orderData.direction());
		Sort sort = Sort.by(direction, orderData.orderBy());

		this.filterByMaxAmountOrCategory(query, maxAmount, category);
		query.with(sort);

		List<InvestmentFundDoc> fundDocList = mongoTemplate.find(query, InvestmentFundDoc.class);

		return fundDocList.stream()
				.map(fundMapper::toModel)
				.toList();
	}

	@Override
	public Optional<InvestmentFund> getFundById(Long fundId) {
		return fundRepository.findById(fundId).map(fundMapper::toModel);
	}

	private void filterByMaxAmountOrCategory(Query query, BigDecimal maxAmount, String category) {

		if (category != null && !category.isEmpty()) {
			query.addCriteria(Criteria.where(Constants.NAME_FIELD_CATEGORY).regex(category, Constants.FLAG_CASE_INSENSITIVE));
		}

		if (maxAmount != null) {
			query.addCriteria(Criteria.where(Constants.NAME_FIELD_MINIMUM_AMOUNT).lte(new Decimal128(maxAmount)));
		}
	}
}
