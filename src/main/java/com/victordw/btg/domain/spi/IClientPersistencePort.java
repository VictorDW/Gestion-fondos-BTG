package com.victordw.btg.domain.spi;

import com.victordw.btg.domain.model.Client;

import java.util.Optional;

public interface IClientPersistencePort {

	Optional<Client> getClient(String clientId);
	void saveClient(Client client);
}
