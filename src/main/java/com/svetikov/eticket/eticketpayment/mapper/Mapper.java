package com.svetikov.eticket.eticketpayment.mapper;

import com.svetikov.eticket.eticketpayment.dto.AbstractDTO;
import com.svetikov.eticket.eticketpayment.model.AbstractEntity;

public interface Mapper<E extends AbstractEntity, D extends AbstractDTO> {

    E toEntity(D dto);

    D toDto(E entity);

    void updateEntity(D dto, E entity);
}
