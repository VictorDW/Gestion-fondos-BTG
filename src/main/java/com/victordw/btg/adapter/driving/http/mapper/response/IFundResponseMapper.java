package com.victordw.btg.adapter.driving.http.mapper.response;

import com.victordw.btg.adapter.driving.http.dto.response.FundResponse;
import com.victordw.btg.domain.model.InvestmentFund;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IFundResponseMapper {

	List<FundResponse> toDto(List<InvestmentFund> investmentFund);

}
