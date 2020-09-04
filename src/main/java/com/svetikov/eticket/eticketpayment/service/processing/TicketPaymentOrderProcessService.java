package com.svetikov.eticket.eticketpayment.service.processing;

import java.util.Map;

public interface TicketPaymentOrderProcessService {

    Map<Object, Object> processPayment(String paymentId);
}
