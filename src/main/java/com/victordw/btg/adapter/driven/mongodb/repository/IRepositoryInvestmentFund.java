package com.victordw.btg.adapter.driven.mongodb.repository;

import com.victordw.btg.adapter.driven.mongodb.document.InvestmentFund;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigDecimal;
import java.util.List;

public interface IRepositoryInvestmentFund extends MongoRepository<InvestmentFund, String> {

	List<InvestmentFund> findByMinimumAmountContainingAndCategoryContaining(BigDecimal amount, String category, Sort sort);
}
