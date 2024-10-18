package com.victordw.btg.adapter.driving.http.controller;

import com.victordw.btg.adapter.driving.http.dto.request.FundCancellationRequest;
import com.victordw.btg.adapter.driving.http.dto.request.FundSubscribedRequest;
import com.victordw.btg.adapter.driving.http.dto.response.ClientInfoResponse;
import com.victordw.btg.adapter.driving.http.dto.response.FundSubscribedResponse;
import com.victordw.btg.adapter.driving.http.dto.response.TransactionResponse;
import com.victordw.btg.adapter.driving.http.mapper.request.IClientMapperRequest;
import com.victordw.btg.adapter.driving.http.mapper.response.IClientMapperResponse;
import com.victordw.btg.domain.api.IClientServicePort;
import com.victordw.btg.domain.model.Client;
import com.victordw.btg.domain.model.FundSubscribed;
import com.victordw.btg.domain.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

	private final IClientMapperRequest clientMapperRequest;
	private final IClientMapperResponse clientMapperResponse;
	private final IClientServicePort clientServicePort;
	private final IClientServicePort clientServicesPort;

	@PostMapping("/{id}/subscription")
	ResponseEntity<Void> subscribeFund(
			@PathVariable("id") String clientId,
			@RequestBody FundSubscribedRequest request
	) {
		FundSubscribed fundSubscribed = clientMapperRequest.toModel(request);
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

	@GetMapping("/{id}/subscriptions")
	ResponseEntity<List<FundSubscribedResponse>> getSubscriptions(@PathVariable("id") String clientId) {
		List<FundSubscribed> funds = clientServicesPort.listAssociatedFunds(clientId);
		return ResponseEntity.ok(clientMapperResponse.toFundsSubscriptionDto(funds));
	}

	@GetMapping("/{id}")
	ResponseEntity<ClientInfoResponse> getInfoClient(@PathVariable("id") String clientId) {
		Client client = clientServicesPort.getClientInformation(clientId);
		return ResponseEntity.ok(clientMapperResponse.toClientInfoDto(client));
	}

	@GetMapping("/{id}/transactions")
	ResponseEntity<List<TransactionResponse>> getAllTransaction(@PathVariable("id") String clientId) {
		List<Transaction> transactions = clientServicesPort.getAllTransaction(clientId);
		return ResponseEntity.ok(clientMapperResponse.toTransactionDto(transactions));
	}

}
