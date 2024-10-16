package com.victordw.btg.adapter.driven.mongodb.mapper;

import com.victordw.btg.adapter.driven.mongodb.document.InvestmentFundDoc;
import com.victordw.btg.domain.model.InvestmentFund;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IFundMapper {

	InvestmentFund toModel(InvestmentFundDoc investmentFundDoc);
}
