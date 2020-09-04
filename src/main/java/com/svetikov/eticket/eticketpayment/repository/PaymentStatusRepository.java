package com.svetikov.eticket.eticketpayment.repository;

import com.svetikov.eticket.eticketpayment.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Long> {

    PaymentStatus findByName(String name);

    List<PaymentStatus> findAllByIdIsNot(Long id);

}
