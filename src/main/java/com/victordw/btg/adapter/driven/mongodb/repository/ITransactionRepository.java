package com.victordw.btg.adapter.driven.mongodb.repository;

import com.victordw.btg.adapter.driven.mongodb.document.TransactionDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ITransactionRepository extends MongoRepository<TransactionDoc, String> {
}
