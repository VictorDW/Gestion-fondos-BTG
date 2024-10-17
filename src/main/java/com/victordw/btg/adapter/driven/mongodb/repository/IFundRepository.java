package com.victordw.btg.adapter.driven.mongodb.repository;

import com.victordw.btg.adapter.driven.mongodb.document.InvestmentFundDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IFundRepository extends MongoRepository<InvestmentFundDoc, Long> {
}
