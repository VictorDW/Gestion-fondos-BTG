package com.victordw.btg.domain.api;

import com.victordw.btg.domain.model.FundSubscribed;

public interface ISubscriptionServicePort {

	void addSubscription(String clientId, FundSubscribed fundSubscribed);
}
