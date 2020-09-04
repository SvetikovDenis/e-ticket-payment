package com.svetikov.eticket.eticketpayment.mapper;

import com.svetikov.eticket.eticketpayment.dto.model.TicketPaymentOrderDTO;
import com.svetikov.eticket.eticketpayment.model.TicketPaymentOrder;
import com.svetikov.eticket.eticketpayment.service.model.PaymentStatusService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TicketPaymentOrderMapper  extends AbstractMapper<TicketPaymentOrder,TicketPaymentOrderDTO>{


    @Autowired
    private  ModelMapper mapper;

    @Autowired
    private PaymentStatusService paymentStatusService;


    TicketPaymentOrderMapper() {
        super(TicketPaymentOrder.class, TicketPaymentOrderDTO.class);
    }

    @PostConstruct
    private void setupMapper() {
        mapper.createTypeMap(TicketPaymentOrder.class, TicketPaymentOrderDTO.class)
                .addMappings(m -> m.skip(TicketPaymentOrderDTO::setStatus))
                .addMappings(m -> m.skip(TicketPaymentOrderDTO::setOrderId))
                .setPostConverter(toDtoConverter());

        mapper.createTypeMap(TicketPaymentOrderDTO.class, TicketPaymentOrder.class)
                .addMappings(m -> m.skip(TicketPaymentOrder::setStatus))
                .addMappings(m -> m.skip(TicketPaymentOrder::setExternalOrderId))
                .setPostConverter(toEntityConverter());
    }


    @Override
    void mapSpecificFields(TicketPaymentOrder source, TicketPaymentOrderDTO destination) {
        destination.setStatus(Objects.isNull(source) || Objects.isNull(source.getStatus().getName()) ? null : source.getStatus().getName());
        destination.setOrderId(Objects.isNull(source) || Objects.isNull(source.getExternalOrderId()) ? null : source.getExternalOrderId());
    }

    @Override
    void mapSpecificFields(TicketPaymentOrderDTO source, TicketPaymentOrder destination) {
        if (source.getStatus() != null) {
            destination.setStatus(paymentStatusService.getByName(source.getStatus()));
        }
        destination.setExternalOrderId(source.getOrderId());
    }

    public List<TicketPaymentOrderDTO> convertListToDTO(List<TicketPaymentOrder> orders) {
        return orders.stream().map(this::toDto).collect(Collectors.toList());
    }
}
