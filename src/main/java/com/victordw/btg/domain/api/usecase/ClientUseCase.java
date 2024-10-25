package com.victordw.btg.domain.api.usecase;

import com.victordw.btg.domain.api.IClientServicePort;
import com.victordw.btg.domain.api.IFundService;
import com.victordw.btg.domain.api.ITransactionService;
import com.victordw.btg.domain.exception.FundAlreadyExistsException;
import com.victordw.btg.domain.exception.InvestmentAmountException;
import com.victordw.btg.domain.exception.NotFoundException;
import com.victordw.btg.domain.model.Client;
import com.victordw.btg.domain.model.FundSubscribed;
import com.victordw.btg.domain.model.InvestmentFund;
import com.victordw.btg.domain.model.Transaction;
import com.victordw.btg.domain.spi.IClientPersistencePort;
import com.victordw.btg.domain.spi.ISendNotificationPort;
import com.victordw.btg.domain.util.ConstantDomain;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public class ClientUseCase implements IClientServicePort {

	private final IClientPersistencePort clientPersistencePort;
	private final IFundService fundService;
	private final ITransactionService transactionService;
	private final Function<String, ISendNotificationPort> methodSend;

	@Override
	public void addSubscription(String clientId, FundSubscribed fundSubscribed) {

		Client client = this.getClient(clientId);
		InvestmentFund fund = fundService.getFundById(fundSubscribed.getFundId());

		this.validateAmountToInvestedAndAvailableBalance(
				fundSubscribed.getInvestmentAmount(),
				fund,
				client.getAvailableBalance()
		);
		this.validateIfAlreadySubscribedToFund(client.getFundsSubscribed(), fund);
		this.addExtraInformationToFundSubscribed(fund.getName(), fundSubscribed);
		client.getFundsSubscribed().add(fundSubscribed);
		this.updateAvailableBalance(client, fundSubscribed.getInvestmentAmount(), ConstantDomain.TYPE_OPENING);
		this.clientPersistencePort.saveClient(client);
		this.transactionService.registerTransaction(
				client.getId(),
				fund.getName(),
				fundSubscribed.getInvestmentAmount(),
				ConstantDomain.TYPE_OPENING
		);
		this.defineSendMethod(client, fund);
	}

	@Override
	public void cancellationSubscription(String clientId, Long fundId) {

		Client client = this.getClient(clientId);
		String nameFund = fundService.getFundById(fundId).getName();
		int position;

		Optional<FundSubscribed> fundToCancelled = client
				.getFundsSubscribed().stream()
				.filter(fundSubscribed -> fundId.equals(fundSubscribed.getFundId()))
				.findFirst();

		if (fundToCancelled.isEmpty()) {
			throw new NotFoundException(
					String.format(
							ConstantDomain.UNSUBSCRIBED_INVESTMENT_FUND,
							fundId)
			);
		}

		position = client.getFundsSubscribed().indexOf(fundToCancelled.get());
		client.getFundsSubscribed().remove(position);
		this.updateAvailableBalance(client, fundToCancelled.get().getInvestmentAmount(), ConstantDomain.TYPE_CANCELLATION);
		this.clientPersistencePort.saveClient(client);
		this.transactionService.registerTransaction(
				client.getId(),
				nameFund,
				fundToCancelled.get().getInvestmentAmount(),
				ConstantDomain.TYPE_CANCELLATION
		);
	}

	public Client getClient(String clientId) {
		return clientPersistencePort.getClient(clientId)
				.orElseThrow(()-> new NotFoundException(
						String.format(
								ConstantDomain.NOT_FOUND_MESSAGE,
								ConstantDomain.Utils.CLIENT.get(),
								clientId)
				));
	}

	private void validateAmountToInvestedAndAvailableBalance(
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

	private void addExtraInformationToFundSubscribed(String nameFund, FundSubscribed fundSubscribed) {
		fundSubscribed.setName(nameFund);
	}

	private void investmentAmountException(String message, String fund) {
		throw new InvestmentAmountException(String.format(message, fund));
	}

	private void validateIfAlreadySubscribedToFund(List<FundSubscribed> funds, InvestmentFund fundToSubscribed) {
		funds.forEach(fundSubscribed -> {
			if (fundSubscribed.getFundId().equals(fundToSubscribed.getId())) {
				throw new FundAlreadyExistsException(String.format(
						ConstantDomain.FUND_ALREADY_EXISTS,
						fundToSubscribed.getName()
				));
			}
		});
	}

	private void updateAvailableBalance(Client client, BigDecimal investmentAmount, String type) {
		BigDecimal availableBalance = client.getAvailableBalance();
		BigDecimal newBalance = type.equals(ConstantDomain.TYPE_OPENING) ?
				availableBalance.subtract(investmentAmount) :
				availableBalance.add(investmentAmount);
		client.setAvailableBalance(newBalance);
	}

	private void defineSendMethod(Client client, InvestmentFund fund) {

		ISendNotificationPort notificationPort = methodSend.apply(client.getNotificationPreference());
		String message = String.format(ConstantDomain.MESSAGE_NOTIFICATION, fund.getName());

		if (client.getNotificationPreference().equalsIgnoreCase(ConstantDomain.SMS)) {
			notificationPort.sendNotification(client.getCellPhone(), message);
			return;
		}

		notificationPort.sendNotification(client.getEmail(), ConstantDomain.SUBJECT, message);
	}

	@Override
	public List<FundSubscribed> listAssociatedFunds(String clientId) {
		return this.getClient(clientId).getFundsSubscribed();
	}

	@Override
	public Client getClientInformation(String clientId) {
		return this.getClient(clientId);
	}

	@Override
	public List<Transaction> getAllTransaction(String clientId) {
		return transactionService.getAllTransaction(clientId);
	}
}
