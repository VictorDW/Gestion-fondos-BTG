package com.victordw.btg.domain.api;

import com.victordw.btg.domain.model.Client;
import com.victordw.btg.domain.model.FundSubscribed;
import com.victordw.btg.domain.model.Transaction;

import java.util.List;

public interface IClientServicePort {

	void addSubscription(String clientId, FundSubscribed fundSubscribed);
	void cancellationSubscription(String clientId, Long fundId);
	List<FundSubscribed> listAssociatedFunds(String clientId);
	Client getClientInformation(String clientId);
	List<Transaction> getAllTransaction(String clientId);
}
