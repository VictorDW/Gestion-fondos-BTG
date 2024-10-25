package com.victordw.btg.adapter.driven.mongodb.mapper;

import com.victordw.btg.adapter.driven.mongodb.document.TransactionDoc;
import com.victordw.btg.domain.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ITransactionMapper {

	TransactionDoc toDoc(Transaction transaction);
	Transaction toModel(TransactionDoc transactionDoc);
}
