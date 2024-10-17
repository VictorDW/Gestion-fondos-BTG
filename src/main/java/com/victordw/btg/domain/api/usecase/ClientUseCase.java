package com.victordw.btg.domain.api.usecase;

import com.victordw.btg.domain.api.IClientServicePort;
import com.victordw.btg.domain.api.IFundServiceBasic;
import com.victordw.btg.domain.exception.InvestmentAmountException;
import com.victordw.btg.domain.exception.NotFoundException;
import com.victordw.btg.domain.model.Client;
import com.victordw.btg.domain.model.FundSubscribed;
import com.victordw.btg.domain.model.InvestmentFund;
import com.victordw.btg.domain.spi.IClientPersistencePort;
import com.victordw.btg.domain.util.ConstantDomain;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class ClientUseCase implements IClientServicePort {

	private final IClientPersistencePort clientPersistencePort;
	private final IFundServiceBasic fundService;

	@Override
	public void addSubscription(String clientId, FundSubscribed fundSubscribed) {

		Client client = clientPersistencePort.getClient(clientId)
				.orElseThrow(()-> new NotFoundException(
						String.format(
								ConstantDomain.NOT_FOUND_MESSAGE,
								ConstantDomain.Utils.CLIENT.get(),
								clientId)
				));

		InvestmentFund fund = fundService.getFundById(fundSubscribed.fundId());

		this.validateAmountToInvestedAndInitialBalance(fundSubscribed.investmentAmount(), fund, client.getAvailableBalance());

		client.getFundsSubscribed().add(fundSubscribed);
		clientPersistencePort.saveClient(client);

	}

	private void validateAmountToInvestedAndInitialBalance(
			BigDecimal amountToInvest,
			InvestmentFund investmentFund,
			BigDecimal availableBalance
	) {
		if (amountToInvest.compareTo(investmentFund.getMinimumAmount()) < ConstantDomain.NUMBER_ZERO) {
			this.investmentAmountException(ConstantDomain.AMOUNT_TO_INVEST_IS_NOT_VALID, investmentFund.getName());
		}
		if (amountToInvest.compareTo(availableBalance) > ConstantDomain.NUMBER_ZERO) {
				this.investmentAmountException(ConstantDomain.INSUFFICIENT_BALANCE, investmentFund.getName());
		}
	}

	private void investmentAmountException(String message, String fund) {
		throw new InvestmentAmountException(String.format(message, fund));
	}

}
