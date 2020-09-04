package com.svetikov.eticket.eticketpayment.service.reception;


import com.svetikov.eticket.eticketpayment.dto.request.TicketPaymentRequestOrderDTO;
import com.svetikov.eticket.eticketpayment.model.TicketPaymentOrder;

import java.util.List;
import java.util.Map;

public interface TicketPaymentOrderService {

    TicketPaymentOrder getById(Long id);

    TicketPaymentOrder getByPaymentId(String id);

    TicketPaymentOrder getReadyProcessOrder(Long statusId1,Long statusId2);

    List<TicketPaymentOrder> getAll();

    List<TicketPaymentOrder> getAllFutureOrdersByClient(String clientId);

    Map<Object,Object> createPaymentOrder(TicketPaymentRequestOrderDTO requestOrderDTO);

    Map<Object, Object> getPaymentOrderStatusByPaymentId(String id);

    TicketPaymentOrder save(TicketPaymentOrder ticketPaymentOrder);

    void deleteById(Long id);

    void deleteByClientId(String id);
}
