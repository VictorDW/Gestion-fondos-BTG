package com.victordw.btg.adapter.driving.http.controller;

import com.victordw.btg.adapter.driving.http.dto.response.FundResponse;
import com.victordw.btg.adapter.driving.http.mapper.response.IFundResponseMapper;
import com.victordw.btg.domain.api.IFundServicesPort;
import com.victordw.btg.domain.model.InvestmentFund;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/fund")
@RequiredArgsConstructor
public class FundController {

	private final IFundServicesPort fundServicesPort;
	private final IFundResponseMapper fundResponseMapper;

	@GetMapping
	public ResponseEntity<List<FundResponse>> getAllFund(
			@RequestParam(required = false) String category,
			@RequestParam(required = false) BigDecimal maxAmount,
			@RequestParam(required = false) String direction
	) {
		List<InvestmentFund> investmentFund = fundServicesPort.getAllFund(maxAmount, category, direction);
		List<FundResponse> responses = fundResponseMapper.toDto(investmentFund);
		return ResponseEntity.ok(responses);
	}
}
