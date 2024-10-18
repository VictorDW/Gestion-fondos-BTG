package com.victordw.btg.domain.api;

import com.victordw.btg.domain.model.FundSubscribed;

public interface IClientServicePort {

	void addSubscription(String clientId, FundSubscribed fundSubscribed);
	void cancellationSubscription(String clientId, Long fundId);
}
