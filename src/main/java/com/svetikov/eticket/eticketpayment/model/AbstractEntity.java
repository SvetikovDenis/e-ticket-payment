package com.svetikov.eticket.eticketpayment.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@MappedSuperclass
@Data
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created")
    private Timestamp created;

    @Column(name = "updated")
    private Timestamp updated;

    //REFACTOR
    @PrePersist
    public void toCreate() {
        setCreated(new Timestamp(System.currentTimeMillis()));
    }

    @PreUpdate
    public void toUpdate() {
        setUpdated(new Timestamp(System.currentTimeMillis()));
    }

}