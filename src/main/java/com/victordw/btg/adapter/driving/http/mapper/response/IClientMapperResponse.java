package com.victordw.btg.adapter.driving.http.mapper.response;

import com.victordw.btg.adapter.driving.http.dto.response.ClientInfoResponse;
import com.victordw.btg.adapter.driving.http.dto.response.FundSubscribedResponse;
import com.victordw.btg.domain.model.Client;
import com.victordw.btg.domain.model.FundSubscribed;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IClientMapperResponse {

 List<FundSubscribedResponse> toDtoList(List<FundSubscribed> funds);
 ClientInfoResponse toDto(Client client);

}
