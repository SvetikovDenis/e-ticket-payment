package com.svetikov.eticket.eticketpayment.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "ticket_payment_order")
public class TicketPaymentOrder extends AbstractEntity{

    @NotBlank(message = "Order id can'b be null or blank")
    @Size(min = 5, max = 25, message = "Order id can't be less than 5 symbols or more than 25")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Order id should consist characters and numbers only")
    @Column(name = "external_order_id")
    private String externalOrderId;

    @NotBlank(message = "Route number can'b be null or blank")
    @Size(min = 5, max = 25, message = "Route number can't be less than 5 symbols or more than 25")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Route number should consist characters and numbers only")
    @Column(name = "route_number")
    private String routeNumber;

    @NotNull
    @Column(name = "order_sent_time")
    private String orderSentTime;

    @NotBlank(message = "Client identifier must be not null")
    @Size(min = 5, max = 20, message = "Client identifier can't be less than 5 symbols or more than 20")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Client identifier should consist characters and numbers only")
    @Column(name = "client_id")
    private String clientId;

    @NotBlank(message = "Payment id can'b be null or blank")
    @Size(min = 36, max = 36)
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
