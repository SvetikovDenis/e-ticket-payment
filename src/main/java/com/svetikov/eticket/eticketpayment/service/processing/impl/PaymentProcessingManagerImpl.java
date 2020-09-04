package com.svetikov.eticket.eticketpayment.service.processing.impl;

import com.svetikov.eticket.eticketpayment.dto.request.PaymentOrderStatusDTO;
import com.svetikov.eticket.eticketpayment.exception.db.EntityNotFoundException;
import com.svetikov.eticket.eticketpayment.model.PaymentStatus;
import com.svetikov.eticket.eticketpayment.model.TicketPaymentOrder;
import com.svetikov.eticket.eticketpayment.model.constant.OrderStatusNames;
import com.svetikov.eticket.eticketpayment.service.model.PaymentStatusService;
import com.svetikov.eticket.eticketpayment.service.processing.PaymentProcessingManager;
import com.svetikov.eticket.eticketpayment.service.reception.TicketPaymentOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Transactional
@Component
@EnableAsync
public class PaymentProcessingManagerImpl implements PaymentProcessingManager {

    @Autowired
    private TicketPaymentOrderService ticketPaymentOrderService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PaymentStatusService paymentStatusService;

    private final static String PROCESS_URL = "http://localhost:8080/api/v1/process/payment/ticket/";

    private final Logger log = LoggerFactory.getLogger(PaymentProcessingManagerImpl.class);

    @Async
    @Scheduled(fixedRateString =  "${config.processing.rate.milliseconds}")
    @Override
    public void processNext() {

        PaymentStatus beginStatus = paymentStatusService.getByName(OrderStatusNames.getBeginProcessing());
        PaymentStatus inProcessStatus = paymentStatusService.getByName(OrderStatusNames.getInProcess());

        TicketPaymentOrder candidateTicketPaymentOrder = ticketPaymentOrderService.getReadyProcessOrder(beginStatus.getId(), inProcessStatus.getId());

        if (candidateTicketPaymentOrder != null) {

            String resultPaymentStatus = processPaymentOrder(candidateTicketPaymentOrder);
            PaymentStatus processedPaymentStatus = paymentStatusService.getByName(resultPaymentStatus);

            if (processedPaymentStatus == null) {
                log.warn("In run - payment status for status name : {} was not found", resultPaymentStatus);
                throw new EntityNotFoundException(PaymentStatus.class, "name", resultPaymentStatus);
            }
            candidateTicketPaymentOrder.setStatus(processedPaymentStatus);
            candidateTicketPaymentOrder = ticketPaymentOrderService.save(candidateTicketPaymentOrder);
            log.info("In run - payment order : {} was saved",candidateTicketPaymentOrder);
        }
    }

    private String processPaymentOrder(TicketPaymentOrder ticketPaymentOrder) {
        PaymentOrderStatusDTO result = restTemplate.
                getForObject(PROCESS_URL + ticketPaymentOrder.getPaymentId(), PaymentOrderStatusDTO.class);
        if (result == null) {
            log.warn("In run - result of payment order can't be null");
            throw new IllegalArgumentException("Payment result status can'b be null");
        }
        return result.getStatus();
    }

}
