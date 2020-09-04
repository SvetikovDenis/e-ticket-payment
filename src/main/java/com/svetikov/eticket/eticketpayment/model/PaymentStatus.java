package com.svetikov.eticket.eticketpayment.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "payment_status")
public class PaymentStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

}
