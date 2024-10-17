package com.victordw.btg.configuration;

import com.victordw.btg.domain.api.IFundServicesPort;
import com.victordw.btg.domain.api.usecase.FundUseCase;
import com.victordw.btg.domain.spi.IFundPersistenPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

	private final IFundPersistenPort fundPersistenPort;

	@Bean
	public IFundServicesPort fundServicesPort() {
		return new FundUseCase(fundPersistenPort);
	}
}
