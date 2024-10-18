package com.victordw.btg.adapter.driving.http.mapper.request;

import com.victordw.btg.adapter.driving.http.dto.request.FundSubscribedRequest;
import com.victordw.btg.domain.model.FundSubscribed;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IFundSubscribeMapperRequest {

	@Mapping(target = "name", ignore = true)
	FundSubscribed toModel(FundSubscribedRequest request);
}
