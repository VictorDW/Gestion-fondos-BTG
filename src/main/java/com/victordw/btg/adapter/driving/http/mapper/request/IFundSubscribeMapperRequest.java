package com.victordw.btg.adapter.driving.http.mapper.request;

import com.victordw.btg.adapter.driving.http.dto.request.FundSubscribedRequest;
import com.victordw.btg.domain.model.FundSubscribed;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IFundSubscribeMapperRequest {

	FundSubscribed toModel(FundSubscribedRequest request);
}
