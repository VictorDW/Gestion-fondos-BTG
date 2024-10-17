package com.victordw.btg.domain.api.usecase;

import com.victordw.btg.domain.api.IClientServicePort;
import com.victordw.btg.domain.exception.ClientNotFoundException;
import com.victordw.btg.domain.model.Client;
import com.victordw.btg.domain.model.FundSubscribed;
import com.victordw.btg.domain.spi.IClientPersistencePort;
import com.victordw.btg.domain.util.ConstantDomain;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientUseCase implements IClientServicePort {

	private final IClientPersistencePort clientPersistencePort;

	@Override
	public void addSubscription(String clientId, FundSubscribed fundSubscribed) {

		Client client = clientPersistencePort.getClient(clientId)
				.orElseThrow(()-> new ClientNotFoundException(
						String.format(ConstantDomain.NOT_FOUND_MESSAGE, clientId)));
		
		client.getFundsSubscribed().add(fundSubscribed);
		clientPersistencePort.saveClient(client);

	}

}
