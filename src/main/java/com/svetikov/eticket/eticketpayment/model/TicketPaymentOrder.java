package com.svetikov.eticket.eticketpayment.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ticket_payment_order")
public class TicketPaymentOrder extends AbstractEntity{

    @Column(name = "external_order_id")
    private String externalOrderId;

    @Column(name = "route_number")
    private String routeNumber;

    @Column(name = "order_sent_time")
    private String orderSentTime;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "payment_id")
    private String paymentId;

    @OneToOne
    @JoinColumn(name = "status_id")
    private PaymentStatus status;

    public TicketPaymentOrder() {
    }

    @Builder
    public TicketPaymentOrder(Long id, String routeNumber, String orderSentTime, String clientId, String paymentId, PaymentStatus status, String externalOrderId) {
        setId(id);
        this.routeNumber = routeNumber;
        this.orderSentTime = orderSentTime;
        this.clientId = clientId;
        this.paymentId = paymentId;
        this.status = status;
        this.externalOrderId = externalOrderId;
    }
}
