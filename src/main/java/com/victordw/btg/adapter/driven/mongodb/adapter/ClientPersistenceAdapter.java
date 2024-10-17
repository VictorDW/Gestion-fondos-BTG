package com.victordw.btg.adapter.driven.mongodb.adapter;

import com.victordw.btg.adapter.driven.mongodb.document.ClientDoc;
import com.victordw.btg.adapter.driven.mongodb.mapper.IClientMapper;
import com.victordw.btg.adapter.driven.mongodb.repository.IClientRepository;
import com.victordw.btg.domain.model.Client;
import com.victordw.btg.domain.spi.IClientPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientPersistenceAdapter implements IClientPersistencePort {

	private final IClientRepository clientRepository;
	private final IClientMapper clientMapper;

	@Override
	public Client getClient(String dni) {
		ClientDoc clientDoc = clientRepository.findByDni(dni);
		return clientMapper.toModel(clientDoc);
	}

	@Override
	public void saveClient(Client client) {
		ClientDoc clientDoc = clientMapper.toDoc(client);
		clientRepository.save(clientDoc);
	}
}
