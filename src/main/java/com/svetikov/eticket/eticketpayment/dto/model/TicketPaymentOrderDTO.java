package com.svetikov.eticket.eticketpayment.dto.model;

import com.svetikov.eticket.eticketpayment.dto.AbstractDTO;
import lombok.Data;

@Data
public class TicketPaymentOrderDTO extends AbstractDTO {

    private String orderId;
    private String routeNumber;
    private String orderSentTime;
    private String clientId;
    private String paymentId;
    private String status;

}
