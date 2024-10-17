package com.victordw.btg.configuration;

import com.victordw.btg.domain.api.ISubscriptionServicePort;
import com.victordw.btg.domain.api.IFundServiceBasic;
import com.victordw.btg.domain.api.IFundServicesPort;
import com.victordw.btg.domain.api.ITransactionService;
import com.victordw.btg.domain.api.usecase.ClientUseCase;
import com.victordw.btg.domain.api.usecase.FundUseCase;
import com.victordw.btg.domain.api.usecase.TransactionUseCase;
import com.victordw.btg.domain.spi.IClientPersistencePort;
import com.victordw.btg.domain.spi.IFundPersistencePort;
import com.victordw.btg.domain.spi.ITransactionPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

	private final IFundPersistencePort fundPersistencePort;
	private final IClientPersistencePort clientPersistencePort;
	private final ITransactionPersistencePort transactionPersistencePort;

	@Bean
	public IFundServicesPort fundServicesPort() {
		return new FundUseCase(fundPersistencePort);
	}

	@Bean
	public ITransactionService transactionService() {
		return new TransactionUseCase(transactionPersistencePort);
	}
	@Bean
	public ISubscriptionServicePort clientServicesPort() {
		return new ClientUseCase(clientPersistencePort, (IFundServiceBasic) fundServicesPort(), transactionService());
	}


}
