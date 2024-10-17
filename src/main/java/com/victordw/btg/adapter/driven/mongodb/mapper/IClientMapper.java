package com.victordw.btg.adapter.driven.mongodb.mapper;

import com.victordw.btg.adapter.driven.mongodb.document.ClientDoc;
import com.victordw.btg.domain.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IClientMapper {

	Client toModel(ClientDoc clientDoc);

	ClientDoc toDoc(Client client);


}
