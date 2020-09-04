package com.svetikov.eticket.eticketpayment.service.reception.impl;

import com.svetikov.eticket.eticketpayment.dto.request.TicketPaymentRequestOrderDTO;
import com.svetikov.eticket.eticketpayment.exception.db.DuplicateEntityException;
import com.svetikov.eticket.eticketpayment.exception.db.EntityNotFoundException;
import com.svetikov.eticket.eticketpayment.model.PaymentStatus;
import com.svetikov.eticket.eticketpayment.model.TicketPaymentOrder;
import com.svetikov.eticket.eticketpayment.model.comparator.PaymentOrderSentTimeComparer;
import com.svetikov.eticket.eticketpayment.model.constant.OrderStatusNames;
import com.svetikov.eticket.eticketpayment.repository.TicketPaymentOrderRepository;
import com.svetikov.eticket.eticketpayment.service.model.PaymentStatusService;
import com.svetikov.eticket.eticketpayment.service.reception.TicketPaymentOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TicketPaymentOrderServiceImpl implements TicketPaymentOrderService {

    @Autowired
    private TicketPaymentOrderRepository ticketPaymentOrderRepository;

    @Autowired
    private PaymentStatusService paymentStatusService;

    private final SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final Logger log = LoggerFactory.getLogger(TicketPaymentOrderServiceImpl.class);

    @Override
    public List<TicketPaymentOrder> getAll() {
        List<TicketPaymentOrder> ticketPaymentOrders = ticketPaymentOrderRepository.findAll();
        if (ticketPaymentOrders.isEmpty()) {
            log.warn("In TicketPaymentOrder - no payment orders was found");
            return ticketPaymentOrders;
        }
        log.info("In getAll  - {} payment orders was found", ticketPaymentOrders.size());
        return ticketPaymentOrders;
    }

    @Transactional
    @Override
    public Map<Object, Object> createPaymentOrder(TicketPaymentRequestOrderDTO requestOrderDTO) {

        TicketPaymentOrder paymentOrderCandidate = ticketPaymentOrderRepository.findByExternalOrderId(requestOrderDTO.getOrderId());
        if (paymentOrderCandidate != null) {
            log.warn("In createPaymentOrder - duplicate  order request for order id : {}", requestOrderDTO.getOrderId());
            throw new DuplicateEntityException(TicketPaymentOrder.class, "order id", requestOrderDTO.getOrderId());
        }

        PaymentStatus paymentStatus = paymentStatusService.getByName(OrderStatusNames.getBeginProcessing());

        if (paymentStatus == null) {
            log.warn("In createPaymentOrder - payment status :  {} was not found", OrderStatusNames.getBeginProcessing());
            throw new EntityNotFoundException(PaymentStatus.class, "name", OrderStatusNames.getBeginProcessing());
        }

        String paymentId = UUID.randomUUID().toString();
        Map<Object, Object> response = new HashMap<>();

        TicketPaymentOrder ticketPaymentOrder = TicketPaymentOrder
                .builder()
                .externalOrderId(requestOrderDTO.getOrderId())
                .routeNumber(requestOrderDTO.getRouteNumber())
                .clientId(requestOrderDTO.getClientId())
                .status(paymentStatus)
                .orderSentTime(dateFormat.format(requestOrderDTO.getOrderSentTime()))
                .paymentId(paymentId)
                .build();

        ticketPaymentOrder = ticketPaymentOrderRepository.save(ticketPaymentOrder);
        response.put("Payment_id", ticketPaymentOrder.getPaymentId());
        return response;
    }

    @Override
    public Map<Object, Object> getPaymentOrderStatusByPaymentId(String id) {
        TicketPaymentOrder ticketPaymentOrderCandidate = ticketPaymentOrderRepository.findByPaymentId(id);
        if (ticketPaymentOrderCandidate == null) {
            log.warn("In getPaymentOrderStatus - ticket payment order with order id : {} was not found", id);
            throw new EntityNotFoundException(TicketPaymentOrder.class, "payment_id", id);
        }
        String orderStatus = ticketPaymentOrderCandidate.getStatus().getName();
        Map<Object, Object> response = new HashMap<>();
        response.put("Order_status", orderStatus);
        return response;
    }

    @Override
    public TicketPaymentOrder getById(Long id) {
        TicketPaymentOrder ticketPaymentOrder = ticketPaymentOrderRepository.findById(id).orElse(null);
        if (ticketPaymentOrder == null) {
            log.warn("In getById - ticket payment order with id : {} was not found", id);
            throw new EntityNotFoundException(TicketPaymentOrder.class, "id", id.toString());
        }
        return ticketPaymentOrder;
    }

    @Override
    public TicketPaymentOrder getReadyProcessOrder(Long statusId1, Long statusId2) {
        TicketPaymentOrder ticketPaymentOrder = ticketPaymentOrderRepository.findReadyProcessOrder(statusId1, statusId2);
        if (ticketPaymentOrder == null) {
            log.info("In getReadyProcessOrder - no ready payment orders was found for status id : {} and : {}", statusId1, statusId2);
            return null;
        }
        log.info("In getReadyProcessOrder - got payment order : {} for processing ", ticketPaymentOrder);
        return ticketPaymentOrder;
    }

    @Override
    public TicketPaymentOrder getByPaymentId(String id) {
        TicketPaymentOrder ticketPaymentOrder = ticketPaymentOrderRepository.findByPaymentId(id);
        if (ticketPaymentOrder == null) {
            log.warn("In getByPaymentId - payment order with id : {} was not found ", id);
            throw new EntityNotFoundException(TicketPaymentOrder.class, "payment id", id);
        }
        log.info("In getByPaymentId - payment order with payment id : {} was found", id);
        return ticketPaymentOrderRepository.findByPaymentId(id);
    }

    @Override
    public List<TicketPaymentOrder> getAllFutureOrdersByClient(String clientId) {

        List<TicketPaymentOrder> allClientsOrders = ticketPaymentOrderRepository.findAllByClientId(clientId);
        if (allClientsOrders.isEmpty()) {
            log.warn("In getAllFutureOrdersByClient - client with id : {} does not existed in database",clientId);
            throw new EntityNotFoundException(TicketPaymentOrder.class, "clientId", clientId);
        }
        List<TicketPaymentOrder> ticketPaymentOrders = ticketPaymentOrderRepository.findAllFutureOrdersByClient(clientId);
        if (ticketPaymentOrders.isEmpty()) {
            log.info("In getAllFutureOrdersByClient - no future payment orders was found for client with id : {}", clientId);
            return ticketPaymentOrders;
        }
        Collections.sort(ticketPaymentOrders, new PaymentOrderSentTimeComparer());
        log.info("In getAllFutureOrdersByClient - {} future payment orders was found for client with id : {}", ticketPaymentOrders.size(), clientId);
        return ticketPaymentOrders;
    }


    @Override
    public TicketPaymentOrder save(TicketPaymentOrder ticketPaymentOrder) {
        TicketPaymentOrder savedOrder = ticketPaymentOrderRepository.save(ticketPaymentOrder);
        log.info("In save - payment order : {} was saved", savedOrder);
        return savedOrder;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        ticketPaymentOrderRepository.deleteById(id);
        log.info("In deleteById - payment order with id : {} was deleted ", id);
    }


    @Transactional
    @Override
    public void deleteByClientId(String id) {
        ticketPaymentOrderRepository.deleteByClientId(id);
        log.info("In deleteById - payment orders for client id : {} was deleted ", id);
    }
}
