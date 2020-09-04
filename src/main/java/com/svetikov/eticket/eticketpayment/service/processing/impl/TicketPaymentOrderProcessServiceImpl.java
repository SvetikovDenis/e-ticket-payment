package com.svetikov.eticket.eticketpayment.service.processing.impl;

import com.svetikov.eticket.eticketpayment.exception.db.EntityNotFoundException;
import com.svetikov.eticket.eticketpayment.model.PaymentStatus;
import com.svetikov.eticket.eticketpayment.model.TicketPaymentOrder;
import com.svetikov.eticket.eticketpayment.model.constant.OrderStatusNames;
import com.svetikov.eticket.eticketpayment.service.model.PaymentStatusService;
import com.svetikov.eticket.eticketpayment.service.reception.TicketPaymentOrderService;
import com.svetikov.eticket.eticketpayment.service.processing.TicketPaymentOrderProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class TicketPaymentOrderProcessServiceImpl implements TicketPaymentOrderProcessService {


    @Autowired
    private PaymentStatusService paymentStatusService;

    @Autowired
    private TicketPaymentOrderService paymentOrderService;

    private final Logger log = LoggerFactory.getLogger(TicketPaymentOrderProcessServiceImpl.class);


    @Override
    public Map<Object, Object> processPayment(String paymentId) {

        TicketPaymentOrder paymentOrder = paymentOrderService.getByPaymentId(paymentId);

        if (paymentOrder == null) {
            log.warn("In processPaymentOrder - ticket payment order for payment id : {} was not found", paymentOrder);
            throw new EntityNotFoundException(TicketPaymentOrder.class, "payment_id", paymentId);
        }

        PaymentStatus excludedStatus = paymentStatusService.getByName(OrderStatusNames.getBeginProcessing());
        if (excludedStatus == null) {
            log.warn("In processPaymentOrder - payment status for name : {} was not found ", OrderStatusNames.getBeginProcessing());
            throw new EntityNotFoundException(PaymentStatus.class, "name", OrderStatusNames.getBeginProcessing());
        }
        List<PaymentStatus> paymentStatuses = paymentStatusService.getAllExcept(excludedStatus.getId());

        if (paymentStatuses.isEmpty()) {
            log.warn("In processPaymentOrder - payment statuses was not found ");
            throw new EntityNotFoundException(PaymentStatus.class, "name", "all");
        }
        String orderStatus = getRandomStatus(paymentStatuses);
        Map<Object, Object> responeStatus = new HashMap<>();
        responeStatus.put("status", orderStatus);
        return responeStatus;
    }

    private String getRandomStatus(List<PaymentStatus> statuses) {
        Random random = new Random();
        return statuses.get(random.nextInt(statuses.size())).getName();
    }

}
