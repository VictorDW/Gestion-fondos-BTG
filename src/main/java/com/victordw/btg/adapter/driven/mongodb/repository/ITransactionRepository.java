package com.victordw.btg.adapter.driven.mongodb.repository;

import com.victordw.btg.adapter.driven.mongodb.document.TransactionDoc;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ITransactionRepository extends MongoRepository<TransactionDoc, String> {

	List<TransactionDoc> findAllByClientId(String clientId, Sort sort);
}
