package com.victordw.btg.domain.spi;

import com.victordw.btg.domain.model.Client;

public interface IClientPersistencePort {

	Client getClient(String dni);
	void saveClient(Client client);
}
