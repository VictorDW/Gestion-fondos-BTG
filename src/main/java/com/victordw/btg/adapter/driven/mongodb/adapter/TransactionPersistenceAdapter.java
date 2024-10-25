package com.victordw.btg.adapter.driven.mongodb.adapter;

import com.victordw.btg.adapter.driven.mongodb.document.TransactionDoc;
import com.victordw.btg.adapter.driven.mongodb.mapper.ITransactionMapper;
import com.victordw.btg.adapter.driven.mongodb.repository.ITransactionRepository;
import com.victordw.btg.configuration.Constants;
import com.victordw.btg.domain.model.Transaction;
import com.victordw.btg.domain.spi.ITransactionPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionPersistenceAdapter implements ITransactionPersistencePort {

	private final ITransactionRepository transactionRepository;
	private final ITransactionMapper transactionMapper;

	@Override
	public void saveTransaction(Transaction transaction) {
		TransactionDoc transactionDoc = transactionMapper.toDoc(transaction);
		transactionRepository.save(transactionDoc);
	}

	@Override
	public List<Transaction> getAllTransactionByClientId(String clientId) {

		Sort.Direction direction = Sort.Direction.DESC;
		Sort sort = Sort.by(direction, Constants.NAME_FIELD_DATA_REGISTRATION);

		return transactionRepository.findAllByClientId(clientId, sort)
				.stream()
				.map(transactionMapper::toModel)
				.limit(Constants.DATA_LIMIT)
				.toList();
	}
}
