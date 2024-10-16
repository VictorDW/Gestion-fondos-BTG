package com.victordw.btg.adapter.driven.mongodb.repository;

import com.victordw.btg.adapter.driven.mongodb.document.InvestmentFundDoc;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigDecimal;
import java.util.List;

public interface IRepositoryInvestmentFund extends MongoRepository<InvestmentFundDoc, String> {

	List<InvestmentFundDoc> findByMinimumAmountContainingAndCategoryContaining(BigDecimal amount, String category, Sort sort);
}
