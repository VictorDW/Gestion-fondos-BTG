package com.victordw.btg.adapter.driving.http.controller;

import com.victordw.btg.adapter.driving.http.dto.request.FundCancellationRequest;
import com.victordw.btg.adapter.driving.http.dto.request.FundSubscribedRequest;
import com.victordw.btg.adapter.driving.http.mapper.request.IFundSubscribeMapperRequest;
import com.victordw.btg.domain.api.IClientServicePort;
import com.victordw.btg.domain.model.FundSubscribed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

	private final IFundSubscribeMapperRequest fundSubscribeMapperRequest;
	private final IClientServicePort clientServicePort;

	@PostMapping("/{id}/subscription")
	ResponseEntity<Void> subscribeFund(
			@PathVariable("id") String clientId,
			@RequestBody FundSubscribedRequest request
	) {
		FundSubscribed fundSubscribed = fundSubscribeMapperRequest.toModel(request);
		clientServicePort.addSubscription(clientId, fundSubscribed);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}/unsubcription")
	ResponseEntity<Void> cancellationFund(
			@PathVariable("id") String clientId,
			@RequestBody FundCancellationRequest request
	) {
		clientServicePort.cancellationSubscription(clientId, request.fundId());
		return ResponseEntity.ok().build();
	}

}
