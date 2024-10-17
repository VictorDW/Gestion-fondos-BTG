package com.victordw.btg.adapter.driving.http.controller;

import com.victordw.btg.adapter.driving.http.dto.request.FundSubscribedRequest;
import com.victordw.btg.adapter.driving.http.mapper.request.IFundSubscribeMapperRequest;
import com.victordw.btg.domain.api.ISubscriptionServicePort;
import com.victordw.btg.domain.model.FundSubscribed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class SubscriptionController {

	private final IFundSubscribeMapperRequest fundSubscribeMapperRequest;
	private final ISubscriptionServicePort clientServicePort;

	@PostMapping("/{id}")
	ResponseEntity<Void> subscribeFund(
			@PathVariable("id") String clientId,
			@RequestBody FundSubscribedRequest fundSubscribedRequest
	) {
		FundSubscribed fundSubscribed = fundSubscribeMapperRequest.toModel(fundSubscribedRequest);
		clientServicePort.addSubscription(clientId, fundSubscribed);
		return ResponseEntity.ok().build();
	}
}
