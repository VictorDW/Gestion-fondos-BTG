package com.victordw.btg.configuration;

import com.victordw.btg.domain.api.IClientServicePort;
import com.victordw.btg.domain.api.IFundServiceBasic;
import com.victordw.btg.domain.api.IFundServicesPort;
import com.victordw.btg.domain.api.usecase.ClientUseCase;
import com.victordw.btg.domain.api.usecase.FundUseCase;
import com.victordw.btg.domain.spi.IClientPersistencePort;
import com.victordw.btg.domain.spi.IFundPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

	private final IFundPersistencePort fundPersistencePort;
	private final IClientPersistencePort clientPersistencePort;

	@Bean
	public IFundServicesPort fundServicesPort() {
		return new FundUseCase(fundPersistencePort);
	}

	@Bean
	public IClientServicePort clientServicesPort() {
		return new ClientUseCase(clientPersistencePort, (IFundServiceBasic) fundServicesPort());
	}
}
