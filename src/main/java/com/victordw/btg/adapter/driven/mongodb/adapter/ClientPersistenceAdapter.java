package com.victordw.btg.adapter.driven.mongodb.adapter;

import com.victordw.btg.adapter.driven.mongodb.document.ClientDoc;
import com.victordw.btg.adapter.driven.mongodb.mapper.IClientMapper;
import com.victordw.btg.adapter.driven.mongodb.repository.IClientRepository;
import com.victordw.btg.domain.model.Client;
import com.victordw.btg.domain.spi.IClientPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientPersistenceAdapter implements IClientPersistencePort {

	private final IClientRepository clientRepository;
	private final IClientMapper clientMapper;

	@Override
	public Optional<Client> getClient(String clientId) {
	 return clientRepository.findById(clientId).map(clientMapper::toModel);
	}

	@Override
	public void saveClient(Client client) {
		ClientDoc clientDoc = clientMapper.toDoc(client);
		clientRepository.save(clientDoc);
	}
}
