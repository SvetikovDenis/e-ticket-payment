package com.svetikov.eticket.eticketpayment.dto.request;

import com.svetikov.eticket.eticketpayment.dto.AbstractDTO;
import lombok.Data;


@Data
public class TicketPaymentOrderIdentifierDTO extends AbstractDTO {

    private String orderIdentifier;

}
